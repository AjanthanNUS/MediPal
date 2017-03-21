package sg.nus.iss.mtech.ptsix.medipal.business.asynctask;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import sg.nus.iss.mtech.ptsix.medipal.business.services.CategoriesService;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;

public class CategorySaveAsyncTask extends AsyncTask<Categories, Void, Long> {
    private final WeakReference<Activity> activityWeakRef;
    private CategoriesService categoriesService;

    public CategorySaveAsyncTask(Activity context) {
        this.activityWeakRef = new WeakReference<Activity>(context);
        categoriesService = new CategoriesService(context);
    }

    @Override
    protected Long doInBackground(Categories... arg0) {
        return categoriesService.makeCategories(arg0[0]);
    }

}
