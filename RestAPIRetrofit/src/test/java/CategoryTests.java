import io.qameta.allure.Attachment;
import io.qameta.allure.Feature;
import okhttp3.ResponseBody;
import org.atiuleneva.db.dao.CategoriesMapper;
import org.atiuleneva.db.dao.ProductsMapper;
import org.atiuleneva.db.model.Categories;
import org.atiuleneva.dto.ErrorBody;
import org.atiuleneva.utils.DbUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import org.atiuleneva.enums.CategoryType;
import org.atiuleneva.dto.Category;
import org.atiuleneva.service.CategoryService;
import org.atiuleneva.utils.RetrofitUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.atiuleneva.enums.CategoryType.FOOD;

@Feature("Testing Categories API")
@DisplayName("Testing Categories API")
public class CategoryTests {
    static CategoryService categoryService;
    static CategoriesMapper categoriesMapper;
    public static Logger logger = LoggerFactory.getLogger(CategoryTests.class);

    @BeforeAll
    static void beforeAll() throws MalformedURLException {
        categoriesMapper = DbUtils.getCategoriesMapper();
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @DisplayName("Get Food Category (Positive Test)")
    @Test
    void getFoodCategoryPositiveTest() throws IOException {
        Response<Category> response = categoryService
                .getCategory(FOOD.id)
                .execute();

        Categories dbFood = categoriesMapper.selectByPrimaryKey((int)FOOD.id);

        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body().getId()).as("Id is not equal to 1!").isEqualTo((long)dbFood.getId());
        assertThat(response.body().getTitle()).isEqualTo(dbFood.getTitle());

        RetrofitUtils.createRequestAttachment(response.raw().request().toString());
        RetrofitUtils.createResponseAttachment(response.raw().toString());
    }

    @DisplayName("Get Category Zero Id (Negative Test)")
    @Test
    void getCategoryZeroIdNegativeTest() throws IOException {
        Response<Category> response = categoryService
                .getCategory(0)
                .execute();

        assertThat(response.code()).isEqualTo(404);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getMessage()).isEqualTo("Unable to find category with id: 0");
        }

        RetrofitUtils.createRequestAttachment(response.raw().request().toString());
        RetrofitUtils.createResponseAttachment(response.raw().toString());
    }

    @DisplayName("Get Category Max Id (Negative Test)")
    @Test
    void getCategoryMaxIdNegativeTest() throws IOException {
        Response<Category> response = categoryService
                .getCategory(Long.MAX_VALUE)
                .execute();

        assertThat(response.code()).isEqualTo(404);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getMessage()).isEqualTo("Unable to find category with id: 9223372036854775807");
        }

        RetrofitUtils.createRequestAttachment(response.raw().request().toString());
        RetrofitUtils.createResponseAttachment(response.raw().toString());
    }

    @DisplayName("Get Category Min Id (Negative Test)")
    @Test
    void getCategoryMinIdNegativeTest() throws IOException {
        Response<Category> response = categoryService
                .getCategory(Long.MIN_VALUE)
                .execute();

        assertThat(response.code()).isEqualTo(404);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getMessage()).isEqualTo("Unable to find category with id: -9223372036854775808");
        }

        RetrofitUtils.createRequestAttachment(response.raw().request().toString());
        RetrofitUtils.createResponseAttachment(response.raw().toString());
    }

}
