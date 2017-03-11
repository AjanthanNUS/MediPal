package sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sg.nus.iss.mtech.ptsix.medipal.R;

public class MedicineViewHolder extends RecyclerView.ViewHolder {

    public TextView medicine, remind, category, description, frequency, quantity, dosage, consumeQuantity, threshold, dateIssued, expireFactor;
    public Button btnEdit;

    public MedicineViewHolder(View view) {
        super(view);
        medicine = (TextView) view.findViewById(R.id.medicine);
        remind = (TextView) view.findViewById(R.id.remind);
        category = (TextView) view.findViewById(R.id.category);
        description = (TextView) view.findViewById(R.id.description);
        frequency = (TextView) view.findViewById(R.id.frequency);
        quantity = (TextView) view.findViewById(R.id.quantity);
        dosage = (TextView) view.findViewById(R.id.dosage);
        consumeQuantity = (TextView) view.findViewById(R.id.consume_quantity);
        threshold = (TextView) view.findViewById(R.id.threshold);
        dateIssued = (TextView) view.findViewById(R.id.date_issued);
        expireFactor = (TextView) view.findViewById(R.id.expire_factor);

        btnEdit = (Button) view.findViewById(R.id.btn_edit);
    }
}
