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
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class IPinfoViewModel extends AndroidViewModel {

    private ipDataBase ip_data_base;
    private MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();        //коллекция всех подписок

    private String ip;
    private String city;
    private String region;
    private String country;
    private String org;
    private String postal;
    private String time_zone;

    private static final String KEY_IP = "ip";
    private static final String KEY_CITY = "city";
    private static final String KEY_REGION = "region";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_ORG = "org";
    private static final String KEY_POSTAL = "postal";
    private static final String KEY_TIME_ZONE = "timezone";
    private MutableLiveData<IPaddress> ipaddress = new MutableLiveData<>();

    public IPinfoViewModel(@NonNull Application application) {
        super(application);
        ip_data_base = ipDataBase.getInstance(application);
    }

    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }

    public void saveIPaddress(IPaddress iPaddress) {
        Disposable disposable = ip_data_base.notesDao().add(iPaddress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        shouldCloseScreen.setValue(true);   //Изменится на true когда "сохранение" завершится
                    }
                });
        compositeDisposable.add(disposable);

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public MutableLiveData<IPaddress> getIpaddress() {
        return ipaddress;
    }

    public void loadINFOip(String ip_string) {
        Disposable disposable = loadINFOipRX(ip_string)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<IPaddress>() {
                    @Override
                    public void accept(IPaddress iPaddress) throws Throwable {
                        ipaddress.setValue(iPaddress);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("IPInfo", "Error" + throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    private Single<IPaddress> loadINFOipRX(String ip_string) {
        String IP_INFO = "https://ipinfo.io/" + ip_string + "/geo";     //запрос на данный url в середине - строка с ip адресом
        return Single.fromCallable(new Callable<IPaddress>() {
            @Override
            public IPaddress call() throws Exception {

                URL url = new URL(IP_INFO);
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
                String str = data.toString();   // строка для проверки что включено в ответ от json (так как json не на все адреса выдает почтовый индекс, организацию и др)

                JSONObject jsonObject = new JSONObject(data.toString());

                if (str.contains(KEY_IP)){              //проверка включает ли json запрашиваемую информацию. Если нет, то вывод будет включать "no (название_поля)"
                    ip = jsonObject.getString(KEY_IP);
                }else{
                    ip = "no ip";
                }

                if (str.contains(KEY_CITY)){
                    city = jsonObject.getString(KEY_CITY);
                }else{
                    city = "no city";
                }

                if (str.contains(KEY_REGION)){
                    region = jsonObject.getString(KEY_REGION);
                }else{
                    region = "no region";
                }

                if (str.contains(KEY_COUNTRY)){
                    country = jsonObject.getString(KEY_COUNTRY);
                }else{
                    country = "no country";
                }

                if (str.contains(KEY_ORG)){
                    org = jsonObject.getString(KEY_ORG);
                }else{
                    org = "no org";
                }

                if (str.contains(KEY_POSTAL)){
                    postal = jsonObject.getString(KEY_POSTAL);
                }else{
                    postal = "no postal";
                }

                if (str.contains(KEY_TIME_ZONE)){
                    time_zone = jsonObject.getString(KEY_TIME_ZONE);
                }else{
                    time_zone = "no postal";
                }

                return new IPaddress(0, ip, city, region, country, org, postal, time_zone);  //

            }
        });
    }


}
