package com.example.android.attendance;

import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.animation.DecelerateInterpolator;
import androidx.appcompat.app.AppCompatActivity;

public class Home  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.activity_main);
    }
    public void setAnimation(){
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.START);
            slide.setDuration(400);
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
    }
}
