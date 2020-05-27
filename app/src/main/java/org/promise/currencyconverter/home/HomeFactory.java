package org.promise.currencyconverter.home;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HomeFactory implements ViewModelProvider.Factory {

    private Context mcontext;


    public HomeFactory(Context context){
        mcontext = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return  (T) new HomeViewModel(mcontext);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
