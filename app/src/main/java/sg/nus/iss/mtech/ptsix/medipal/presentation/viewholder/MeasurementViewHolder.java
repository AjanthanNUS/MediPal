package sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sg.nus.iss.mtech.ptsix.medipal.R;

public class MeasurementViewHolder extends RecyclerView.ViewHolder {

    public TextView MeaID, MeaSystolic, MeaDiastolic,MeaPulse,MeaTemperature,MeaWeight,MeaMeasureOn,MeaComment ;
    public Button btnDel;

    public MeasurementViewHolder(View view) {
        super(view);
        MeaID = (TextView) view.findViewById(R.id.MeaID);
        MeaSystolic = (TextView) view.findViewById(R.id.MeaSystolic);
        MeaDiastolic = (TextView) view.findViewById(R.id.MeaDiastolic);
        MeaPulse = (TextView) view.findViewById(R.id.MeaPulse);
        MeaTemperature = (TextView) view.findViewById(R.id.MeaTemperature);
        MeaWeight = (TextView) view.findViewById(R.id.MeaWeight);
        MeaMeasureOn =(TextView) view.findViewById(R.id.MeaMeasureOn);
        MeaComment =(TextView) view.findViewById(R.id.MeaComment);
        this.btnDel = (Button) view.findViewById(R.id.btn_Mea_Delete);
    }
}
