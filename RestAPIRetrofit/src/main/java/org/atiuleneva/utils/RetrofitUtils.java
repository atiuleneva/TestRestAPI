package org.atiuleneva.utils;


import io.qameta.allure.Attachment;
import lombok.experimental.UtilityClass;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@UtilityClass
public class RetrofitUtils {
    static HttpLoggingInterceptor logging =  new HttpLoggingInterceptor(new PrettyLogger());

    public static Retrofit getRetrofit() throws MalformedURLException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMinutes(1l))
                .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return new Retrofit.Builder()
                .baseUrl(ConfigUtils.getBaseUrl())
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Attachment(value="Request", type="text/plain")
    public static String createRequestAttachment(String request) {
        return request;
    }

    @Attachment(value="Response", type="text/plain")
    public static String createResponseAttachment(String response) {
        return response;
    }

    @Attachment(value="Json Body", type="text/plain")
    public static String createJsonAttachment(String response) {
        return response;
    }
}
