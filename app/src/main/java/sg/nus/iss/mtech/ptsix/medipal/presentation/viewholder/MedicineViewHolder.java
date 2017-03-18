package sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sg.nus.iss.mtech.ptsix.medipal.R;

public class MedicineViewHolder extends RecyclerView.ViewHolder {

    public TextView medicine, remind, category, description, frequency, frequencyInterval, frequencyStartTime, quantity, dosage, consumeQuantity, threshold, dateIssued, expireFactor;
    public Button btnEdit;

    public MedicineViewHolder(View view) {
        super(view);
        this.medicine = (TextView) view.findViewById(R.id.medicine);
        this.remind = (TextView) view.findViewById(R.id.remind);
        this.category = (TextView) view.findViewById(R.id.category);
        this.description = (TextView) view.findViewById(R.id.description);
        this.frequency = (TextView) view.findViewById(R.id.frequency);
        this.frequencyInterval = (TextView) view.findViewById(R.id.frequency_interval);
        this.frequencyStartTime = (TextView) view.findViewById(R.id.frequency_start_time);
        this.quantity = (TextView) view.findViewById(R.id.quantity);
        this.dosage = (TextView) view.findViewById(R.id.dosage);
        this.consumeQuantity = (TextView) view.findViewById(R.id.consume_quantity);
        this.threshold = (TextView) view.findViewById(R.id.threshold);
        this.dateIssued = (TextView) view.findViewById(R.id.date_issued);
        this.expireFactor = (TextView) view.findViewById(R.id.expire_factor);

        this.btnEdit = (Button) view.findViewById(R.id.btn_edit);
    }
}
