package org.promise.currencyconverter.home;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.promise.currencyconverter.Currency;
import org.promise.currencyconverter.CurrencyAdapter;
import org.promise.currencyconverter.R;
import org.promise.currencyconverter.StackOverflow;
import org.promise.currencyconverter.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment implements CurrencyAdapter.OnClicked {

    private HomeFragmentBinding binding;
    private int jobId = 1;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeFactory homeFactory = new HomeFactory( getContext());
        HomeViewModel mViewModel = new ViewModelProvider(this, homeFactory).get(HomeViewModel.class);
        final CurrencyAdapter adapter = new CurrencyAdapter(this);
       // mViewModel.getCurrency();
        mViewModel.getPagedData().observe(getViewLifecycleOwner(), new Observer<PagedList<Currency>>() {
            @Override
            public void onChanged(PagedList<Currency> currencies) {

            }
        });
        mViewModel.getStackOverflow().observe(getViewLifecycleOwner(), new Observer<PagedList<StackOverflow>>() {
            @Override
            public void onChanged(PagedList<StackOverflow> stackOverflows) {
                adapter.submitList(stackOverflows);
            }
        });
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        ComponentName componentName = new ComponentName(getActivity(), CurrencyService.class);
        JobInfo jobInfo = new JobInfo.Builder(jobId, componentName)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
    }

    @Override
    public void onStop() {
        super.onStop();
        JobScheduler jobScheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(jobId);
    }

    @Override
    public void clicked(View view, StackOverflow currency) {
        HomeFragmentDirections.ActionHomeFragmentToConverterFragment action = HomeFragmentDirections.actionHomeFragmentToConverterFragment(currency);
        Navigation.findNavController(view).navigate(action);
    }
}
