package org.atiuleneva;

import com.github.javafaker.Faker;
import lombok.NonNull;
import org.atiuleneva.db.dao.CategoriesMapper;
import org.atiuleneva.db.dao.ProductsMapper;
import org.atiuleneva.db.model.Categories;
import org.atiuleneva.db.model.CategoriesExample;
import org.atiuleneva.db.model.Products;
import org.atiuleneva.db.model.ProductsExample;
import org.atiuleneva.utils.DbUtils;

public class Main {
    static Faker faker = new Faker();

    private static  String resource = "mybatisConfig.xml";
    public static void main(String[] args)  {

        CategoriesMapper categoriesMapper = DbUtils.getCategoriesMapper();
        ProductsMapper productsMapper = DbUtils.getProductsMapper();


        long categoriesNumber = countCategoriesNumber(categoriesMapper);


//        deleteProductById(productsMapper);

//        Categories newCategory = new Categories();
//        newCategory.setTitle(faker.artist().name());
//        long categoryNumber = (categoriesNumber + 1);
//        newCategory.setId((int) categoryNumber);
//        categoriesMapper.insert(newCategory);
//
//        productsMapper.insert(new Products(faker.commerce().productName(), 7777, categoryNumber));
//        productsMapper.insert(new Products(faker.commerce().productName(), 7117, categoryNumber));

    }


    private static long countCategoriesNumber(CategoriesMapper categoriesMapper) {
        long categoriesCount = categoriesMapper.countByExample(new CategoriesExample());
        System.out.println("Количество категорий: " + categoriesCount);
        return categoriesCount;
    }

    private static void deleteProductById(ProductsMapper productsMapper) {
        Products product3101 = productsMapper.selectByPrimaryKey(3200L);
        productsMapper.deleteByPrimaryKey(product3101.getId());
    }

}
