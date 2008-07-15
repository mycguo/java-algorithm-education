package com.devshed.tapestry.first;

import org.apache.tapestry.html.BasePage;

public abstract class Secret extends BasePage
{

    public Secret() 
    {
        System.out.println(" calling Secrect class");
    }
    public abstract String getTheWord();

    public abstract void setTheWord(String theWord);
}