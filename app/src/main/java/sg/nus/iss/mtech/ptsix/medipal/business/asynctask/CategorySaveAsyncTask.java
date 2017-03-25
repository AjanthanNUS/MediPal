package sg.nus.iss.mtech.ptsix.medipal.business.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import sg.nus.iss.mtech.ptsix.medipal.business.services.CategoriesService;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;

public class CategorySaveAsyncTask extends AsyncTask<Categories, Void, Void> {
    private CategoriesService categoriesService;
    private Context mContext;

    public CategorySaveAsyncTask(Context context) {
        this.categoriesService = new CategoriesService(context);
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(Categories... arg0) {
        this.categoriesService.makeCategories(arg0[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
