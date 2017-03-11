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
import android.widget.RadioGroup;
import android.widget.Toast;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.CategoriesDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.CategoriesActivity;

public class CategoriesAddFragment extends Fragment {

    private RadioGroup categoriesRemind;
    private EditText categoryName, categoryCode, categoryDescription;
    private Boolean categoryRemind = true;
    private Button btnSave, btnCancel;
    private CategoriesDao categoriesDao;

    public CategoriesAddFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoriesDao = new CategoriesDao(this.getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_categories_add, container, false);

        categoryName = (EditText) rootView.findViewById(R.id.categories_name);
        categoryCode = (EditText) rootView.findViewById(R.id.categories_code);
        categoryDescription = (EditText) rootView.findViewById(R.id.categories_description);

        btnSave = (Button) rootView.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    // Save the Category
                    Toast.makeText(getActivity(), "Saving completed", Toast.LENGTH_SHORT).show();
                    resetFields();
                    ((CategoriesActivity) getActivity()).switchTab(0, -1);
                }
            }
        });

        btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel the Category
                resetFields();
                ((CategoriesActivity) getActivity()).switchTab(0, -1);
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
        if (isVisibleToUser) {
            if (id >= 1) {
                Categories category = this.categoriesDao.getCategories(id);
                categoryName.setText(category.getEventCategory());
                categoryCode.setText(category.getEventCode());
                categoryDescription.setText(category.getEventDescription());
            } else {
                this.resetFields();
            }
        }
    }

    private void categoriesRemindButtonClicked(int checkedId) {
        switch (checkedId) {
            case R.id.categories_remind_yes:
                categoryRemind = true;
                break;

            case R.id.categories_remind_no:
                categoryRemind = false;
                break;

            case R.id.categories_remind_optional:
                categoryRemind = null;
                break;
        }
    }

    private void resetFields() {
        this.categoryName.setText("");
        this.categoryCode.setText("");
        this.categoryDescription.setText("");
        this.categoryRemind = true;
    }

    private boolean isValid() {
        boolean isValid = true;

        // Category name required check
        if (TextUtils.isEmpty(categoryName.getText().toString().trim())) {
            categoryName.setError("Please fill in a category name.");
            isValid = false;
        }

        // Category name number of character check
        if (categoryName.getText().toString().trim().length() > 50) {
            categoryName.setError("Please fill in a name below 50 characters.");
            isValid = false;
        }

        // Category code required check
        if (TextUtils.isEmpty(categoryCode.getText().toString().trim())) {
            categoryCode.setError("Please fill in a 3 character code.");
            isValid = false;
        }

        //Check code only have character

        // Category code duplicate required check
        // May have performance issue
        // // TODO: 11/3/17
        //if (categoriesDao.get.getCategoriesByCode(categoryCode.getText().toString().trim()).size() > 0) {
        //    categoryCode.setError("This category code already exist. Please use another.");
        //    isValid = false;
        //}

        // Category description required check
        if (TextUtils.isEmpty(categoryDescription.getText().toString().trim())) {
            categoryDescription.setError("Please fill in some text for description.");
            isValid = false;
        }

        // Category description number of character check
        if (categoryDescription.getText().toString().trim().length() > 255) {
            categoryDescription.setError("Please fill in a description below 255 characters.");
            isValid = false;
        }

        return isValid;
    }
}
