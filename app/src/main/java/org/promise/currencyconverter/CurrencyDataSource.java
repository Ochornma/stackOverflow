package org.promise.currencyconverter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CurrencyDataSource extends PageKeyedDataSource<Integer, StackOverflow> {

    private RequestQueue mQueue;
    public static final int PAGE_SIZE = 50;
    private static final int FIRST_PAGE = 1;
    private static final String SITE_NAME = "stackoverflow";
    private List<StackOverflow>  stackOverflows = new ArrayList<>();

    public CurrencyDataSource(Context context){
        mQueue = VolleyRequest.getVolley(context);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, StackOverflow> callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://api.stackexchange.com/2.2/answers?page=" + FIRST_PAGE+"&pagesize=" +PAGE_SIZE+ "&order=desc&sort=activity&site=" +SITE_NAME, null, response -> {

            try {
              JSONArray jsonArray = response.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("owner");
                    String name = jsonObject1.getString("display_name");
                    long userid = jsonObject1.getLong("user_id");
                    stackOverflows.add(new StackOverflow(name, userid));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if ( stackOverflows != null){
                callback.onResult(stackOverflows, null, FIRST_PAGE + 1);
            }
        }, error -> {

        });
        mQueue.add(jsonObjectRequest);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, StackOverflow> callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://api.stackexchange.com/2.2/answers?page=" + params.key+"&pagesize=" +PAGE_SIZE+ "&order=desc&sort=activity&site=" +SITE_NAME, null, response -> {

            try {
                JSONArray jsonArray = response.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("owner");
                    String name = jsonObject1.getString("display_name");
                    long userid = jsonObject1.getLong("user_id");
                    stackOverflows.add(new StackOverflow(name, userid));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Integer key = (params.key > 1) ? params.key - 1 : null;
            if ( stackOverflows != null){
                callback.onResult(stackOverflows, key);
            }
        }, error -> {

        });
        mQueue.add(jsonObjectRequest);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, StackOverflow> callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://api.stackexchange.com/2.2/answers?page=" + params.key+"&pagesize=" +PAGE_SIZE+ "&order=desc&sort=activity&site=" +SITE_NAME, null, response -> {

            try {
                JSONArray jsonArray = response.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("owner");
                    String name = jsonObject1.getString("display_name");
                    long userid = jsonObject1.getLong("user_id");
                    stackOverflows.add(new StackOverflow(name, userid));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Integer key = null;
            try {
                key = (response.getBoolean("has_more")) ? params.key - 1 : null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if ( stackOverflows != null){
                callback.onResult(stackOverflows, key);
            }
        }, error -> {

        });
        mQueue.add(jsonObjectRequest);
    }
}
