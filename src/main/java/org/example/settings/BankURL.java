package org.example.settings;

import java.net.URL;


public class BankURL {
    URL url;
    private boolean isMonobank;
    private boolean isPrivatbank;
    private boolean isNBU;
    public void setBankURL(URL url){
        this.url = url;
        this.isMonobank = url.toString().contains("monobank");
        this.isPrivatbank = url.toString().contains("privatbank");
        this.isNBU = url.toString().contains("NBU");
    }
    public URL getBankURL(){
        return url;
    }

    public boolean isMonobank() {
        return isMonobank;
    }

    public boolean isPrivatbank() {
        return isPrivatbank;
    }

    public boolean isNBU() {
        return isNBU;
    }
}