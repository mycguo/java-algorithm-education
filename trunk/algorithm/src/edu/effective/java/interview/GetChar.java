package edu.effective.java.interview;

//GetChar: fetch next char 
import java.io.*;
public class GetChar { 
   private Reader in; // internal file name for input stream
  
   // GetChar: constructor  
   public GetChar () { 
      in = new InputStreamReader(System.in);
   }

   // getNextChar: fetches next char
   public char getNextChar() {
      char ch = ' '; // = ' ' to keep compiler happy
      try {
         ch = (char)in.read();
      } catch (IOException e) {
         System.out.println("Exception reading character");
      }
      return ch;
   }
}
