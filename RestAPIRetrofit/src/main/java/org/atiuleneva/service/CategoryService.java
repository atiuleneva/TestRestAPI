package org.atiuleneva.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import org.atiuleneva.dto.Category;

public interface CategoryService {
    @GET("categories/{id}")
    Call<Category> getCategory(@Path("id") long id);
}