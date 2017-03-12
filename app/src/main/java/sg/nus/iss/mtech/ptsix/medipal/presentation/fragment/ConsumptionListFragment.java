package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.manager.ConsumptionManager;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ConsumptionVO;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ConsumptionViewAdapter;

/**
 * @author Ajanthan
 */
public class ConsumptionListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ConsumptionViewAdapter consumptionViewAdapter;
    private List<ConsumptionVO> consumptionList;

    public ConsumptionListFragment() {
    }

    @SuppressWarnings("unused")
    public static ConsumptionListFragment newInstance(int columnCount) {
        ConsumptionListFragment fragment = new ConsumptionListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        consumptionList = new ArrayList<>();
        ConsumptionManager consumptionManager = new ConsumptionManager();
        consumptionList.addAll(consumptionManager.getAllConsumptionList());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumption_list_view, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            consumptionViewAdapter = new ConsumptionViewAdapter(consumptionList, mListener);
            recyclerView.setAdapter(consumptionViewAdapter);
            //   Drawable dividerDrawable = ContextCompat.getDrawable(container.getContext(), R.drawable.divider_horizontal);
            //   RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(dividerDrawable);
            //   recyclerView.addItemDecoration(dividerItemDecoration);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ConsumptionVO consumption);
    }

    public ConsumptionViewAdapter getConsumptionViewAdapter() {
        return consumptionViewAdapter;
    }
}
