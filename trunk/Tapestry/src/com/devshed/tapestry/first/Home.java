package com.devshed.tapestry.first;

 

import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.html.BasePage;

 

public abstract class Home extends BasePage {

 

    public Home() {
        System.out.println("calling newer Guess class");
    }

   

    public abstract String getTheWord();

   
    public IPage onWordSubmit(IRequestCycle cycle) {

        

        com.devshed.tapestry.first.Secret nextPage = (com.devshed.tapestry.first.Secret)cycle.getPage("Secret");

        nextPage.setTheWord(getTheWord());

       

        return nextPage;

      

     }
    @Override
    protected String getOutputEncoding() 
    { 
        System.out.println(" i am getting called");
        return "big5"; //if utf-8 is desired 
    
    }

}