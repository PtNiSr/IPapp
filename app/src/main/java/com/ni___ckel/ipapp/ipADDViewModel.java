package com.ni___ckel.ipapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ipADDViewModel extends AndroidViewModel {

    private static final String YOUR_IP = "https://api.ipify.org/?format=json";
    private static final String KEY_IP = "ip";
    private MutableLiveData<YourIP> yourIP = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LiveData<YourIP> getYourIP() {
        return yourIP;
    }

    public ipADDViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadYourIP() {

        Disposable disposable = loadYourIPrx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<YourIP>() {
                    @Override
                    public void accept(YourIP yourIPv) throws Throwable {
                        yourIP.setValue(yourIPv);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("ipADD", "Error" + throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    private Single<YourIP> loadYourIPrx() {
        return Single.fromCallable(new Callable<YourIP>() {
            @Override
            public YourIP call() throws Exception {


                URL url = new URL(YOUR_IP);
                HttpURLConnection URLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = URLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder data = new StringBuilder();
                String result;
                do {
                    result = bufferedReader.readLine();
                    if (result != null) {
                        data.append(result);
                    }
                } while (result != null);

                JSONObject jsonObject = new JSONObject(data.toString());
                String ip = jsonObject.getString(KEY_IP);
                return new YourIP(ip);


            }
        });
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
