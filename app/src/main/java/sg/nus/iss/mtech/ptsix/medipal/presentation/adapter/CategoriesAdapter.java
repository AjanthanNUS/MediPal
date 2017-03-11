package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.CategoriesActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder.CategoriesViewHolder;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {

    private List<Categories> categoriesList;
    private int mExpandedPosition = -1;
    private Context mContext;

    public CategoriesAdapter(List<Categories> categoriesList, Context context) {
        this.categoriesList = categoriesList;
        this.mContext = context;
    }

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_categories_list_row, parent, false);
        return new CategoriesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoriesViewHolder holder, final int position) {
        final Categories category = categoriesList.get(position);
        holder.code.setText(category.getEventCode().toString());
        holder.category.setText(category.getEventCategory().toString());
        holder.description.setText("Description: " + category.getEventDescription().toString());
        if (category.getEventRemind() >= 1) {
            holder.remind.setText("(Reminder On)");
        } else {
            holder.remind.setText("(Reminder Off)");
        }

        // Handle edit button
        holder.btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mContext instanceof CategoriesActivity){
                    ((CategoriesActivity)mContext).switchTab(1, category.getId());
                }
            }
        });

        // Expandable part
        final boolean isExpanded = position == mExpandedPosition;
        holder.description.setVisibility(isExpanded?View.VISIBLE:View.GONE);
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
        return categoriesList.size();
    }
}
