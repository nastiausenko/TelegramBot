package org.example.settings;

import java.net.URL;


public class BankURL {
    URL url;
    private boolean isMonobank;
    private boolean isPrivatbank;
    public void setBankURL(URL url){
        this.url = url;
        this.isMonobank = url.toString().contains("monobank");
        this.isPrivatbank = url.toString().contains("privatbank");
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
}