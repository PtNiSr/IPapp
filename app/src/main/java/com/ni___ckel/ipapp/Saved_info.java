package com.ni___ckel.ipapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Saved_info extends AppCompatActivity {

    private TextView textViewIP2;
    private TextView textViewCity2;
    private TextView textViewRegion2;
    private TextView textViewCountry2;
    private TextView textViewOrg2;
    private TextView textViewPostalCode2;
    private TextView textViewTimeZone2;
    private Button buttonBack2;
    private String id_string;

    private static final String id_ = "id_string";
    private ipDataBase ip_data_base;


    private Saved_info_ViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_info);

        initViews();

        ip_data_base = ipDataBase.getInstance(getApplication());
        id_string = getIntent().getStringExtra(id_);    //получили id элемента в виде строки
        int id = Integer.valueOf(id_string);        //получили id нажатого элемента (перевели в int)

        viewModel = new ViewModelProvider(this).get(Saved_info_ViewModel.class);
        viewModel.loadFromBase(id);
        viewModel.getIpaddress_mut().observe(this, new Observer<IPaddress>() {
            @Override
            public void onChanged(IPaddress iPaddress) {

                String ip_string = getString(R.string.ip_string2, iPaddress.getIP());
                textViewIP2.setText(ip_string);
                String str = getString(R.string.city_string2, iPaddress.getCity());
                textViewCity2.setText(str);
                String str2 = getString(R.string.region_string2, iPaddress.getRegion());
                textViewRegion2.setText(str2);
                String str3 = getString(R.string.country_string2, iPaddress.getCountry());
                textViewCountry2.setText(str3);
                String str4 = getString(R.string.org_string2, iPaddress.getOrg());
                textViewOrg2.setText(str4);
                String str5 = getString(R.string.postal_code_string2, iPaddress.getPostal());
                textViewPostalCode2.setText(str5);
                String str6 = getString(R.string.time_zonep_string2, iPaddress.getTimezone());
                textViewTimeZone2.setText(str6);

            }
        });

        buttonBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.newIntent(Saved_info.this);
                startActivity(intent);
            }
        });

    }

    private void initViews() {
        textViewIP2 = findViewById(R.id.textViewIP2);
        textViewCity2 = findViewById(R.id.textViewCity2);
        textViewRegion2 = findViewById(R.id.textViewRegion2);
        textViewCountry2 = findViewById(R.id.textViewCountry2);
        textViewOrg2 = findViewById(R.id.textViewOrg2);
        textViewPostalCode2 = findViewById(R.id.textViewPostalCode2);
        textViewTimeZone2 = findViewById(R.id.textViewTimeZone2);
        buttonBack2 = findViewById(R.id.buttonBack2);

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, Saved_info.class);
    }
}