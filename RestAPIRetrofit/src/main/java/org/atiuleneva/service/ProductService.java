package org.atiuleneva.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import org.atiuleneva.dto.Category;
import org.atiuleneva.dto.Product;

public interface ProductService {
    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") long id);

    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequest);

    @PUT ("products")
    Call<Product> putProduct(@Body Product productRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") long id);
}
