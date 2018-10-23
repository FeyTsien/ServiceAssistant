package com.dt.serviceassistant.utils;

import android.widget.EditText;
import android.widget.TextView;

/**
 * @author :
 * @date :   2017/1/15
 */
public class TextViewUtils {

    //去除起始和结尾的空格，例："  Hello World ".trim()就是成为"Hello World"。
    public static String getText(TextView view) {
        return view.getText().toString().trim();
    }


     public  static String getText(EditText editText){
         return editText.getText().toString().trim();
     }

}
