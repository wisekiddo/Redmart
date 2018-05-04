package com.wisekiddo.redmart.data.source.remote;

import com.wisekiddo.redmart.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ronald on 16/3/18.
 */

public class ApiClient {

   private static Retrofit retrofit = null;

    public ApiClient() {
    }
    public static Retrofit getClient() {
        if (retrofit == null) {
            synchronized (Retrofit.class) {
                if (retrofit == null) {
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
                    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build();
                }
            }
        }
        return retrofit;
    }

}

    /**     Setting Interceptor Chain

     *  chain -> {
     Request request = chain.request();
     Request newReq =
     request
     .newBuilder()
     .addHeader("Authorization", format("token %s", githubToken))
     .build();
     return chain.proceed(newReq);
     })
     */
