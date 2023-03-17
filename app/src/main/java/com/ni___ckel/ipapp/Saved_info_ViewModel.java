package com.ni___ckel.ipapp;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.logging.ConsoleHandler;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Saved_info_ViewModel extends AndroidViewModel {

    private ipDataBase ip_data_base;
    private IPaddress ipaddress;
    private MutableLiveData<IPaddress> ipaddress_mut = new MutableLiveData<>();

    public Saved_info_ViewModel(@NonNull Application application) {
        super(application);
        ip_data_base = ipDataBase.getInstance(application);

    }

    public LiveData<IPaddress> getIpaddress_mut() {
        return ipaddress_mut;
    }

    public void loadFromBase(int id){
        loadFromBaseRX(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<IPaddress>() {
            @Override
            public void accept(IPaddress iPaddress) throws Throwable {
                ipaddress_mut.setValue(iPaddress);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                Log.d("Saved_INFO", "Error" + throwable.getMessage());
            }
        });
    }

    private Single<IPaddress> loadFromBaseRX(int id){
        return Single.fromCallable(new Callable<IPaddress>() {
            @Override
            public IPaddress call() throws Exception {
                ipaddress = ip_data_base.notesDao().getOnlyIPaddress(id);
                return ipaddress;
            }
        });
    }
}
