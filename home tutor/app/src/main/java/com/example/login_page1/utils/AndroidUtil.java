package com.example.login_page1.utils;

import android.content.Context;
import android.widget.Toast;

public class AndroidUtil {
   public static void showToast(Context context,String message){
       Toast.makeText(context,message, Toast.LENGTH_LONG).show();
    }
}
