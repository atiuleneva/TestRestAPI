import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.atiuleneva.db.dao.CategoriesMapper;
import org.atiuleneva.db.dao.ProductsMapper;
import org.atiuleneva.db.model.Categories;
import org.atiuleneva.db.model.Products;
import org.atiuleneva.utils.DbUtils;
import org.atiuleneva.utils.TestDataSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.*;
import org.atiuleneva.enums.CategoryType;
import org.atiuleneva.dto.ErrorBody;
import org.atiuleneva.dto.Product;
import org.atiuleneva.service.ProductService;
import org.atiuleneva.utils.RetrofitUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.*;

public class ProductTests {
    Long productId;
    Faker faker = new Faker();
    static ProductService productService;
    static ProductsMapper productsMapper;
    static CategoriesMapper categoriesMapper;
    Product product;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() throws MalformedURLException {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
        productsMapper = DbUtils.getProductsMapper();
        categoriesMapper = DbUtils.getCategoriesMapper();
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withCategoryTitle(CategoryType.FOOD.title)
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());
    }

    @SneakyThrows
    @Test
    void getSugarProductTest() throws IOException {
        Response<Product> response =
                productService.getProduct(TestDataSet.PRODUCT_SUGAR_ID)
                        .execute();
        Product product = response.body();

        Products dbProduct = productsMapper.selectByPrimaryKey(TestDataSet.PRODUCT_SUGAR_ID);
        Categories dbCategory = categoriesMapper.selectByPrimaryKey(dbProduct.getCategory_id().intValue());

        assertThat(response.isSuccessful()).isTrue();
        assertThat(product.getId()).isEqualTo(dbProduct.getId());
        assertThat(product.getTitle()).isEqualTo(dbProduct.getTitle());
        assertThat(product.getCategoryTitle()).isEqualTo(dbCategory.getTitle());
    }

    @SneakyThrows
    @Test
    void getJustCreatedProductTest() throws IOException {
        // подготовка теста - создание нового продукта
        Response<Product> createResponse =
                productService.createProduct(product)
                        .execute();
        productId = createResponse.body().getId();

        // тест
        Response<Product> getResponse = productService.getProduct(productId)
                .execute();
        Product gotProduct = getResponse.body();

        Products dbProduct = productsMapper.selectByPrimaryKey(productId);
        Categories dbCategory = categoriesMapper.selectByPrimaryKey(dbProduct.getCategory_id().intValue());

        assertThat(getResponse.isSuccessful()).isTrue();
        assertThat(gotProduct.getId()).isEqualTo(dbProduct.getId());
        assertThat(gotProduct.getTitle()).isEqualTo(dbProduct.getTitle());
        assertThat(gotProduct.getCategoryTitle()).isEqualTo(dbCategory.getTitle());
        assertThat(gotProduct.getPrice()).isEqualTo(dbProduct.getPrice());
    }

    @SneakyThrows
    @Test
    void createNewProductTest() throws IOException {
        Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
    }

    @SneakyThrows
    @Test
    void createNewProductNegativeTest() {
        Response<Product> response =
                productService.createProduct(product.withId(555l))
                        .execute();
//        productId = Objects.requireNonNull(response.body()).getId();
        assertThat(response.code()).isEqualTo(400);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getMessage()).isEqualTo("Id must be null for new entity");
        }
    }

    @SneakyThrows
    @Test
    void getProductZeroIdNegativeTest() throws IOException {
        Response<Product> response = productService.getProduct(0)
                .execute();

        assertThat(response.code()).isEqualTo(404);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getMessage()).isEqualTo("Unable to find product with id: 0");
        }
    }

    @SneakyThrows
    @Test
    void getProductMinusIdNegativeTest() throws IOException {
        Response<Product> response = productService.getProduct(-2)
                .execute();

        assertThat(response.code()).isEqualTo(404);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getMessage()).isEqualTo("Unable to find product with id: -2");
        }
    }
    @SneakyThrows
    @Test
    void getProductMaxIdNegativeTest() throws IOException {
        Response<Product> response = productService.getProduct(Long.MAX_VALUE)
                .execute();

        assertThat(response.code()).isEqualTo(404);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getMessage()).isEqualTo("Unable to find product with id: 9223372036854775807");
        }
    }

    @SneakyThrows
    @Test
    void putProductZeroNegativeTest() throws IOException {
        Product zeroProduct = new Product()
                .withId(0l)
                .withCategoryTitle(CategoryType.FOOD.title)
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());

        Response<Product> response = productService.putProduct(zeroProduct)
                .execute();

        assertThat(response.code()).isEqualTo(400);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getMessage()).isEqualTo("Product with id: 0 doesn't exist");
        }
    }

    @SneakyThrows
    @Test
    void putProductMinIdNegativeTest() throws IOException {
        Product zeroProduct = new Product()
                .withId(Long.MIN_VALUE)
                .withCategoryTitle(CategoryType.FOOD.title)
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());

        Response<Product> response = productService.putProduct(zeroProduct)
                .execute();

        assertThat(response.code()).isEqualTo(400);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getMessage()).isEqualTo("Product with id: -9223372036854775808 doesn't exist");
        }
    }

    @SneakyThrows
    @Test
    void putProductMaxNegativeTest() throws IOException {
        Product zeroProduct = new Product()
                .withId(Long.MAX_VALUE)
                .withCategoryTitle(CategoryType.FOOD.title)
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());

        Response<Product> response = productService.putProduct(zeroProduct)
                .execute();

        assertThat(response.code()).isEqualTo(400);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getMessage()).isEqualTo("Product with id: 9223372036854775807 doesn't exist");
        }
    }

    @SneakyThrows
    @Test
    void putProductChangePriceTest() throws IOException {
        Product banana = new Product()
                .withId(TestDataSet.PRODUCT_BANANAMAMA_ID)
                .withCategoryTitle(TestDataSet.PRODUCT_BANANAMAMA_TYPE.title)
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(TestDataSet.PRODUCT_BANANAMAMA_TITLE);

        Response<Product> response = productService.putProduct(banana)
                .execute();

        Products dbProduct = productsMapper.selectByPrimaryKey(TestDataSet.PRODUCT_BANANAMAMA_ID);
        Categories dbCategory = categoriesMapper.selectByPrimaryKey(dbProduct.getCategory_id().intValue());


        Product changedProduct = response.body();
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.isSuccessful()).isTrue();
        assertThat(changedProduct.getId()).isEqualTo(dbProduct.getId());
        assertThat(changedProduct.getTitle()).isEqualTo(dbProduct.getTitle());
        assertThat(changedProduct.getCategoryTitle()).isEqualTo(dbCategory.getTitle());
        assertThat(changedProduct.getPrice()).isEqualTo(dbProduct.getPrice());

    }
