package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import sg.nus.iss.mtech.ptsix.medipal.R;

public class ICESMSFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // View v = inflater.inflate(R.layout.fragment, null);
        View v = inflater.inflate(R.layout.fragment_ice_contact_sms, container, false);
        return v;

    }


    public void sendSMS(View v) {

        //refernce the edittext
        //get the phone the number and the message

        String number = "0712345678";
        String msg = "Please call John. He is in emergency";
        //use the sms manager to send message
        SmsManager sm = SmsManager.getDefault();
        sm.sendTextMessage(number, null, msg, null, null);
        Toast.makeText(getActivity(), "Messege sent", Toast.LENGTH_LONG).show();


    }


}
