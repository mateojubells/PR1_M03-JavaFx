package com.example.pr1_m03;

import java.util.ArrayList;

public class Earnings {
    private ArrayList earnings;

    public Earnings(ArrayList earnings) {
        this.earnings = earnings;
    }

    private void addEarning(Earning e){
        earnings.add(e);
    }
}