///Цена = 0
    @SneakyThrows
    @Test
    void putProductZeroPriceNegativeTest() throws IOException {
        Product banana = new Product()
                .withId(TestDataSet.PRODUCT_BANANAMAMA_ID)
                .withCategoryTitle(TestDataSet.PRODUCT_BANANAMAMA_TYPE.title)
                .withPrice(0)
                .withTitle(TestDataSet.PRODUCT_BANANAMAMA_TITLE);

        Response<Product> response = productService.putProduct(banana)
                .execute();

        Product changedProduct = response.body();
        assertThat(response.code()).isEqualTo(400);
        assertThat(response.isSuccessful()).isTrue();
        assertThat(changedProduct.getPrice()).isEqualTo(banana.getPrice());

    }

    /// Цена = Null
    @SneakyThrows
    @Test
    void putProductNullPriceNegativeTest() throws IOException {
        Product banana = new Product()
                .withId(TestDataSet.PRODUCT_BANANAMAMA_ID)
                .withCategoryTitle(TestDataSet.PRODUCT_BANANAMAMA_TYPE.title)
                .withPrice(null)
                .withTitle(TestDataSet.PRODUCT_BANANAMAMA_TITLE);

        Response<Product> response = productService.putProduct(banana)
                .execute();

        Product changedProduct = response.body();
        assertThat(response.code()).isEqualTo(400);
        assertThat(response.isSuccessful()).isTrue();
        assertThat(changedProduct.getPrice()).isEqualTo(banana.getPrice());

    }

    @SneakyThrows
    @Test
    void putProductMAXPriceNegativeTest() throws IOException {
        Product banana = new Product()
                .withId(TestDataSet.PRODUCT_BANANAMAMA_ID)
                .withCategoryTitle(TestDataSet.PRODUCT_BANANAMAMA_TYPE.title)
                .withPrice(Integer.MAX_VALUE)
                .withTitle(TestDataSet.PRODUCT_BANANAMAMA_TITLE);

        Response<Product> response = productService.putProduct(banana)
                .execute();

        Product changedProduct = response.body();
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.isSuccessful()).isTrue();
        assertThat(changedProduct.getPrice()).isEqualTo(banana.getPrice());

    }
    @SneakyThrows
    @Test
    void putProductMINPriceNegativeTest() throws IOException {
        Product banana = new Product()
                .withId(TestDataSet.PRODUCT_BANANAMAMA_ID)
                .withCategoryTitle(TestDataSet.PRODUCT_BANANAMAMA_TYPE.title)
                .withPrice(Integer.MIN_VALUE)
                .withTitle(TestDataSet.PRODUCT_BANANAMAMA_TITLE);

        Response<Product> response = productService.putProduct(banana)
                .execute();

        Product changedProduct = response.body();
        assertThat(response.code()).isEqualTo(400);
        assertThat(response.isSuccessful()).isTrue();
        assertThat(changedProduct.getPrice()).isEqualTo(banana.getPrice());

    }

    @SneakyThrows
    @Test
    void putProductTextPriceNegativeTest() throws IOException {
        Product banana = new Product()
                .withId(TestDataSet.PRODUCT_BANANAMAMA_ID)
                .withCategoryTitle(TestDataSet.PRODUCT_BANANAMAMA_TYPE.title)
                .withPrice("Один")
                .withTitle(TestDataSet.PRODUCT_BANANAMAMA_TITLE);

        Response<Product> response = productService.putProduct(banana)
                .execute();

        Product changedProduct = response.body();
        assertThat(response.code()).isEqualTo(400);
        assertThat(response.isSuccessful()).isFalse();
    }

    @SneakyThrows
    @Test
    void putProductFractionalPriceTest() throws IOException {
        Product banana = new Product()
                .withId(TestDataSet.PRODUCT_BANANAMAMA_ID)
                .withCategoryTitle(TestDataSet.PRODUCT_BANANAMAMA_TYPE.title)
                .withPrice(77.7)
                .withTitle(TestDataSet.PRODUCT_BANANAMAMA_TITLE);

        Response<Product> response = productService.putProduct(banana)
                .execute();

        Product changedProduct = response.body();
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.isSuccessful()).isTrue();
        assertThat(changedProduct.getPrice()).isEqualTo(banana.getPrice());
    }

    @SneakyThrows
    @Test
    void putProductHugeTitleNegativeTest() throws IOException {
        Product banana = new Product()
                .withId(TestDataSet.PRODUCT_BANANAMAMA_ID)
                .withCategoryTitle(TestDataSet.PRODUCT_BANANAMAMA_TYPE.title)
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(TestDataSet.PRODUCT_BANANAMAMA_TITLE_Big);

        Response<Product> response = productService.putProduct(banana)
                .execute();

        Product changedProduct = response.body();
        assertThat(response.code()).isEqualTo(400);
        assertThat(response.isSuccessful()).isFalse();
    }
    ///Удаление продукта
    @SneakyThrows
    @Test
    void deleteProductPositiveTest() throws IOException {
        //product.setId(0);
        Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();

        /// Удаление созданного продукта

        Response<ResponseBody> deleteResponse =
        productService.deleteProduct(productId)
                .execute();
        assertThat(deleteResponse.code()).isEqualTo(200);
        assertThat(deleteResponse.isSuccessful()).isTrue();

        ///Проверяем, что продукт удален
        Products dbProduct = productsMapper.selectByPrimaryKey(productId);
        assertThat(dbProduct).isEqualTo(null);

//        Response<Product> getResponse = productService.getProduct(productId)
//                .execute();
//
//        assertThat(getResponse.code()).isEqualTo(404);
//        if (getResponse != null && !getResponse.isSuccessful() && getResponse.errorBody() != null) {
//            ResponseBody body = getResponse.errorBody();
//            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
//            ErrorBody errorBody = converter.convert(body);
//            assertThat(errorBody.getMessage()).isEqualTo("Unable to find product with id: " + productId);
//        }
        productId = null;
    }

    @AfterEach
    void tearDown() {
        if (productId != null) {
            try {
                retrofit2.Response<ResponseBody> response =
                        productService.deleteProduct(productId)
                                .execute();
                assertThat(response.isSuccessful()).isTrue();
            } catch (IOException e) {

            }
        }

    }
}
