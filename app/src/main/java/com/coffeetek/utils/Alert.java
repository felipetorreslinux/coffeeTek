package com.coffeetek.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeetek.R;

public class Alert {

    Context context;

    public Alert(Context context) {
        this.context = context;
    }

    public Toast openBottom(String message){
        Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(context).inflate(R.layout.text_alert, null);
        TextView textView = view.findViewById(R.id.text_alert);
        textView.setText(message);
        toast.setView(view);
        toast.show();
        return toast;
    }

    public Toast openCenter(String message){
        Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(context).inflate(R.layout.text_alert, null);
        TextView textView = view.findViewById(R.id.text_alert);
        textView.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return toast;
    }

    public Toast viewCart(String message){
        Toast toast = Toast.makeText(context, null, Toast.LENGTH_LONG);
        View view = LayoutInflater.from(context).inflate(R.layout.text_alert, null);
        TextView textView = view.findViewById(R.id.text_alert);
        textView.setText(message);
        toast.setView(view);
        toast.show();
        return toast;
    }
}
