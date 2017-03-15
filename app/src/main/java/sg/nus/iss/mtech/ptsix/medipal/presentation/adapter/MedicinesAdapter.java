package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.CategoriesDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.RemindersDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MedicineActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder.MedicineViewHolder;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicineViewHolder>  {

    private List<Medicine> medicinesList;
    private int mExpandedPosition = -1;
    private CategoriesDao categoriesDao;
    private RemindersDao remindersDao;
    private Context mContext;

    public MedicinesAdapter(List<Medicine> medicinesList, Context context) {
        this.medicinesList = medicinesList;
        this.categoriesDao = new CategoriesDao(context);
        this.remindersDao = new RemindersDao(context);
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
        final Categories category = this.categoriesDao.getCategories(medicine.getCatId());
        final Reminders reminder = this.remindersDao.getReminders(medicine.getReminderId());

        holder.medicine.setText(medicine.getMedicine());
        holder.category.setText(this.mContext.getResources().getString(R.string.medicine_list_category, category.getCategory()));
        holder.description.setText(this.mContext.getResources().getString(R.string.medicine_list_description, medicine.getDescription()));
        if (medicine.getRemind() == 1) {
            holder.remind.setText(R.string.medicine_list_remind_on);
        }
        else if (medicine.getRemind() == 0) {
            holder.remind.setText(R.string.medicine_list_remind_off);
        }

        holder.frequency.setText(this.mContext.getResources().getString(R.string.medicine_list_frequency, reminder.getFrequency()));
        holder.quantity.setText(this.mContext.getResources().getString(R.string.medicine_list_quantity_left, medicine.getQuantity()));
        holder.dosage.setText(this.mContext.getResources().getString(R.string.medicine_list_dosage, medicine.getDosage()));
        holder.consumeQuantity.setText(this.mContext.getResources().getString(R.string.medicine_list_consume_quantity, medicine.getConsumeQuantity()));
        if (category.getCode().equals("CHR")) {
            holder.threshold.setText(this.mContext.getResources().getString(R.string.medicine_list_threshold_required, medicine.getThreshold()));
        }
        else {
            holder.threshold.setText(this.mContext.getResources().getString(R.string.medicine_list_threshold_required_not));
        }
        holder.dateIssued.setText("Date Issued: " + medicine.getDateIssued());
        holder.expireFactor.setText("Expire in: " + medicine.getExpireFactor() + " months");


        // Handle edit button
        holder.btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mContext instanceof MedicineActivity){
                    ((MedicineActivity)mContext).switchTab(Constant.MEDICINE_TAB_ADD_INDEX, medicine.getId());
                }
            }
        });

        // Expandable part
        final boolean isExpanded = position == mExpandedPosition;
        holder.description.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.remind.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.frequency.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.quantity.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.dosage.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.consumeQuantity.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.threshold.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.dateIssued.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.expireFactor.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? - 1: position;
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
