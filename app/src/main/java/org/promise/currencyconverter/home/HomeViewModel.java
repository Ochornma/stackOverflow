package org.promise.currencyconverter.home;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import org.promise.currencyconverter.Currency;
import org.promise.currencyconverter.CurrencyRepository;
import org.promise.currencyconverter.StackOverflow;

public class HomeViewModel extends ViewModel {
    private LiveData<PagedList<Currency>> getData;
    private LiveData<PagedList<StackOverflow>> getstack;
    private final CurrencyRepository repository;

    public HomeViewModel(@NonNull Context context) {
        repository = new CurrencyRepository(context);
        getData = repository.getPagedCurrency();
        getstack = repository.getItemPagedList();
    }

    public LiveData<PagedList<Currency>> getPagedData(){
        return getData;
    }

    public LiveData<PagedList<StackOverflow>> getStackOverflow(){
        return getstack;
    }
}
