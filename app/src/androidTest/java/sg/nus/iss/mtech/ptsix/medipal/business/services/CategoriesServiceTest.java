package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.CategoriesDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CategoriesServiceTest {

    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();

        CategoriesDao categoriesDao = new CategoriesDao(context);
        categoriesDao.open();
        categoriesDao.truncateAllCategories();
        categoriesDao.close();
    }

    @Test
    public void testMakeCategory() throws Exception {
        CategoriesService categoriesService = new CategoriesService(context);

        Categories category = new Categories();
        category.setCategory("First Test Category.");
        category.setDescription("This is FTC category");
        category.setCode("FTC");
        category.setRemind(0);
        Long category1ReturnValue = categoriesService.makeCategories(category);

        Categories category2 = new Categories();
        category2.setCategory("Second Test Category.");
        category2.setDescription("This is STC category");
        category2.setCode("STC");
        category2.setRemind(1);
        Long category2ReturnValue = categoriesService.makeCategories(category2);

        Categories category3 = new Categories();
        category3.setCategory("Third Test Category.");
        category3.setDescription("This is TTC category");
        category3.setCode("TTC");
        category3.setRemind(-1);
        Long category3ReturnValue = categoriesService.makeCategories(category3);

        Categories checkCategory1 = categoriesService.getCategories(1);
        Categories checkCategory2 = categoriesService.getCategories(2);
        Categories checkCategory3 = categoriesService.getCategories(3);

        assertEquals(Long.valueOf(1), category1ReturnValue);
        assertEquals(Long.valueOf(2), category2ReturnValue);
        assertEquals(Long.valueOf(3), category3ReturnValue);
        assertEquals("First Test Category.", checkCategory1.getCategory());
        assertEquals("This is FTC category", checkCategory1.getDescription());
        assertEquals("FTC", checkCategory1.getCode());
        assertEquals(0, checkCategory1.getRemind());
        assertEquals("Second Test Category.", checkCategory2.getCategory());
        assertEquals("This is STC category", checkCategory2.getDescription());
        assertEquals("STC", checkCategory2.getCode());
        assertEquals(1, checkCategory2.getRemind());
        assertEquals("Third Test Category.", checkCategory3.getCategory());
        assertEquals("This is TTC category", checkCategory3.getDescription());
        assertEquals("TTC", checkCategory3.getCode());
        assertEquals(-1, checkCategory3.getRemind());
    }

    @Test
    public void testUpdateCategory() throws Exception {
        CategoriesService categoriesService = new CategoriesService(context);

        Categories category = new Categories();
        category.setCategory("Testing Category.");
        category.setDescription("Testing category");
        category.setCode("TTT");
        category.setRemind(1);
        categoriesService.makeCategories(category);

        category = new Categories();
        category.setCategory("First Test Category.");
        category.setDescription("This is FTC category");
        category.setCode("FTC");
        category.setRemind(0);
        categoriesService.makeCategories(category);

        Categories checkCategory1 = categoriesService.getCategories(2);
        assertEquals("First Test Category.", checkCategory1.getCategory());
        assertEquals("This is FTC category", checkCategory1.getDescription());
        assertEquals("FTC", checkCategory1.getCode());
        assertEquals(0, checkCategory1.getRemind());

        checkCategory1.setCategory("Second Test Category.");
        checkCategory1.setDescription("This is STC category");
        checkCategory1.setCode("STC");
        checkCategory1.setRemind(1);
        Long category2ReturnValue = categoriesService.updateCategories(checkCategory1);

        checkCategory1 = categoriesService.getCategories(2);
        assertEquals(Long.valueOf(1), category2ReturnValue);
        assertEquals("Second Test Category.", checkCategory1.getCategory());
        assertEquals("This is STC category", checkCategory1.getDescription());
        assertEquals("STC", checkCategory1.getCode());
        assertEquals(1, checkCategory1.getRemind());

        checkCategory1.setCategory("Third Test Category.");
        checkCategory1.setDescription("This is TTC category");
        checkCategory1.setCode("TTC");
        checkCategory1.setRemind(-1);
        Long category3ReturnValue = categoriesService.updateCategories(checkCategory1);

        checkCategory1 = categoriesService.getCategories(2);
        assertEquals(Long.valueOf(1), category3ReturnValue);
        assertEquals("Third Test Category.", checkCategory1.getCategory());
        assertEquals("This is TTC category", checkCategory1.getDescription());
        assertEquals("TTC", checkCategory1.getCode());
        assertEquals(-1, checkCategory1.getRemind());
    }

    @Test
    public void testDeleteCategory() throws Exception {
        CategoriesService categoriesService = new CategoriesService(context);

        Categories category = new Categories();
        category.setCategory("Testing Category.");
        category.setDescription("Testing category");
        category.setCode("TTT");
        category.setRemind(1);
        Long category1ReturnValue = categoriesService.makeCategories(category);
        category.setId(category1ReturnValue.intValue());

        Long deleteSuccessValue = categoriesService.deleteCategories(category);
        assertEquals(Long.valueOf(1), deleteSuccessValue);

        deleteSuccessValue = categoriesService.deleteCategories(category);
        assertEquals(Long.valueOf(0), deleteSuccessValue);
    }

    @Test
    public void testGetCategoryList() throws Exception {
        CategoriesService categoriesService = new CategoriesService(context);

        Categories category = new Categories();
        category.setCategory("First Test Category.");
        category.setDescription("This is FTC category");
        category.setCode("FTC");
        category.setRemind(0);
        Long category1ReturnValue = categoriesService.makeCategories(category);

        Categories category2 = new Categories();
        category2.setCategory("Second Test Category.");
        category2.setDescription("This is STC category");
        category2.setCode("STC");
        category2.setRemind(1);
        Long category2ReturnValue = categoriesService.makeCategories(category2);

        Categories category3 = new Categories();
        category3.setCategory("Third Test Category.");
        category3.setDescription("This is TTC category");
        category3.setCode("TTC");
        category3.setRemind(-1);
        Long category3ReturnValue = categoriesService.makeCategories(category3);

        List<Categories> categoryList = categoriesService.getCategories();
        Categories checkCategory1 = categoryList.get(0);
        Categories checkCategory2 = categoryList.get(1);
        Categories checkCategory3 = categoryList.get(2);

        assertEquals(3, categoryList.size());
        assertEquals(Long.valueOf(1), category1ReturnValue);
        assertEquals(Long.valueOf(2), category2ReturnValue);
        assertEquals(Long.valueOf(3), category3ReturnValue);
        assertEquals("First Test Category.", checkCategory1.getCategory());
        assertEquals("This is FTC category", checkCategory1.getDescription());
        assertEquals("FTC", checkCategory1.getCode());
        assertEquals(0, checkCategory1.getRemind());
        assertEquals("Second Test Category.", checkCategory2.getCategory());
        assertEquals("This is STC category", checkCategory2.getDescription());
        assertEquals("STC", checkCategory2.getCode());
        assertEquals(1, checkCategory2.getRemind());
        assertEquals("Third Test Category.", checkCategory3.getCategory());
        assertEquals("This is TTC category", checkCategory3.getDescription());
        assertEquals("TTC", checkCategory3.getCode());
        assertEquals(-1, checkCategory3.getRemind());
    }

    @Test
    public void testGetCategoryByCode() throws Exception {
        CategoriesService categoriesService = new CategoriesService(context);

        Categories category = new Categories();
        category.setCategory("First Test Category.");
        category.setDescription("This is FTC category");
        category.setCode("FTC");
        category.setRemind(0);
        Long category1ReturnValue = categoriesService.makeCategories(category);

        List<Categories> categoryList = categoriesService.getCategoriesByCode("FTC");
        Categories checkCategory1 = categoryList.get(0);

        assertEquals(1, categoryList.size());
        assertEquals(Long.valueOf(1), category1ReturnValue);
        assertEquals(1, checkCategory1.getId());
        assertEquals("First Test Category.", checkCategory1.getCategory());
        assertEquals("This is FTC category", checkCategory1.getDescription());
        assertEquals("FTC", checkCategory1.getCode());
        assertEquals(0, checkCategory1.getRemind());
    }


}
