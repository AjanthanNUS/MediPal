package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

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
import sg.nus.iss.mtech.ptsix.medipal.presentation.util.Constant;

public class MedicineAddFragment extends Fragment {

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private Spinner medicineCategory, medicineDosage;
    private EditText medicineName, medicineDescription, medicineFrequency, medicineFrequencyInterval, medicineFrequencyStartTime, medicineThreshold, medicineQuantity, medicineConsumeQuantity;
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
        //this.medicineThresholdSwitch = (Switch) rootView.findViewById(R.id.medicine_threshold_switch);
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
                    medicineThresholdLabel.setVisibility(View.GONE);
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

                    if (cat.getCode().equals("CHR")) {
                        medicineThresholdLabel.setVisibility(View.VISIBLE);
                    } else {
                        medicineThresholdLabel.setVisibility(View.GONE);
                    }
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
//                    // Add new
//                    if (isCommonValid() && isNewValid()) {
//                        saveCategories();
//                        Toast.makeText(getActivity(), R.string.category_add_save_completed, Toast.LENGTH_SHORT).show();
//                        resetFields();
//                        ((CategoriesActivity) getActivity()).switchTab(Constant.CATEGORY_TAB_LIST_INDEX, Constant.CATEGORY_ADD_INVALID_ID);
//                    }
                } else {
//                    // Update
                    if (isCommonValid() && isUpdateValid()) {
                        updateMedicines();
                        Toast.makeText(getActivity(), R.string.category_add_update_completed, Toast.LENGTH_SHORT).show();
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
        Medicine medicine = createMedicineFromInput();
        medicine.setId(getArguments().getInt("id"));
        Long mediResult = medicineDao.update(medicine);

        if (mediResult == 1 && medicine.getRemind() == 1) {
            Reminders reminder = createReminderFromInput();
            reminder.setId(medicine.getReminderId());
            Long remindResult = remindersDao.update(reminder);
            return 1;

        } else if(mediResult == 1 && medicine.getRemind() == 0) {
            // Remove reminder
            Reminders reminder = createReminderFromInput();
            reminder.setId(medicine.getReminderId());
            remindersDao.delete(reminder);
            return 1;

        }

        return 0;

    }

    private long saveMedicines() {
        Medicine medicine = createMedicineFromInput();
        return medicineDao.save(medicine);
    }

    private Medicine createMedicineFromInput() {
        Medicine medicine = new Medicine();
        medicine.setMedicine(this.medicineName.getText().toString());
        medicine.setDescription(this.medicineDescription.getText().toString());
        medicine.setCatId(this.medicineCategory.getSelectedItemPosition());
        if (this.reminderSwitchValue) {
            medicine.setRemind(1);
        }
        else {
            medicine.setRemind(0);
        }
        medicine.setQuantity(Integer.parseInt(this.medicineQuantity.getText().toString()));
        medicine.setDosage(this.medicineDosage.getSelectedItemPosition());
        medicine.setConsumeQuantity(Integer.parseInt(this.medicineConsumeQuantity.getText().toString()));
        //if()


        return medicine;
    }

    private Reminders createReminderFromInput() {
        Reminders reminder = new Reminders();
        log.d(this.medicineFrequencyStartTime.getText().toString(), this.medicineFrequencyStartTime.getText().toString());
        // // TODO: 15/3/17  kknkn
        reminder.setEventStartTime(new Date());
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

            if (medicine.getThreshold() > 0) {
                this.medicineThreshold.setText(medicine.getThreshold() + "");
            } else {
                this.medicineThreshold.setText("");
            }
        } else {
            this.medicineRemindSwitch.setChecked(false);
        }
        this.medicineQuantity.setText(medicine.getQuantity() + "");
        this.medicineConsumeQuantity.setText(medicine.getConsumeQuantity() + "");
        this.medicineDosage.setSelection(medicine.getDosage());
        this.btnDelete.setVisibility(View.VISIBLE);
    }

    private void resetFields() {
        this.getArguments().putInt("id", Constant.MEDICINE_ADD_INVALID_ID);
        this.medicineName.setText("");
        this.medicineDescription.setText("");
        this.medicineCategory.setSelection(0);
        this.medicineRemindSwitch.setEnabled(false);
        this.medicineRemindSwitch.setChecked(false);
        this.medicineFrequencyLabel.setVisibility(View.GONE);
        this.medicineFrequencyIntervalLabel.setVisibility(View.GONE);
        this.medicineFrequencyStartTimeLabel.setVisibility(View.GONE);
        this.medicineThresholdLabel.setVisibility(View.GONE);
        this.medicineFrequency.setText("");
        this.medicineFrequencyInterval.setText("");
        this.medicineFrequencyStartTime.setText(dateFormatter.format(currentCal.getTime()));
        this.medicineQuantity.setText("");
        this.medicineConsumeQuantity.setText("");
        this.medicineDosage.setSelection(0);
        this.btnDelete.setVisibility(View.GONE);
    }

    private boolean isCommonValid() {
        boolean isValid = true;


        return isValid;
    }

    private boolean isUpdateValid() {
        boolean isValid = true;


        return isValid;
    }
}
