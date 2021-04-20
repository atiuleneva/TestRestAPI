import okhttp3.ResponseBody;
import org.atiuleneva.dto.ErrorBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

public class CategoryTests {
    static CategoryService categoryService;

    @BeforeAll
    static void beforeAll() throws MalformedURLException {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @Test
    void getFoodCategoryPositiveTest() throws IOException {
        Response<Category> response = categoryService
                .getCategory(FOOD.id)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body().getId()).as("Id is not equal to 1!").isEqualTo(FOOD.id);
        assertThat(response.body().getTitle()).isEqualTo(FOOD.title);
    }

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
    }

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
    }

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
    }

}
