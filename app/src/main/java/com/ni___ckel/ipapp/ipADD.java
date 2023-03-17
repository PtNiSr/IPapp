package com.ni___ckel.ipapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ipADD extends AppCompatActivity {

    private EditText editTextNumberIP1;
    private EditText editTextNumberIP2;
    private EditText editTextNumberIP3;
    private EditText editTextNumberIP4;
    private TextView textView6;
    private TextView textView8;
    private TextView textView7;
    private Button buttonGetIP;
    private Button buttonGetInfo;

    private String stringIP = "129.225.57.12";
    private String strIP1;
    private String strIP2;
    private String strIP3;
    private String strIP4;
    private static final String YOUR_IP = "https://api.ipify.org/?format=json";
    private ipADDViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_add);
        initViews();
        viewModel = new ViewModelProvider(this).get(ipADDViewModel.class);
//        viewModel.loadYourIP();
        viewModel.getYourIP().observe(this, new Observer<YourIP>() {
            @Override
            public void onChanged(YourIP yourIP) {
                stringIP = yourIP.getIp();
                String[] strIP = stringIP.split("\\.");     //разделяем полученную строку на 4 части (которые разделены точкой).
                strIP1 = strIP[0];
                strIP2 = strIP[1];
                strIP3 = strIP[2];
                strIP4 = strIP[3];
            }
        });


        buttonGetIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.loadYourIP();

                editTextNumberIP1.setText(strIP1);
                editTextNumberIP2.setText(strIP2);
                editTextNumberIP3.setText(strIP3);
                editTextNumberIP4.setText(strIP4);
            }
        });

        buttonGetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip1 = editTextNumberIP1.getText().toString().trim();
                String ip2 = editTextNumberIP2.getText().toString().trim();
                String ip3 = editTextNumberIP3.getText().toString().trim();
                String ip4 = editTextNumberIP4.getText().toString().trim();
                String ip_string = ip1 + "." + ip2 + "." + ip3 + "." + ip4;

                if (
                        ip1.equals("") ||
                                ip2.equals("") ||
                                ip3.equals("") ||
                                ip4.equals("") ||
                                !ip1.matches("[-+]?\\d+") ||        // проверка, что строка является числом (верно только для небольших чисел. В принципе можно было не делать, так как json взвращает сообщенеи об ошибке
                                !ip2.matches("[-+]?\\d+") ||
                                !ip3.matches("[-+]?\\d+") ||
                                !ip4.matches("[-+]?\\d+")
                ) {
                    Toast.makeText(ipADD.this, "you should write IP", Toast.LENGTH_SHORT).show();
                } else {

                    int ip_num_1 = Integer.parseInt(ip1);
                    int ip_num_2 = Integer.parseInt(ip2);
                    int ip_num_3 = Integer.parseInt(ip3);
                    int ip_num_4 = Integer.parseInt(ip4);

                    if     ((ip_num_1 < 0) || (ip_num_1 > 255) ||
                            (ip_num_2 < 0) || (ip_num_2 > 255) ||
                            (ip_num_3 < 0) || (ip_num_3 > 255) ||
                            (ip_num_4 < 0) || (ip_num_4 > 255)) {
                        Toast.makeText(ipADD.this, "Wrong IP", Toast.LENGTH_SHORT).show();

                    } else {
                        Intent intent = IPinfo.newIntent(ipADD.this);
                        intent.putExtra("ip_string", ip_string);
                        startActivity(intent);
                    }

                }

            }
        });

    }


    private void initViews() {

        editTextNumberIP1 = findViewById(R.id.editTextNumberIP1);
        editTextNumberIP2 = findViewById(R.id.editTextNumberIP2);
        editTextNumberIP3 = findViewById(R.id.editTextNumberIP3);
        editTextNumberIP4 = findViewById(R.id.editTextNumberIP4);
        textView6 = findViewById(R.id.textView6);
        textView8 = findViewById(R.id.textView8);
        textView7 = findViewById(R.id.textView7);
        buttonGetIP = findViewById(R.id.buttonGetIP);
        buttonGetInfo = findViewById(R.id.buttonGetInfo);

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ipADD.class);
    }


}