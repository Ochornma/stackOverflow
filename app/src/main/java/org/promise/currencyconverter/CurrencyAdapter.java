package org.promise.currencyconverter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.promise.currencyconverter.databinding.CurrencyDisplayBinding;

public class CurrencyAdapter extends PagedListAdapter<StackOverflow, CurrencyAdapter.CurrencyHolder> {

   // private Context context;
    private OnClicked onClicked;

    public CurrencyAdapter(OnClicked onClicked) {
        super(diffCallback);
        this.onClicked = onClicked;
       // this.context = context;
    }

    @NonNull
    @Override
    public CurrencyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CurrencyDisplayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.currency_display, parent, false);
        //CurrencyDisplayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.currency_display, parent, false);
        return new CurrencyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyHolder holder, int position) {
        StackOverflow currency = getItem(position);
        if (currency != null){
            if (position == 6){
                holder.binding.currency.setText("Promise");
                holder.binding.rate.setText("Promise");
                holder.setIsInTheMiddle(true);
            } else
            holder.binding.currency.setText(currency.getName());
            holder.binding.rate.setText(String.valueOf(currency.getUserid()));
        }
        holder.itemView.setOnClickListener( v -> {
            onClicked.clicked(holder.itemView, currency);
        });
    }

    static class CurrencyHolder extends RecyclerView.ViewHolder {
        CurrencyDisplayBinding binding;
        private boolean mIsInTheMiddle = false;
        CurrencyHolder(@NonNull CurrencyDisplayBinding binding1) {
            super(binding1.getRoot());
            this.binding = binding1;

        }
        boolean getIsInTheMiddle() {
            return mIsInTheMiddle;
        }

        void setIsInTheMiddle(boolean isInTheMiddle) {
            mIsInTheMiddle = isInTheMiddle;
        }
    }

    private static DiffUtil.ItemCallback<StackOverflow> diffCallback = new DiffUtil.ItemCallback<StackOverflow>() {
        @Override
        public boolean areItemsTheSame(@NonNull StackOverflow oldItem, @NonNull StackOverflow newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull StackOverflow oldItem, @NonNull StackOverflow newItem) {
            return oldItem.equals(newItem);
        }
    };

    public interface OnClicked{
        void clicked(View view, StackOverflow currency);
    }
}
