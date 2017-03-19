package sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sg.nus.iss.mtech.ptsix.medipal.R;

public class CategoriesViewHolder extends RecyclerView.ViewHolder {

    public TextView code, category, remindOn, remindOff, description;
    public Button btnEdit;

    public CategoriesViewHolder(View view) {
        super(view);
        this.code = (TextView) view.findViewById(R.id.code);
        this.category = (TextView) view.findViewById(R.id.category);
        this.remindOn = (TextView) view.findViewById(R.id.remind_on);
        this.remindOff = (TextView) view.findViewById(R.id.remind_off);
        this.description = (TextView) view.findViewById(R.id.description);

        this.btnEdit = (Button) view.findViewById(R.id.btn_edit);
    }
}
