package com.ni___ckel.ipapp;

import java.util.ArrayList;

public class IP_base_fake {
    private ArrayList<IPaddress> ipADDresses = new ArrayList<>();


   public IP_base_fake() {
        for (int i = 0; i < 20; i++) {
            IPaddress ipaddress = new IPaddress(i, "192.168.0.1", "Moscow", "Moscow", "Russia", "Home", "117647", "Moscow/Europe");
            ipADDresses.add(ipaddress);
        }

    }

    public ArrayList<IPaddress> getIpADDresses() {
        return new ArrayList<>(ipADDresses);
    }
}


