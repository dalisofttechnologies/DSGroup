package com.example.sd100testapp;

public class DataHolder {
    private static final  DataHolder instance = new DataHolder();

    public  static DataHolder getInstance(){
        return instance;
    }

    private  String data, data2, MachineIDHolder;
    public String getData(){
        return data;
    }
    public void setData(String data){
        this.data = data;
    }
    public String getData2(){
        return data2;
    }
    public void setData2(String data2){
        this.data2 = data2;
    }
}

