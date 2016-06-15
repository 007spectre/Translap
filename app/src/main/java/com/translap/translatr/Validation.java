package com.translap.translatr;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by saurabh goyal on 6/12/2015.
 */
public class Validation {
    boolean flag;
    Context context;
    String number;



    public Validation(Context applicationContext) {
        context=applicationContext;

    }

    public boolean mobileNo(String number){
        this.number= number;


        int length = number.length();
        if(length !=10)
        {            Toast.makeText(context,"Please enter a 10 digit card number", Toast.LENGTH_SHORT).show();


            flag=false;

        }
        else {
            flag=true;
        }
        return  flag;

}
    public boolean blank(String[] blankarray) {
        for(int i=0;i<blankarray.length;i++) {

            if ((blankarray[i] == null || blankarray[i].trim().equals(""))) {
                Toast.makeText(context, "Please enter all the details", Toast.LENGTH_SHORT).show();
                flag = false;
                break;
            }
            else if(blankarray[4].compareTo(blankarray[5]) != 0){
                Toast.makeText(context, "Password Not Match" , Toast.LENGTH_SHORT).show();
                flag = false;
                break;
            }
            else {

                flag = true;

            }
        }
        return flag;

    }
    public boolean report(String[] report) {
        for(int i=0;i<report.length;i++) {

            if ((report[i] == null || report[i].trim().equals(""))) {
                Toast.makeText(context, "Please enter all the details", Toast.LENGTH_SHORT).show();
                flag = false;
                break;
            }
            else {

                flag = true;

            }


        }
        return flag;

    }
            public Boolean email(String email){
             String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern))
        {

            flag =true;

        }
        else {
            Toast.makeText(context,"invalid email address", Toast.LENGTH_SHORT).show();
            flag = false;
        }
        return flag;




    }



}
