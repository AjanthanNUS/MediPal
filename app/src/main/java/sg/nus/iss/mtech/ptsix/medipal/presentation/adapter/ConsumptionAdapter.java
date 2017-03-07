package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.manager.ConsumptionManager;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;

/**
 * Created by Ajanthan on 3/5/2017.
 */

public class ConsumptionAdapter extends ArrayAdapter<Consumption> {

    private List<Consumption> consumptionList;
    private ConsumptionManager cManager;
    private int layoutResourceId;

    public ConsumptionAdapter(Context context, int resource, List<Consumption> consumptionList) {
        super(context, resource, consumptionList);
        this.layoutResourceId = resource;
        this.consumptionList = consumptionList;

    }

//    public ConsumptionAdapter(Context context, int layoutResourceId, int resource) {
//        super(context, resource);
//        this.context = context;
//
//        this.consumptionList = new ArrayList<>();
//        this.cManager = new ConsumptionManager();
//        this.consumptionList.addAll(cManager.getAllConsumptionList());
//    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View rowView = convertView;
        ConsumptionViewHolder viewHolder = null;
        if (rowView == null) {

            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            rowView = inflater.inflate(layoutResourceId, parent, false);

            viewHolder = new ConsumptionViewHolder();
            viewHolder.medicine = (TextView) rowView.findViewById(R.id.medicineName);
            viewHolder.quantity = (TextView) rowView.findViewById(R.id.consumedQuantity);
            viewHolder.consumedOn = (TextView) rowView.findViewById(R.id.consumedOnDate);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ConsumptionViewHolder) rowView.getTag();
        }

        Consumption consumption = consumptionList.get(position);

        viewHolder.medicine.setText(consumption.getMedicine().getName());
        viewHolder.quantity.setText(consumption.getQuantity());
        viewHolder.consumedOn.setText(consumption.getConsumedOn().toString());
        //return the row view.
        return convertView;

    }


    public int getItemCount() {
        return consumptionList.size();
    }

    static class ConsumptionViewHolder {
        public TextView consumedOn;
        public TextView medicine;
        public TextView quantity;
    }
}
