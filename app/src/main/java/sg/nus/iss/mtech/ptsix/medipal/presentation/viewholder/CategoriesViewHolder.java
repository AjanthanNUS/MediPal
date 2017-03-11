package sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sg.nus.iss.mtech.ptsix.medipal.R;

public class CategoriesViewHolder extends RecyclerView.ViewHolder {

    public TextView code, category, remind, description;
    public Button btnEdit;

    public CategoriesViewHolder(View view) {
        super(view);
        code = (TextView) view.findViewById(R.id.code);
        category = (TextView) view.findViewById(R.id.category);
        remind = (TextView) view.findViewById(R.id.remind);
        description = (TextView) view.findViewById(R.id.description);
        btnEdit = (Button) view.findViewById(R.id.btn_edit);
    }
}
