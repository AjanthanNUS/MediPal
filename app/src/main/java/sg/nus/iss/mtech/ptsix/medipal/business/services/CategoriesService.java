package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;

import java.util.ArrayList;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.CategoriesDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;

public class CategoriesService {
    private Context context;

    public CategoriesService(Context context) {
        this.context = context;
    }

    public long makeCategories(Categories categories) {
        CategoriesDao categoriesDao = new CategoriesDao(context);
        long result = 0;

        categoriesDao.open();
        try {
            result = categoriesDao.save(categories);
            categories.setId((int) result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            categoriesDao.close();
        }

        return result;
    }

    public long updateCategories(Categories categories) {
        CategoriesDao categoriesDao = new CategoriesDao(context);
        long result = 0;

        categoriesDao.open();
        try {
            result = categoriesDao.update(categories);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            categoriesDao.close();
        }

        return result;
    }

    public long deleteCategories(Categories categories) {
        CategoriesDao categoriesDao = new CategoriesDao(context);
        long result = 0;

        categoriesDao.open();
        try {
            result = categoriesDao.delete(categories);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            categoriesDao.close();
        }

        return result;
    }

    public Categories getCategories(int categoriesId) {
        CategoriesDao categoriesDao = new CategoriesDao(context);
        Categories categories = null;

        categoriesDao.open();
        try {
            categories = categoriesDao.getCategories(categoriesId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            categoriesDao.close();
        }

        return categories;
    }

    public ArrayList<Categories> getCategories() {
        CategoriesDao categoriesDao = new CategoriesDao(context);
        ArrayList<Categories> ret = new ArrayList<>();

        categoriesDao.open();
        try {
            ret = categoriesDao.getCategories();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            categoriesDao.close();
        }

        return ret;
    }
}
