package com.ni___ckel.ipapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IPinfo extends AppCompatActivity {

    private TextView textViewIP;
    private TextView textViewCity;
    private TextView textViewRegion;
    private TextView textViewCountry;
    private TextView textViewOrg;
    private TextView textViewPostal;
    private TextView textViewTimeZone;
    private Button buttonSave;
    private Button buttonBack;

    private String ip_string;
    private String str0;
    private String str;
    private String str2;
    private String str3;
    private String str4;
    private String str5;
    private String str6;

    private IPinfoViewModel viewModel;

    private static final String ip_address = "ip_string";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipinfo);
        initViews();

        Intent intent = MainActivity.newIntent(IPinfo.this);

        ip_string = getIntent().getStringExtra(ip_address).trim();     //переданный ip адрес с прошлого экрана

        viewModel = new ViewModelProvider(this).get(IPinfoViewModel.class);
        viewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {

                //если сохранение во ViewModel прошло
                // успешно, то будет перевод на другой экран. Подписка была добавлена так
                // как неизвестно время которое понадобится на сохранение
                // таким образом изменения в boolean сигнализируют о "безопасности" перехода на другой экран

                if (shouldClose) {
                    startActivity(intent);
                }
            }
        });

        viewModel.loadINFOip(ip_string);        //метод вызывается с передачей ip полученного с прошлого экрана (в url присутствует этот ip)
        viewModel.getIpaddress().observe(this, new Observer<IPaddress>() {
            @Override
            public void onChanged(IPaddress iPaddress) {

                str0 = getString(R.string.ipString, iPaddress.getIP());
                textViewIP.setText(str0);
                str = getString(R.string.cityString, iPaddress.getCity());
                textViewCity.setText(str);
                str2 = getString(R.string.regionString, iPaddress.getRegion());
                textViewRegion.setText(str2);
                str3 = getString(R.string.countryString, iPaddress.getCountry());
                textViewCountry.setText(str3);
                str4 = getString(R.string.orgString, iPaddress.getOrg());
                textViewOrg.setText(str4);
                str5 = getString(R.string.postal_codeString, iPaddress.getPostal());
                textViewPostal.setText(str5);
                str6 = getString(R.string.time_zoneString, iPaddress.getTimezone());
                textViewTimeZone.setText(str6);
            }
        });



        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIPaddress();

            }
        });

    }

    private void saveIPaddress() {
        IPaddress ipaddress = new IPaddress(0, str0, str, str2, str3, str4, str5, str6);
        viewModel.saveIPaddress(ipaddress);

    }

    private void initViews() {
        textViewIP = findViewById(R.id.textViewIP);
        textViewCity = findViewById(R.id.textViewCity);
        textViewRegion = findViewById(R.id.textViewRegion);
        textViewCountry = findViewById(R.id.textViewCountry);
        textViewOrg = findViewById(R.id.textViewOrg);
        textViewPostal = findViewById(R.id.textViewPostal);
        textViewTimeZone = findViewById(R.id.textViewTimeZone);
        buttonSave = findViewById(R.id.buttonSave);
        buttonBack = findViewById(R.id.buttonBack);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, IPinfo.class);
    }


}