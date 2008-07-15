package com.devshed.tapestry.first;


import org.apache.tapestry.html.BasePage;

public abstract class Guess extends BasePage {

   

    public Guess() {
        System.out.println("calling Guess class");
    }

   

    public abstract String getTheWord();

   

    public String onWordSubmit() {

        return "Secret";

    }

   

}