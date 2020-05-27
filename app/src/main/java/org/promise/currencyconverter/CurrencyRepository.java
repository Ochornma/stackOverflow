package org.promise.currencyconverter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyRepository {
    private static final String TAG = "repositorr";
    private RequestQueue mQueue;
    private Context context;
    //database
    private CurrencyDao currencyDao;
    private LiveData<PagedList<Currency>> personsLiveData;

    //network
    private LiveData<PagedList<StackOverflow>> itemPagedList;
    private LiveData<PageKeyedDataSource<Integer, StackOverflow>> liveDataSource;


    public CurrencyRepository(Context context) {
        CurrencyDatabase currencyDatabase = CurrencyDatabase.getInstance(context);
        currencyDao = currencyDatabase.currencyDao();
        mQueue = VolleyRequest.getVolley(context);
        //database
        DataSource.Factory<Integer, Currency> factory = currencyDao.getAllPaged();
        LivePagedListBuilder<Integer, Currency> pagedListBuilder = new LivePagedListBuilder<Integer, Currency>(factory, 20);
        personsLiveData = pagedListBuilder.build();
        this.context = context;
        //network
       CurrencyDataSourceFactory itemDataSourceFactory = new CurrencyDataSourceFactory(context);
        liveDataSource = itemDataSourceFactory.getStackLiveDataSource();
        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(CurrencyDataSource.PAGE_SIZE)
                        .build();
        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, config)).build();
    }


    public LiveData<PagedList<Currency>> getPagedCurrency(){
        return personsLiveData;
    }

    private void insert(Currency currency) {

        new InsertNoteAsyncTask(currencyDao).execute(currency);
    }

    public LiveData<PagedList<StackOverflow>> getItemPagedList(){
        return itemPagedList;
    }

    private void update(Currency currency) {

        new UpdateNoteAsyncTask(currencyDao).execute(currency);
    }

    private int getCount(){
       // return currencyDao.count();
        try {
            return new CountAsyncTask(currencyDao).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void getCurrency(){
        String[] symbols = context.getResources().getStringArray(R.array.currencies);
        Log.i(TAG, "getCurrency: " + symbols.length);
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, "http://data.fixer.io/api/latest?access_key=5d8a110578bbaab2f995c12321f5aa0a&format=1", null, response -> {

            try {
                JSONObject json = response.getJSONObject("rates");
                if (getCount() == 0){
                    for (String symbol : symbols) {
                        Log.i(TAG + "a", "getCurrency: " + symbols.length + symbol);
                        try {
                            int rate = json.getInt(symbol);
                            insert(new Currency(rate, symbol));
                        } catch (Exception e) {
                            Toast.makeText(context, "error 1, failed 1", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                } else {
                    for (int i = 0; i < symbols.length; i++) {
                        Log.i(TAG + "a", "getCurrency: " + symbols.length + symbols[i]);
                        try {
                            int rate = json.getInt(symbols[i]);
                                Currency currency = new Currency(rate, symbols[i]);
                                currency.setId(i);
                                update(currency);
                        }catch (Exception e){
                            Toast.makeText(context, "error 1, failed 1", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }

            } catch (JSONException e) {
                Toast.makeText(context, "error 2, failed 2", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(context, "error, failed", Toast.LENGTH_SHORT).show();
            if (getCount() == 0){
                for (int i = 0; i < symbols.length; i++) {
                    Log.i(TAG + "b", "getCurrency: " + symbols.length + symbols[i]);
                    int rate = 10 + i;
                        insert(new Currency(rate, symbols[i]));
                    }
                } else {
                for (int i = 0; i < symbols.length; i++) {
                    Log.i(TAG + "b", "getCurrency: " + symbols.length + symbols[i]);
                    int rate = 10 + i;
                        Currency currency = new Currency(rate, symbols[i]);
                        currency.setId(i);
                        update(currency);
                }
            }


        });
        mQueue.add(jsonObject);
    }


    private static class InsertNoteAsyncTask extends AsyncTask<Currency, Void, Void> {

        private CurrencyDao currencyDao;
        private InsertNoteAsyncTask(CurrencyDao currencyDao) {
            this.currencyDao = currencyDao;
        }

        @Override
        protected Void doInBackground(Currency... currencies) {
            currencyDao.insert(currencies[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Currency, Void, Void> {

        private CurrencyDao currencyDao;
        private UpdateNoteAsyncTask(CurrencyDao currencyDao) {
            this.currencyDao = currencyDao;
        }

        @Override
        protected Void doInBackground(Currency... currencies) {
            currencyDao.update(currencies[0]);
            return null;
        }
    }

    private static class CountAsyncTask extends AsyncTask<Void, Void, Integer>{
        private CurrencyDao currencyDao;

        private CountAsyncTask(CurrencyDao currencyDao){
            this.currencyDao = currencyDao;
        }
        @Override
        protected Integer doInBackground(Void... voids) {
            return currencyDao.count();
        }

    }

}
