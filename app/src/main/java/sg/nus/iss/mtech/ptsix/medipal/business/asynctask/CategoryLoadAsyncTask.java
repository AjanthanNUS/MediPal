package sg.nus.iss.mtech.ptsix.medipal.business.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.services.CategoriesService;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.CategoriesAdapter;

public class CategoryLoadAsyncTask extends AsyncTask<Integer, Void, ArrayList<Categories>> {

    private RecyclerView recyclerView;
    private CategoriesAdapter mAdapter;
    private Context mContext;
    private CategoriesService categoriesService;

    public CategoryLoadAsyncTask(Context context, View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        categoriesService = new CategoriesService(context);
        mContext = context;
    }

    @Override
    protected ArrayList<Categories> doInBackground(Integer... params) {
        return categoriesService.getCategories();
    }

    @Override
    protected void onPostExecute(ArrayList<Categories> categoriesList) {
        mAdapter = new CategoriesAdapter(categoriesList, mContext);
        recyclerView.setAdapter(mAdapter);
    }
}
