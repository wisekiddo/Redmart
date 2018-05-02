package com.wisekiddo.redmart.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.collect.Lists;
import com.wisekiddo.redmart.data.model.Catalog;
import com.wisekiddo.redmart.data.model.Item;
import com.wisekiddo.redmart.data.source.DataSource;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronald on 13/3/18.
 */

@Singleton
public class RemoteDataSource implements DataSource {

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    private final static Map<Integer, Item> ITEMS_SERVICE_DATA;

    private ApiService service;

    static {
        //For test, TODO: male a mockito
        /*
       final int RANGE = 100;
       ITEMS_SERVICE_DATA = new LinkedHashMap<>(RANGE);
        for (int i=RANGE;i<RANGE+RANGE;i++) {
           addItem("Title:"+(i+1), "Ground looks good,"+ (i+1)+" no foundation work required.");
        }
        */
    }
    public RemoteDataSource(){}


    private static void addItem(String title, String description) {
        Item newItem = new Item(title, description, 1);
        ITEMS_SERVICE_DATA.put(newItem.getId(), newItem);
    }

    @Override
    public void getItems(final @NonNull LoadItemsCallback callback) {
        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onItemsLoaded(Lists.newArrayList(ITEMS_SERVICE_DATA.values()));
            }
        }, SERVICE_LATENCY_IN_MILLIS);
        service = ApiClient.getClient().create(ApiService.class);
        loadNextPage(0);

    }
    private void loadNextPage(Integer page) {

        Call<Catalog> catalogResponseCall = service.getSource(page,100);
        catalogResponseCall.enqueue(new Callback<Catalog>() {
            @Override
            public void onResponse(Call<Catalog> call, Response<Catalog> response) {

                if (response.isSuccessful()) {
                    Log.i("--->",response.body().getPage()+"");
                    List<Item> results = fetchResults(response);
                    // adapter.addAll(results);
                    Integer page = response.body().getPage().intValue() + 1;
                    Integer total = response.body().getTotal().intValue();
                    if(page<total){

                        loadNextPage(page);
                    }                }
                else {
                    // error case
                    switch (response.code()) {
                        case 404:
                            Log.e(this.getClass().getSimpleName(), "not found : 404");
                            break;
                        case 500:
                            Log.e(this.getClass().getSimpleName(), "server broken: 500");
                            break;
                        default:
                            Log.e(this.getClass().getSimpleName(), "unknown error");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Catalog> call, Throwable t) {
                t.printStackTrace();
                // TODO: 08/11/16 handle failure
            }
        });
    }
    /**
     * @param response extracts List<{@link Item>} from response
     * @return
     */
    private List<Item> fetchResults(Response<Catalog> response) {
        Catalog catalog = response.body();
        return catalog.getItems();
    }

    @Override
    public void getItem(@NonNull Integer itemId, final @NonNull GetItemCallback callback) {
        final Item item = ITEMS_SERVICE_DATA.get(itemId);

        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onItemLoaded(item);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }




    @Override
    public void saveItem(@NonNull Item item) {
        ITEMS_SERVICE_DATA.put(item.getId(), item);
    }


    @Override
    public void refreshItems() {
        // Not required because the {@link ItemRepository} handles the logic of refreshing the
        // items from all the available data sources.
    }

    @Override
    public void deleteAllItems() {
        ITEMS_SERVICE_DATA.clear();
    }

    @Override
    public void deleteItem(@NonNull Integer itemId) {
        ITEMS_SERVICE_DATA.remove(itemId);
    }
}
