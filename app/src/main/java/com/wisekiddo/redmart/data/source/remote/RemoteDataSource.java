package com.wisekiddo.redmart.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.wisekiddo.redmart.data.model.Catalog;
import com.wisekiddo.redmart.data.model.Item;
import com.wisekiddo.redmart.data.source.DataSource;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronald on 13/3/18.
 */

@Singleton
public class RemoteDataSource implements DataSource {

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    private final static Map<Integer, Item> mapItems;

    private ApiService service;

    static {
        mapItems = new LinkedHashMap<>();
        //For test, TODO: male a mockito
        /*
       final int RANGE = 100;
       mapItems = new LinkedHashMap<>(RANGE);
        for (int i=RANGE;i<RANGE+RANGE;i++) {
           addItem("Title:"+(i+1), "Ground looks good,"+ (i+1)+" no foundation work required.");
        }
        */
    }
    public RemoteDataSource(){}


    @Override
    public Flowable<List<Item>> getItems() {
        return null;
    }

    @Override
    public Flowable<Optional<Item>> getItem(@NonNull Integer taskId) {
        return null;
    }

    @Override
    public void getItems(final @NonNull LoadItemsCallback callback) {
        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onItemsLoaded(Lists.newArrayList(mapItems.values()));
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

                    Catalog catalog = response.body();
                    Integer page = catalog.getPage()+ 1;
                    Integer total = catalog.getTotal();

                    List<Item> items = catalog.getItems();
                    for( Item item:items){
                        addItem(item);
                    }
                    Log.i("Current Page",catalog.getPage()+"");
                    if(page<total){
                        loadNextPage(page);
                    }
                }
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
            }
        });
    }


    private static void addItem(Item item) {
        mapItems.put(item.getId(), item);
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
        final Item item = mapItems.get(itemId);

        Call<Item> productResponseCall = service.getProduct(itemId);
        productResponseCall.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful()) {

                    Item item = response.body();
                    addItem(item);

                    Log.i("Current Page",item.getTitle()+"");
                }
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
            public void onFailure(Call<Item> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
        mapItems.put(item.getId(), item);
    }


    @Override
    public void refreshItems() {
        // Not required because the {@link ItemRepository} handles the logic of refreshing the
        // items from all the available data sources.
    }

    @Override
    public void deleteAllItems() {
        mapItems.clear();
    }

    @Override
    public void deleteItem(@NonNull Integer itemId) {
        mapItems.remove(itemId);
    }
}
