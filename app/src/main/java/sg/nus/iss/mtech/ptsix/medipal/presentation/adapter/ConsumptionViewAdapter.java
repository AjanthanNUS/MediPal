package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ConsumptionVO;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.ConsumptionListFragment.OnListFragmentInteractionListener;


/**
 * @author Ajanthan
 */
public class ConsumptionViewAdapter extends RecyclerView.Adapter<ConsumptionViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private List<ConsumptionVO> mConsumptionList;
    private int mExpandedPosition = -1;
    private Context context;

    public ConsumptionViewAdapter(List<ConsumptionVO> items, OnListFragmentInteractionListener listener) {
        mConsumptionList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_consumption_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ConsumptionVO cItem = mConsumptionList.get(position);
        holder.consumptionItem = cItem;

        holder.consumedOnDateView.setText(CommonUtil.formatDateStandard(cItem.getConsumedOn()));
        holder.medicineNameView.setText(cItem.getMedicine().getMedicine());
        holder.consumedQuantityView.setText((cItem.getQuantity() == 0) ? context.getResources().getString(R.string.skipped) :
                String.valueOf(cItem.getQuantity()));
        holder.description.setText(cItem.getMedicine().getDescription());
        holder.smileyImage.setImageResource((cItem.getQuantity() == 0) ? R.drawable.ic_action_sad : R.drawable.ic_blue_happy_smily);

        final boolean isExpanded = position == mExpandedPosition;
        holder.descriptionHolder.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mConsumptionList.size();
    }

    public List<ConsumptionVO> getmConsumptionList() {
        return mConsumptionList;
    }

    public void setmConsumptionList(List<ConsumptionVO> mConsumptionList) {
        this.mConsumptionList = mConsumptionList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView consumedOnDateView;
        public final TextView medicineNameView;
        public final TextView consumedQuantityView;
        public final TextView description;
        public final ImageView smileyImage;
        public final LinearLayout descriptionHolder;

        public ConsumptionVO consumptionItem;

        public ViewHolder(View view) {
            super(view);
            consumedOnDateView = (TextView) view.findViewById(R.id.consumedOnDate);
            medicineNameView = (TextView) view.findViewById(R.id.medicineName);
            consumedQuantityView = (TextView) view.findViewById(R.id.consumedQuantity);
            description = (TextView) view.findViewById(R.id.tv_description);
            smileyImage = (ImageView) view.findViewById(R.id.smiley_image);
            descriptionHolder = (LinearLayout) view.findViewById(R.id.description_holder);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + medicineNameView.getText() + "'";
        }
    }
}
