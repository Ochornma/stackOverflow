package org.promise.currencyconverter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import org.promise.currencyconverter.databinding.ConverterFragmentBinding;

public class ConverterFragment extends Fragment {

    private ConverterFragmentBinding binding;
   // private ConverterViewModel mViewModel;

    public static ConverterFragment newInstance() {
        return new ConverterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.converter_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StackOverflow currency = ConverterFragmentArgs.fromBundle(getArguments()).getCurrency();
        binding.currency.setText(currency.getName());
        binding.rate.setText(String.valueOf(currency.getUserid()));
        //mViewModel = ViewModelProviders.of(this).get(ConverterViewModel.class);

    }

}
