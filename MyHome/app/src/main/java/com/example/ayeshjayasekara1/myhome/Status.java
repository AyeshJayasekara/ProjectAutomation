package com.example.ayeshjayasekara1.myhome;

/**
 * Created by ayeshjayasekara1 on 5/10/17.
 */

public class Status {

    private int StatusArray[];

    public Status(int[] statusArray) {
        StatusArray = statusArray;
    }

    public Status(){
        int x=0;
        this.StatusArray = new int[24];
        for(;x<24;x++)
            this.StatusArray[x]=0;
    }

    public int[] getStatusArray() {
        return StatusArray;
    }

    private void setStatusArrayValue(int i,int val){
        StatusArray[i]=val;
    }

    public void AllController(int flag){
        setStatusArrayValue(0,flag);
    }

    public void controlLight(int i,int stat){
        if(i > 0 && i< 5)
            setStatusArrayValue(i,stat);
    }

    public void dimLight(int i,int stat){
        i+=4;
        if(i>4 && i<7)
            setStatusArrayValue(i,stat);
    }

    public void curtain(int i){
        setStatusArrayValue(7,i);
    }

    public void door(int i){
        setStatusArrayValue(8,i);
    }

    public void aircon(int i){
        setStatusArrayValue(9,i);
    }

    public void readInput(int i){
        setStatusArrayValue(10,i);
    }

    public int checkReadPin() {
        return this.StatusArray[10];
    }
}
