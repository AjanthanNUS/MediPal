package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentDetailActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.AppointmentAdapter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AppointmentFragment extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private AppointmentAdapter mAppointmentAdapter;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AppointmentFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AppointmentFragment newInstance(int columnCount) {
        AppointmentFragment fragment = new AppointmentFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAppointmentAdapter = new AppointmentAdapter(getContext(), new ArrayList<Appointment>());

        View rootView = inflater.inflate(R.layout.fragment_appointment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_appointment);
        listView.setAdapter(mAppointmentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Appointment appointment = mAppointmentAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), AppointmentDetailActivity.class).putExtra("appointment", appointment);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabAppointment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AppointmentDetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, "");
                startActivity(intent);
            }
        });

        return rootView;
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


    public List<Appointment> generateDummyAppointments() {
        List<Appointment> appointments = new ArrayList<>();

        Date d = new Date();
        Appointment appointment1 = new Appointment("Tampines", "Go for the monthly checkup", d);
        Appointment appointment2 = new Appointment("Bedok", "Go for GP", d);
        Appointment appointment3 = new Appointment("QueensTown", "Queens Town Clincic", d);

        appointments.add(appointment1);
        appointments.add(appointment2);
        appointments.add(appointment3);
        return  appointments;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Appointment item);
    }
}
