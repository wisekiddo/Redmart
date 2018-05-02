package com.wisekiddo.redmart.data.source.remote;



import com.wisekiddo.redmart.data.model.Catalog;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Igor Havrylyuk on 27.03.2017.
 */

public interface ApiService {

    @GET("v1.6.0/catalog/search")
    Call<Catalog> getSource(
            @Query("page") Integer page,
            @Query("pageSize") Integer pageSize);



}
