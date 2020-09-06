package com.example.android.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Success extends AppCompatActivity {
    ImageView success_logo;
    TextView success_text;
    Animation slide_down;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
        success_logo = findViewById(R.id.success_image);
        success_text = findViewById(R.id.success_text);
        new Handler().postDelayed(() -> {
            Intent i = new Intent(Success.this, Home.class);
            startActivity(i);
            finish();
        }, 2500);
        slide_down = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.slide_down );
        slide_down.setStartOffset(1200);
        success_logo.animate()
                .translationY(-1300)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(1200);
        success_text.setVisibility(View.INVISIBLE);
        success_text.setAnimation(slide_down);
    }
}
