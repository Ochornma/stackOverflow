package org.promise.currencyconverter;

public interface CurrencyReturned {

    void onSuccessful(Currency currency);
    void onFailed(Currency currency);
}
