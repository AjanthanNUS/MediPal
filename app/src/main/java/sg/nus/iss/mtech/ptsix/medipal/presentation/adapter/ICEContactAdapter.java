package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.ICE;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ICEContactActivity;

/**
 * Created by JOHN on 3/12/2017.
 */

public class ICEContactAdapter extends RecyclerView.Adapter<ICEContactAdapter.ICEViewHolder> {

    private List<ICE> iceList;
    private int mExpandedPosition = -1;
    private Context mContext;

    public class ICEViewHolder extends RecyclerView.ViewHolder {

        public TextView name, contactNo, contactType, description, sequence;
        public Button btnEdit,btnCall,btnSMS;

        public ICEViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.ice_list_name);
            contactNo = (TextView) view.findViewById(R.id.ice_contact_no);

            description = (TextView) view.findViewById(R.id.description);
            btnCall = (Button) view.findViewById(R.id.list_btn_call);
            btnSMS = (Button) view.findViewById(R.id.list_btn_sms);
            btnEdit = (Button)  view.findViewById(R.id.list_btn_edit);
        }
    }

    public ICEContactAdapter(List<ICE> iceList, Context context) {
        this.iceList = iceList;
        Log.i(String.valueOf(iceList.size()), "List  Size");
        this.mContext = context;
    }

    @Override
    public ICEContactAdapter.ICEViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_ice_list_row, parent, false);
        return new ICEViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ICEViewHolder holder, final int position) {

        ICE ice = iceList.get(position);
        holder.name.setText(ice.getName().toString());
        final int id = ice.getId();
        final String contactNo = ice.getContactNo();
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i("Befor Button Call", "BBC");
                call(contactNo);
            }
        });

        holder.btnSMS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMS(view, contactNo);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mContext instanceof ICEContactActivity){
                    ((ICEContactActivity)mContext).switchTab(1, 1);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return iceList.size();
    }

    private void call(String contactNumber) {

        Intent call = new Intent(Intent.ACTION_DIAL);
        call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        call.setData(Uri.parse("tel:" + contactNumber));
        mContext.startActivity(call);
    }

    public void sendSMS(View v, String number) {
        //get the phone the number and the message
        String msg="Please call ABCD, He is in emergency";
        //use the sms manager to send message
        SmsManager sm=SmsManager.getDefault();
        sm.sendTextMessage(number, null, msg, null, null);
        Toast.makeText(mContext,"Messege sent",Toast.LENGTH_LONG).show();
    }

}
