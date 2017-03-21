package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.HealthBio;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.HealthBioActivity;

/**
 * Created by JOHN on 3/14/2017.
 */

public class HealthBioAdaptor extends RecyclerView.Adapter<HealthBioAdaptor.HealthBioHolder> {
    private List<HealthBio> healthBioList;
    private int mExpandedPosition = -1;
    private Context mContext;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.getDefault());


    public class HealthBioHolder extends RecyclerView.ViewHolder {

        private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.getDefault());

        public TextView condition, conditionType, startDate;
        public Button btnEdit;

        public HealthBioHolder(View view) {
            super(view);
            condition = (TextView) view.findViewById(R.id.list_health_bio_condition);
            conditionType = (TextView) view.findViewById(R.id.list_health_bio_condition_type);
            startDate = (TextView) view.findViewById(R.id.list_health_bio_start_date);
            btnEdit = (Button)  view.findViewById(R.id.list_health_bio_btn_edit);
        }
    }

    public HealthBioAdaptor(List<HealthBio> healthBioList, Context mContext) {
        this.healthBioList = healthBioList;
        this.mContext = mContext;
    }

    @Override
    public HealthBioAdaptor.HealthBioHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_health_bio_list_row, parent, false);
        return new HealthBioHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HealthBioHolder holder, final int position) {
        final HealthBio healthBio = healthBioList.get(position);
        final int id = healthBio.getId();
        holder.condition.setText(healthBio.getEventCondition().toString());
        //String dateStr = dateFormatter.format(healthBio.getEventStartDate());
        holder.startDate.setText(dateFormatter.format(healthBio.getEventStartDate()));
        Log.i("ADAPTOR Date ", dateFormatter.format(healthBio.getEventStartDate()));
        holder.btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mContext instanceof HealthBioActivity){
                    ((HealthBioActivity)mContext).switchTab(1, healthBio.getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return healthBioList.size();
    }
}
