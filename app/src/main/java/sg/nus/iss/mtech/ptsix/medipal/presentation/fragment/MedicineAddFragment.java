package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.CategoriesDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MedicineDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.RemindersDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.CategoriesActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MedicineActivity;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;

public class MedicineAddFragment extends Fragment {

    private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.getDefault());
    private Spinner medicineCategory, medicineDosage;
    private EditText medicineName, medicineDescription, medicineFrequency, medicineFrequencyInterval, medicineFrequencyStartTime, medicineThreshold, medicineQuantity, medicineConsumeQuantity, medicineDateIssue, medicineExpireFactor;
    private Switch medicineRemindSwitch;
    private TextInputLayout medicineFrequencyLabel, medicineFrequencyIntervalLabel, medicineFrequencyStartTimeLabel, medicineThresholdLabel;
    private MedicineDao medicineDao;
    private CategoriesDao categoriesDao;
    private RemindersDao remindersDao;
    private Button btnSave, btnCancel, btnDelete;
    Calendar currentCal = Calendar.getInstance();
    Calendar shownDate = Calendar.getInstance();
    private Boolean reminderSwitchValue = false;

    private List<Categories> categoriesList = null;

    public MedicineAddFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.medicineDao = new MedicineDao(this.getContext());
        this.categoriesDao = new CategoriesDao(this.getContext());
        this.remindersDao = new RemindersDao(this.getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_medicine_add, container, false);

        this.medicineName = (EditText) rootView.findViewById(R.id.medicine_name);
        this.medicineDescription = (EditText) rootView.findViewById(R.id.medicine_description);
        this.medicineCategory = (Spinner) rootView.findViewById(R.id.medicine_category);
        this.medicineRemindSwitch = (Switch) rootView.findViewById(R.id.medicine_remind_switch);
        this.medicineFrequency = (EditText) rootView.findViewById(R.id.medicine_frequency);
        this.medicineFrequencyInterval = (EditText) rootView.findViewById(R.id.medicine_frequency_interval);
        this.medicineFrequencyStartTime = (EditText) rootView.findViewById(R.id.medicine_frequency_start_time);
        this.medicineThreshold = (EditText) rootView.findViewById(R.id.medicine_threshold);
        this.medicineFrequencyLabel = (TextInputLayout) rootView.findViewById(R.id.medicine_frequency_label);
        this.medicineFrequencyIntervalLabel = (TextInputLayout) rootView.findViewById(R.id.medicine_frequency_interval_label);
        this.medicineFrequencyStartTimeLabel = (TextInputLayout) rootView.findViewById(R.id.medicine_frequency_start_time_label);
        this.medicineThresholdLabel = (TextInputLayout) rootView.findViewById(R.id.medicine_threshold_label);
        this.medicineQuantity = (EditText) rootView.findViewById(R.id.medicine_quantity);
        this.medicineConsumeQuantity = (EditText) rootView.findViewById(R.id.medicine_consume_quantity);
        this.medicineDosage = (Spinner) rootView.findViewById(R.id.medicine_dosage);
        this.medicineDateIssue = (EditText) rootView.findViewById(R.id.medicine_date_issue);
        this.medicineExpireFactor = (EditText) rootView.findViewById(R.id.medicine_expire_factor);

        List<String> dosageList = new ArrayList<>();
        dosageList.add("<Select Dosage>");
        dosageList.add(1, "pills");
        dosageList.add(2, "cc");
        dosageList.add(3, "ml");
        dosageList.add(4, "gr");
        dosageList.add(5, "mg");
        dosageList.add(6, "drops");
        dosageList.add(7, "pieces");
        dosageList.add(8, "puffs");
        dosageList.add(9, "units");
        dosageList.add(10, "teaspoon");
        dosageList.add(11, "tablespoon");
        dosageList.add(12, "patch");

        ArrayAdapter<String> dosageAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, dosageList);
        this.medicineDosage.setAdapter(dosageAdapter);

        this.categoriesList = this.categoriesDao.getCategories();
        List<String> categoryCodes = new ArrayList<>();
        categoryCodes.add("<Select Category>");
        for (Categories category : categoriesList) {
            categoryCodes.add(category.getId(), category.getCode());
        }

        ArrayAdapter<String> catergoriesAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, categoryCodes);
        this.medicineCategory.setAdapter(catergoriesAdapter);

        this.medicineCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (position == 0) {
                    medicineRemindSwitch.setEnabled(false);
                    medicineRemindSwitch.setChecked(false);
                    //medicineThresholdLabel.setVisibility(View.GONE);
                    //medicineThresholdSwitch.setEnabled(false);
                } else {

                    Categories cat = categoriesList.get(position - 1);
                    if (cat.getRemind() == 0) {
                        medicineRemindSwitch.setEnabled(false);
                        medicineRemindSwitch.setChecked(false);
                    } else if (cat.getRemind() == 1) {
                        medicineRemindSwitch.setEnabled(false);
                        medicineRemindSwitch.setChecked(true);
                    } else {
                        medicineRemindSwitch.setEnabled(true);
                        medicineRemindSwitch.setChecked(true);
                    }

//                    if (cat.getCode().equals("CHR")) {
//                        medicineThresholdLabel.setVisibility(View.VISIBLE);
//                    } else {
//                        medicineThresholdLabel.setVisibility(View.GONE);
//                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                medicineRemindSwitch.setEnabled(false);
                //medicineThresholdSwitch.setEnabled(false);
            }
        });

        this.medicineRemindSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    medicineFrequencyLabel.setVisibility(View.VISIBLE);
                    medicineFrequencyIntervalLabel.setVisibility(View.VISIBLE);
                    medicineFrequencyStartTimeLabel.setVisibility(View.VISIBLE);
                    reminderSwitchValue = true;


                } else {
                    medicineFrequencyLabel.setVisibility(View.GONE);
                    medicineFrequencyIntervalLabel.setVisibility(View.GONE);
                    medicineFrequencyStartTimeLabel.setVisibility(View.GONE);
                    reminderSwitchValue = false;
                }
            }
        });

        this.medicineFrequencyStartTime.setText(dateFormatter.format(shownDate.getTime()));
        this.medicineFrequencyStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                shownDate = calendar;
                                medicineFrequencyStartTime.setText(dateFormatter.format(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getActivity(), onDateSetListener,
                                currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                                currentCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        this.btnSave = (Button) rootView.findViewById(R.id.btn_save);
        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments().getInt("id") == Constant.MEDICINE_ADD_INVALID_ID) {
                    // Add new
                    if (isCommonValid() && isNewValid()) {
                        saveMedicines();
                        Toast.makeText(getActivity(), R.string.medicine_add_save_completed, Toast.LENGTH_SHORT).show();
                        resetFields();
                        ((MedicineActivity) getActivity()).switchTab(Constant.MEDICINE_TAB_LIST_INDEX, Constant.MEDICINE_ADD_INVALID_ID);
                    }
                } else {
                    // Update
                    if (isCommonValid() && isUpdateValid()) {
                        updateMedicines();
                        Toast.makeText(getActivity(), R.string.medicine_add_update_completed, Toast.LENGTH_SHORT).show();
                        resetFields();
                        ((MedicineActivity) getActivity()).switchTab(Constant.MEDICINE_TAB_LIST_INDEX, Constant.MEDICINE_ADD_INVALID_ID);
                    }
                }
            }
        });

        this.btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel the Category
                resetFields();
                ((MedicineActivity) getActivity()).switchTab(Constant.MEDICINE_TAB_LIST_INDEX, Constant.MEDICINE_ADD_INVALID_ID);
            }
        });

        this.btnDelete = (Button) rootView.findViewById(R.id.btn_delete);
        this.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel the Category
                resetFields();
                ((CategoriesActivity) getActivity()).switchTab(Constant.MEDICINE_TAB_LIST_INDEX, Constant.MEDICINE_ADD_INVALID_ID);
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
                if (id > Constant.MEDICINE_ADD_INVALID_ID) {
                    Medicine medicine = this.medicineDao.getMedicine(id);
                    setInputFromMedicine(medicine);
                } else {
                    this.resetFields();
                }
            } else {
                this.resetFields();
            }
        }
    }

    private long updateMedicines() {
        Medicine databaseMedicine = medicineDao.getMedicine(getArguments().getInt("id"));
        Medicine newMedicine = createMedicineFromInput();
        newMedicine.setId(databaseMedicine.getId());
        newMedicine.setReminderId(databaseMedicine.getReminderId());
        Long mediResult = 0L;
        Long remindResult = 0L;

        if (newMedicine.getRemind() == 1) {
            // Set reminder table
            mediResult = medicineDao.update(newMedicine);
            Reminders reminder = createReminderFromInput();
            reminder.setId(databaseMedicine.getReminderId());
            remindResult = remindersDao.update(reminder);
        } else {
            // Clear reminder column data
            mediResult = medicineDao.update(newMedicine);
            Reminders reminder = new Reminders();
            reminder.setId(databaseMedicine.getReminderId());
            reminder.setInterval(0);
            reminder.setFrequency(0);
            reminder.setEventStartTime(new Date());
            remindResult = remindersDao.update(reminder);
        }

        return 1;
    }

    private long saveMedicines() {
        Medicine newMedicine = createMedicineFromInput();
        Long mediResult = 0L;
        Long remindResult = 0L;

        if (newMedicine.getRemind() == 1) {
            // Set reminder table
            Reminders reminder = createReminderFromInput();
            remindResult = remindersDao.save(reminder);
            newMedicine.setReminderId(remindResult.intValue());
            mediResult = medicineDao.save(newMedicine);
        } else {
            // Clear reminder column data
            Reminders reminder = new Reminders();
            reminder.setInterval(0);
            reminder.setFrequency(0);
            reminder.setEventStartTime(new Date());
            remindResult = remindersDao.save(reminder);
            newMedicine.setReminderId(remindResult.intValue());
            mediResult = medicineDao.save(newMedicine);
        }
        return 1;
    }

    private Medicine createMedicineFromInput() {
        Medicine medicine = new Medicine();
        medicine.setMedicine(this.medicineName.getText().toString().trim());
        medicine.setDescription(this.medicineDescription.getText().toString().trim());
        medicine.setCatId(this.medicineCategory.getSelectedItemPosition());
        if (this.reminderSwitchValue) {
            medicine.setRemind(1);
        } else {
            medicine.setRemind(0);
        }
        medicine.setQuantity(Integer.parseInt(this.medicineQuantity.getText().toString().trim()));
        medicine.setDosage(this.medicineDosage.getSelectedItemPosition());
        medicine.setConsumeQuantity(Integer.parseInt(this.medicineConsumeQuantity.getText().toString().trim()));
        try {
            medicine.setDateIssued(dateFormatter.parse(this.medicineDateIssue.getText().toString().trim()));
        } catch (ParseException ex) {
            // no need to handle
        }
        medicine.setExpireFactor(Integer.parseInt(this.medicineExpireFactor.getText().toString().trim()));
        medicine.setThreshold(Integer.parseInt(this.medicineThreshold.getText().toString().trim()));
        return medicine;
    }

    private Reminders createReminderFromInput() {
        Reminders reminder = new Reminders();
        try {
            reminder.setEventStartTime(dateFormatter.parse(this.medicineFrequencyStartTime.getText().toString().trim()));
        } catch (ParseException ex) {
            // no need to handle
        }
        reminder.setFrequency(Integer.parseInt(this.medicineFrequency.getText().toString()));
        reminder.setInterval(Integer.parseInt(this.medicineFrequencyInterval.getText().toString()));
        return reminder;
    }

    private void setInputFromMedicine(Medicine medicine) {
        this.medicineName.setText(medicine.getMedicine());
        this.medicineDescription.setText(medicine.getDescription());
        this.medicineCategory.setSelection(medicine.getCatId());
        if (medicine.getRemind() == 1) {
            this.medicineRemindSwitch.setChecked(true);

            Reminders reminder = this.remindersDao.getReminders(medicine.getReminderId());

            this.medicineFrequency.setText(reminder.getFrequency() + "");
            this.medicineFrequencyInterval.setText(reminder.getInterval() + "");
            this.medicineFrequencyStartTime.setText(dateFormatter.format(reminder.getEventStartTime()));
        } else {
            this.medicineRemindSwitch.setChecked(false);
        }

        if (medicine.getThreshold() > 0) {
            this.medicineThreshold.setText(medicine.getThreshold() + "");
        } else {
            this.medicineThreshold.setText("0");
        }
        this.medicineQuantity.setText(medicine.getQuantity() + "");
        this.medicineConsumeQuantity.setText(medicine.getConsumeQuantity() + "");
        this.medicineDosage.setSelection(medicine.getDosage());
        if (medicine.getId() == Constant.MEDICINE_ADD_INVALID_ID) {
            this.btnDelete.setVisibility(View.GONE);
        } else {
            this.btnDelete.setVisibility(View.VISIBLE);
        }
        this.medicineDateIssue.setText(dateFormatter.format(medicine.getDateIssued()));
        this.medicineExpireFactor.setText(medicine.getExpireFactor() + "");
    }

    private void resetFields() {
        this.getArguments().putInt("id", Constant.MEDICINE_ADD_INVALID_ID);
        this.medicineName.setText("");
        this.medicineName.setError(null);
        this.medicineDescription.setText("");
        this.medicineDescription.setError(null);
        this.medicineCategory.setSelection(0);
        ((TextView) this.medicineCategory.getSelectedView()).setError(null);
        this.medicineRemindSwitch.setEnabled(false);
        this.medicineRemindSwitch.setChecked(false);
        this.medicineFrequencyLabel.setVisibility(View.GONE);
        this.medicineFrequency.setError(null);
        this.medicineFrequencyIntervalLabel.setVisibility(View.GONE);
        this.medicineFrequencyInterval.setError(null);
        this.medicineFrequencyStartTimeLabel.setVisibility(View.GONE);
        this.medicineFrequencyStartTime.setError(null);
        this.medicineThresholdLabel.setVisibility(View.VISIBLE);
        this.medicineThreshold.setError(null);
        this.medicineFrequency.setText("");
        this.medicineFrequency.setError(null);
        this.medicineFrequencyInterval.setText("");
        this.medicineFrequencyInterval.setError(null);
        this.medicineFrequencyStartTime.setText(dateFormatter.format(currentCal.getTime()));
        this.medicineFrequencyStartTime.setError(null);
        this.medicineQuantity.setText("");
        this.medicineQuantity.setError(null);
        this.medicineConsumeQuantity.setText("");
        this.medicineConsumeQuantity.setError(null);
        this.medicineDosage.setSelection(0);
        ((TextView) this.medicineDosage.getSelectedView()).setError(null);
        this.medicineDateIssue.setText(dateFormatter.format(currentCal.getTime()));
        this.medicineDateIssue.setError(null);
        this.medicineExpireFactor.setText("");
        this.medicineExpireFactor.setError(null);
        this.btnDelete.setVisibility(View.GONE);
    }

    private boolean isCommonValid() {
        boolean isValid = true;

        String medicineNameString = this.medicineName.getText().toString().trim();
        String medicineDescriptionString = this.medicineDescription.getText().toString().trim();
        String medicineFrequencyString = this.medicineFrequency.getText().toString().trim();
        String medicineFrequencyIntervalString = this.medicineFrequencyInterval.getText().toString().trim();
        String medicineFrequencyStartTimeString = this.medicineFrequencyStartTime.getText().toString().trim();
        String medicineQuantityString = this.medicineQuantity.getText().toString().trim();
        String medicineConsumeQuantityString = this.medicineConsumeQuantity.getText().toString().trim();
        String medicineThresholdString = this.medicineThreshold.getText().toString().trim();
        String medicineExpireFactorString = this.medicineExpireFactor.getText().toString().trim();
        String medicineDateIssueString = this.medicineDateIssue.getText().toString().trim();

        if (TextUtils.isEmpty(medicineNameString)) {
            this.medicineName.setError(getResources().getString(R.string.medicine_add_error_name_empty));
            isValid = false;
        }

        if (medicineNameString.length() > 50) {
            this.medicineName.setError(getResources().getString(R.string.medicine_add_error_name_length));
            isValid = false;
        }

        if (TextUtils.isEmpty(medicineDescriptionString)) {
            this.medicineDescription.setError(getResources().getString(R.string.medicine_add_error_description_empty));
            isValid = false;
        }

        if (medicineDescriptionString.length() > 255) {
            this.medicineDescription.setError(getResources().getString(R.string.medicine_add_error_description_length));
            isValid = false;
        }

        if (this.medicineCategory.getSelectedItemPosition() == 0) {
            ((TextView) this.medicineCategory.getSelectedView()).setError(getResources().getString(R.string.medicine_add_error_category_empty));
            isValid = false;
        }

        if (this.medicineFrequency.isShown() && TextUtils.isEmpty(medicineFrequencyString)) {
            this.medicineFrequency.setError(getResources().getString(R.string.medicine_add_error_frequency_empty));
            isValid = false;
        }

        if (this.medicineFrequency.isShown() && !medicineFrequencyString.equals("") && Integer.parseInt(medicineFrequencyString) < 0 && Integer.parseInt(medicineFrequencyString) > 1000) {
            this.medicineFrequency.setError(getResources().getString(R.string.medicine_add_error_frequency_length));
            isValid = false;
        }

        if (this.medicineFrequencyInterval.isShown() && TextUtils.isEmpty(medicineFrequencyIntervalString)) {
            this.medicineFrequencyInterval.setError(getResources().getString(R.string.medicine_add_error_frequency_interval_empty));
            isValid = false;
        }

        if (this.medicineFrequencyInterval.isShown() && !medicineFrequencyIntervalString.equals("") && Integer.parseInt(medicineFrequencyIntervalString) < 0 && Integer.parseInt(medicineFrequencyIntervalString) > 1000) {
            this.medicineFrequencyInterval.setError(getResources().getString(R.string.medicine_add_error_frequency_interval_length));
            isValid = false;
        }

        if (this.medicineFrequencyStartTime.isShown() && TextUtils.isEmpty(medicineFrequencyStartTimeString)) {
            this.medicineFrequencyStartTime.setError(getResources().getString(R.string.medicine_add_error_start_time_empty));
            isValid = false;
        } else {
            try {
                dateFormatter.parse(medicineFrequencyStartTimeString);
            } catch (ParseException ex) {
                this.medicineFrequencyStartTime.setError(getResources().getString(R.string.medicine_add_error_start_time_format));
                isValid = false;
            }
        }

        if (TextUtils.isEmpty(medicineQuantityString)) {
            this.medicineQuantity.setError(getResources().getString(R.string.medicine_add_error_quantity_empty));
            isValid = false;
        }

        if (!medicineQuantityString.equals("") && Integer.parseInt(medicineQuantityString) < 1 && Integer.parseInt(medicineQuantityString) > 1000) {
            this.medicineQuantity.setError(getResources().getString(R.string.medicine_add_error_quantity_length));
            isValid = false;
        }

        if (this.medicineDosage.getSelectedItemPosition() == 0) {
            ((TextView) this.medicineDosage.getSelectedView()).setError(getResources().getString(R.string.medicine_add_error_dosage_empty));
            isValid = false;
        }

        if (TextUtils.isEmpty(medicineConsumeQuantityString)) {
            this.medicineConsumeQuantity.setError(getResources().getString(R.string.medicine_add_error_consume_quantity_empty));
            isValid = false;
        }

        if (!medicineConsumeQuantityString.equals("") && Integer.parseInt(medicineConsumeQuantityString) < 1 && Integer.parseInt(medicineConsumeQuantityString) > 1000) {
            this.medicineConsumeQuantity.setError(getResources().getString(R.string.medicine_add_error_consume_quantity_length));
            isValid = false;
        }

        if (this.medicineThreshold.isShown() && TextUtils.isEmpty(medicineThresholdString)) {
            this.medicineThreshold.setError(getResources().getString(R.string.medicine_add_error_threshold_empty));
            isValid = false;
        }

        if (this.medicineThreshold.isShown() && !medicineThresholdString.equals("") && Integer.parseInt(medicineThresholdString) < 0 && Integer.parseInt(medicineThresholdString) > 1000) {
            this.medicineThreshold.setError(getResources().getString(R.string.medicine_add_error_threshold_length));
            isValid = false;
        }

        if (TextUtils.isEmpty(medicineDateIssueString)) {
            this.medicineDateIssue.setError(getResources().getString(R.string.medicine_add_error_date_issue_empty));
            isValid = false;
        } else {
            try {
                dateFormatter.parse(medicineDateIssueString);
            } catch (ParseException ex) {
                this.medicineDateIssue.setError(getResources().getString(R.string.medicine_add_error_date_issue_format));
                isValid = false;
            }
        }

        if (TextUtils.isEmpty(medicineExpireFactorString)) {
            this.medicineExpireFactor.setError(getResources().getString(R.string.medicine_add_error_expire_factor_empty));
            isValid = false;
        }

        if (!medicineExpireFactorString.equals("") && Integer.parseInt(medicineExpireFactorString) < 1 && Integer.parseInt(medicineExpireFactorString) > 24) {
            this.medicineExpireFactor.setError(getResources().getString(R.string.medicine_add_error_expire_factor_length));
            isValid = false;
        }

        return isValid;
    }

    private boolean isUpdateValid() {
        boolean isValid = true;

        // valida name not duplicate


        return isValid;
    }

    private boolean isNewValid() {
        boolean isValid = true;

        // Valid name no duplicate

        //Valid start date after today

        //Valid date issue after today


        return isValid;
    }
}
