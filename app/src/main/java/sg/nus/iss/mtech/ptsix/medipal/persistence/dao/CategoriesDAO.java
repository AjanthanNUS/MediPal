package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;

public class CategoriesDAO extends DBDAO {

    public CategoriesDAO(Context context) {
        super(context);
    }

    public List<Categories> getCategories() {
        List<Categories> categoriesList = generateFakeData();
        return categoriesList;
    }

    public long save(Categories category) {
        ContentValues values = new ContentValues();
        return 1L;
    }

    public List<Categories> getCategoriesByCode(String code) {
        List<Categories> categoriesList = generateFakeData();

        List<Categories> newCategoriesList = new ArrayList<>();
        for (Categories category : categoriesList) {
            if (category.getCode() != null && category.getCode().equals(code)) {
                newCategoriesList.add(category);
            }
        }

        return newCategoriesList;
    }

    public String getCategoriesCodeById(int id) {
        List<Categories> categoriesList = generateFakeData();

        List<Categories> newCategoriesList = new ArrayList<>();
        for (Categories category : categoriesList) {
            if (category.getId().equals(id)) {
                return category.getCode();
            }
        }

        return "Code not found";
    }

    public String getCategoriesNameById(int id) {
        List<Categories> categoriesList = generateFakeData();

        List<Categories> newCategoriesList = new ArrayList<>();
        for (Categories category : categoriesList) {
            if (category.getId().equals(id)) {
                return category.getCategory();
            }
        }

        return "Name not found";
    }

    public Categories getCategoriesById(int id) {
        List<Categories> categoriesList = generateFakeData();

        List<Categories> newCategoriesList = new ArrayList<>();
        for (Categories category : categoriesList) {
            if (category.getId().equals(id)) {
                return category;
            }
        }

        return null;
    }

    private List<Categories> generateFakeData() {
        List<Categories> categoriesList = new ArrayList<>();

        Categories category = new Categories();
        category.setId(1);
        category.setCategory("Supplement");
        category.setCode("SUP");
        category.setDescription("User may opt to set reminder for consumption of supplement.");
        category.setRemind(false);
        categoriesList.add(category);

        category = new Categories();
        category.setId(2);
        category.setCode("CHR");
        category.setCategory("Chronic");
        category.setDescription("This is to categorise medication for long-term/life-time consumption for diseases, i.e. diabetes, hypertension, heart regulation, etc.");
        category.setRemind(true);
        categoriesList.add(category);

        category = new Categories();
        category.setId(3);
        category.setCode("INC");
        category.setCategory("Incidental");
        category.setDescription("For common cold, flu or symptoms happen to be unplanned or subordinate conjunction with something and prescription from general practitioners.");
        category.setRemind(true);
        categoriesList.add(category);

        category = new Categories();
        category.setId(4);
        category.setCode("COM");
        category.setCategory("Complete Course");
        category.setDescription("This may applies to medication like antibiotics for sinus infection, pneumonia, bronchitis, acne, strep throat, cellulitis, etc.");
        category.setRemind(true);
        categoriesList.add(category);

        category = new Categories();
        category.setId(5);
        category.setCode("SEL");
        category.setCategory("Self Apply");
        category.setDescription("To note down any self-prescribed or consume medication, i.e. applying band aids, balms, etc.");
        //category.setRemind(null);
        categoriesList.add(category);

        return categoriesList;
    }
}
