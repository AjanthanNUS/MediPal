package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.CategoriesDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MedicineActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder.MedicineViewHolder;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicineViewHolder>  {

    private List<Medicine> medicinesList;
    private int mExpandedPosition = -1;
    private CategoriesDao categoriesDao;
    private Context mContext;

    public MedicinesAdapter(List<Medicine> medicinesList, Context context) {
        this.medicinesList = medicinesList;
        this.categoriesDao = new CategoriesDao(context);
        this.mContext = context;
    }

    @Override
    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_medicine_list_row, parent, false);
        return new MedicineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MedicineViewHolder holder, final int position) {
        Medicine medicine = medicinesList.get(position);
        holder.medicine.setText(medicine.getEventMedicine());
        Categories category = this.categoriesDao.getCategories(medicine.getEventCatID());

        holder.category.setText(category.getEventCategory());
        holder.description.setText("Description: " + medicine.getEventDescription());
        holder.remind.setText("Remind: " + medicine.getEventRemindEnabled());
        holder.frequency.setText("Frequency: 3 times/day" );
        holder.quantity.setText("Quantity: " + medicine.getEventQuantity() + " issued");
        holder.dosage.setText("Dosage: " + medicine.getEventDosage());
        holder.consumeQuantity.setText("Consume Quantity: " + medicine.getEventConsumeQuantity() + " each time");
        if (category.getEventCode().equals("CHR")) {
            holder.threshold.setText("Threshold: If falls below " + medicine.getEventThreshold());
        }
        else {
            holder.threshold.setText("Threshold: Not required");
        }
        holder.dateIssued.setText("Date Issued: " + medicine.getEventDateIssued());
        holder.expireFactor.setText("Expire in: " + medicine.getEventExpireFactor() + " months");


        // Handle edit button
        holder.btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mContext instanceof MedicineActivity){
                    ((MedicineActivity)mContext).switchTab(1);
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
}
