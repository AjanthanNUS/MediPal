package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.services.CategoriesService;
import sg.nus.iss.mtech.ptsix.medipal.business.services.RemindersService;
import sg.nus.iss.mtech.ptsix.medipal.common.enums.DosageEnums;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MedicineActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder.MedicineViewHolder;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicineViewHolder> {

    private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.getDefault());
    private SimpleDateFormat timeFormatter = new SimpleDateFormat(Constant.TIME_FORMAT, Locale.getDefault());
    private List<Medicine> medicinesList;
    private int mExpandedPosition = -1;
    private CategoriesService categoriesService;
    private RemindersService remindersService;
    private Context mContext;

    public MedicinesAdapter(List<Medicine> medicinesList, Context context) {
        this.medicinesList = medicinesList;
        this.categoriesService = new CategoriesService(context);
        this.remindersService = new RemindersService(context);
        this.mContext = context;
    }

    @Override
    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_medicine_list_row, parent, false);
        return new MedicineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MedicineViewHolder holder, final int position) {
        final Medicine medicine = medicinesList.get(position);
        final Categories category = this.categoriesService.getCategories(medicine.getCatId());
        final Reminders reminder = this.remindersService.getReminders(medicine.getReminderId());

        holder.medicine.setText(medicine.getMedicine());
        holder.category.setText(this.mContext.getResources().getString(R.string.medicine_list_category, category.getCategory()));
        holder.description.setText(this.mContext.getResources().getString(R.string.medicine_list_description, medicine.getDescription()));
        if (medicine.getRemind() == 1) {
            holder.remind.setText(R.string.medicine_list_remind_on);
            holder.frequency.setText(this.mContext.getResources().getString(R.string.medicine_list_frequency, reminder.getFrequency()));
            holder.frequencyInterval.setText(this.mContext.getResources().getString(R.string.medicine_list_frequency_interval, reminder.getInterval()));
            holder.frequencyStartTime.setText("Start Date:" + dateFormatter.format(reminder.getStartTime()).toString());
        } else if (medicine.getRemind() == 0) {
            holder.remind.setText(R.string.medicine_list_remind_off);
            holder.frequency.setVisibility(View.GONE);
            holder.frequencyInterval.setVisibility(View.GONE);
            holder.frequencyStartTime.setVisibility(View.GONE);
        }

        holder.quantity.setText(this.mContext.getResources().getString(R.string.medicine_list_quantity_left, medicine.getQuantity()));
        holder.dosage.setText(this.mContext.getResources().getString(R.string.medicine_list_dosage, DosageEnums.getDosageFromIntValue(medicine.getDosage())));
        holder.consumeQuantity.setText(this.mContext.getResources().getString(R.string.medicine_list_consume_quantity, medicine.getConsumeQuantity()));
        if (medicine.getThreshold() >= 0) {
            holder.threshold.setText(this.mContext.getResources().getString(R.string.medicine_list_threshold_required, medicine.getThreshold()));
        } else {
            holder.threshold.setText(this.mContext.getResources().getString(R.string.medicine_list_threshold_required_not));
        }
        holder.dateIssued.setText("Date Issued: " + dateFormatter.format(medicine.getDateIssued()));
        holder.expireFactor.setText("Expire in: " + medicine.getExpireFactor() + " months");

        // Handle edit button
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof MedicineActivity) {
                    ((MedicineActivity) mContext).switchTab(Constant.MEDICINE_TAB_ADD_INDEX, medicine.getId());
                }
            }
        });

        // Expandable part
        final boolean isExpanded = position == mExpandedPosition;
        holder.description.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.remind.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.quantity.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.dosage.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.consumeQuantity.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.threshold.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.dateIssued.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.expireFactor.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);
        if(medicine.getRemind() == 1) {
            holder.frequency.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            holder.frequencyInterval.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            holder.frequencyStartTime.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicinesList.size();
    }

    public void updateDataSet(List<Medicine> medicinesList) {
        this.medicinesList.clear();
        this.medicinesList.addAll(medicinesList);
        notifyDataSetChanged();
    }
}
