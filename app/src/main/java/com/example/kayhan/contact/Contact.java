package com.example.kayhan.contact;

import android.content.ContentValues;

/**
 * Created by Kayhan on 4/8/2015.
 */
public class Contact {
    public int _id;
    public String name;
    public String number;
    public Contact(String name,String number){
        this.name = name;
        this.number = number;
    }
}
