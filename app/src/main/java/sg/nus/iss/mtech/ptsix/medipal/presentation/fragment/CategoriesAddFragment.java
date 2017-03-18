package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.CategoriesDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.CategoriesActivity;

public class CategoriesAddFragment extends Fragment {

    private RadioGroup categoriesRemind;
    private EditText categoryName, categoryCode, categoryDescription;
    private RadioButton categoriesRemindYes, categoriesRemindNo, categoriesRemindOp;
    private Boolean categoryRemind = true;
    private Button btnSave, btnCancel;
    private CategoriesDao categoriesDao;

    public CategoriesAddFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.categoriesDao = new CategoriesDao(this.getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_categories_add, container, false);

        categoryName = (EditText) rootView.findViewById(R.id.categories_name);
        categoryCode = (EditText) rootView.findViewById(R.id.categories_code);
        categoryDescription = (EditText) rootView.findViewById(R.id.categories_description);
        categoriesRemindYes = (RadioButton) rootView.findViewById(R.id.categories_remind_yes);
        categoriesRemindNo = (RadioButton) rootView.findViewById(R.id.categories_remind_no);
        categoriesRemindOp = (RadioButton) rootView.findViewById(R.id.categories_remind_optional);

        btnSave = (Button) rootView.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments().getInt("id") == Constant.CATEGORY_ADD_INVALID_ID) {
                    // Add new
                    if (isCommonValid() && isNewValid()) {
                        saveCategories();
                        Toast.makeText(getActivity(), R.string.category_add_save_completed, Toast.LENGTH_SHORT).show();
                        resetFields();
                        ((CategoriesActivity) getActivity()).switchTab(Constant.CATEGORY_TAB_LIST_INDEX, Constant.CATEGORY_ADD_INVALID_ID);
                    }
                } else {
                    // Update
                    if (isCommonValid() && isUpdateValid()) {
                        updateCategories();
                        Toast.makeText(getActivity(), R.string.category_add_update_completed, Toast.LENGTH_SHORT).show();
                        resetFields();
                        ((CategoriesActivity) getActivity()).switchTab(Constant.CATEGORY_TAB_LIST_INDEX, Constant.CATEGORY_ADD_INVALID_ID);
                    }
                }
            }
        });

        btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel the Category
                resetFields();
                ((CategoriesActivity) getActivity()).switchTab(Constant.CATEGORY_TAB_LIST_INDEX, Constant.CATEGORY_ADD_INVALID_ID);
            }
        });

        categoriesRemind = (RadioGroup) rootView.findViewById(R.id.categories_remind);
        categoriesRemind.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                categoriesRemindButtonClicked(checkedId);
            }
        });

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        int id = getArguments().getInt("id");
        if (getView() != null) {
            if (isVisibleToUser) {
                if (id > Constant.CATEGORY_ADD_INVALID_ID) {
                    Categories category = this.categoriesDao.getCategories(id);
                    setInputFromCategory(category);
                } else {
                    this.resetFields();
                }
            } else {
                this.resetFields();
            }
        }
    }

    private void categoriesRemindButtonClicked(int checkedId) {
        switch (checkedId) {
            case R.id.categories_remind_yes:
                this.categoryRemind = true;
                break;

            case R.id.categories_remind_no:
                this.categoryRemind = false;
                break;

            case R.id.categories_remind_optional:
                this.categoryRemind = null;
                break;
        }
    }

    private long updateCategories() {
        Categories category = createCategoryFromInput();
        category.setId(getArguments().getInt("id"));
        return categoriesDao.update(category);
    }

    private long saveCategories() {
        Categories category = createCategoryFromInput();
        return categoriesDao.save(category);
    }

    private Categories createCategoryFromInput() {
        Categories category = new Categories();
        category.setCategory(this.categoryName.getText().toString());
        category.setCode(this.categoryCode.getText().toString());
        category.setDescription(this.categoryDescription.getText().toString());
        if (this.categoryRemind == null) {
            category.setRemind(-1);
        } else if (this.categoryRemind) {
            category.setRemind(1);
        } else {
            category.setRemind(0);
        }

        return category;
    }

    private void setInputFromCategory(Categories category) {
        this.categoryName.setText(category.getCategory());
        this.categoryCode.setText(category.getCode());
        this.categoryDescription.setText(category.getDescription());
        if (category.getRemind() == 1) {
            this.categoriesRemindYes.setChecked(true);
            this.categoryRemind = true;
        } else if (category.getRemind() == 0) {
            this.categoriesRemindNo.setChecked(true);
            this.categoryRemind = false;
        } else {
            this.categoriesRemindOp.setChecked(true);
            this.categoryRemind = null;
        }
    }

    private void resetFields() {
        this.getArguments().putInt("id", Constant.CATEGORY_ADD_INVALID_ID);
        this.categoryName.setText("");
        this.categoryCode.setText("");
        this.categoryDescription.setText("");
        this.categoryRemind = true;
        this.categoriesRemindYes.setChecked(true);
    }

    private boolean isCommonValid() {
        boolean isValid = true;

        if (TextUtils.isEmpty(categoryName.getText().toString().trim())) {
            categoryName.setError(getResources().getString(R.string.category_add_error_category_name_empty));
            isValid = false;
        }

        if (categoryName.getText().toString().trim().length() > 50) {
            categoryName.setError(getResources().getString(R.string.category_add_error_category_name_length));
            isValid = false;
        }

        if (TextUtils.isEmpty(categoryCode.getText().toString().trim()) || categoryCode.getText().toString().trim().length() != 3) {
            categoryCode.setError(getResources().getString(R.string.category_add_error_category_code_length));
            isValid = false;
        }

        if (!categoryCode.getText().toString().trim().matches("[a-zA-Z]+")) {
            categoryCode.setError(getResources().getString(R.string.category_add_error_category_code_chara));
            isValid = false;
        }

        if (TextUtils.isEmpty(categoryDescription.getText().toString().trim())) {
            categoryDescription.setError(getResources().getString(R.string.category_add_error_description_name_empty));
            isValid = false;
        }

        if (categoryDescription.getText().toString().trim().length() > 255) {
            categoryDescription.setError(getResources().getString(R.string.category_add_error_description_name_length));
            isValid = false;
        }

        return isValid;
    }

    private boolean isNewValid() {
        boolean isValid = true;

        if (categoriesDao.getCategoriesByCode(categoryCode.getText().toString().trim()).size() > 0) {
            categoryCode.setError(getResources().getString(R.string.category_add_error_category_code_duplicated));
            isValid = false;
        }

        return isValid;
    }

    private boolean isUpdateValid() {
        boolean isValid = true;

        List<Categories> categoriesList = categoriesDao.getCategoriesByCode(categoryCode.getText().toString().trim());
        if (categoriesList.size() > 0) {
            for (int i = 0; i < categoriesList.size(); i++) {
                Categories categories = categoriesList.get(i);
                if (getArguments().getInt("id") != categories.getId()) {
                    categoryCode.setError(getResources().getString(R.string.category_add_error_category_code_duplicated));
                    isValid = false;
                }
            }
        }

        return isValid;
    }
}
