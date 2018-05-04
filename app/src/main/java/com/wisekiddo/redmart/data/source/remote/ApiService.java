package com.wisekiddo.redmart.data.source.remote;



import com.wisekiddo.redmart.data.model.Catalog;
import com.wisekiddo.redmart.data.model.Item;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *  * Created by ronald on 16/3/18.
 */

public interface ApiService {

    @GET("v1.6.0/catalog/search")
    Call<Catalog> getSource(
            @Query("page") Integer page,
            @Query("pageSize") Integer pageSize);

    @GET("v1.6.0/catalog/products/{id}")
    Call<Item> getProduct(
            @Path("id") int id);


}
