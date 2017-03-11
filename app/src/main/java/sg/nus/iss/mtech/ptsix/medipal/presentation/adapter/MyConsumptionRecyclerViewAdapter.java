package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.ConsumptionListFragment.OnListFragmentInteractionListener;


/**
 * @author Ajanthan
 */
public class MyConsumptionRecyclerViewAdapter extends RecyclerView.Adapter<MyConsumptionRecyclerViewAdapter.ViewHolder> {

    private final List<Consumption> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyConsumptionRecyclerViewAdapter(List<Consumption> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_consumption_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Consumption cItem = mValues.get(position);
        holder.consumptionItem = cItem;

        //holder.consumedOnDateView.setText(CommonUtil.formatCalender(cItem.getConsumedOn()));
        //holder.medicineNameView.setText(cItem.getMedicine().getName());
        //holder.consumedQuantityView.setText(cItem.getQuantity().toString());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.consumptionItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView consumedOnDateView;
        public final TextView medicineNameView;
        public final TextView consumedQuantityView;

        public Consumption consumptionItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            consumedOnDateView = (TextView) view.findViewById(R.id.consumedOnDate);
            medicineNameView = (TextView) view.findViewById(R.id.medicineName);
            consumedQuantityView = (TextView) view.findViewById(R.id.consumedQuantity);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + medicineNameView.getText() + "'";
        }
    }
}
