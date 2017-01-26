package com.company;

import java.util.ArrayList;

/**
 * Created by erikjakubowski on 1/26/17.
 */
public class User {

    String name;



    ArrayList<Messages> messageList = new ArrayList<>();


    public User(String name) {


        this.name = name;

    }
}

