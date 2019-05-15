package com.coffeetek.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimationsView {

    Context context;
    Animation animation;

    public AnimationsView(Context context) {
        this.context = context;
    }

    public Animation viewCountCart(View view){
        animation = new TranslateAnimation(0,0,1000,0);
        animation.setDuration(1500);
        animation.setFillEnabled(true);
        view.startAnimation(animation);
        return animation;
    }
}
