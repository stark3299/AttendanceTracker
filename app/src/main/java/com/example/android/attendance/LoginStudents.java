package com.example.android.attendance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginStudents extends AppCompatActivity implements View.OnClickListener{
    TextView sign_text, email_login , pass_login , forgot;
    Button login;
    FirebaseAuth login_auth;
    ProgressBar progress;
    SharedPreferences sharedPreferences;
    private static final String pref = "pref";
    private static final String emails = "email";
    private static final String passwords = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.activity_login_students);
        sign_text = findViewById(R.id.register_text);
        email_login = findViewById(R.id.email_id1);
        pass_login = findViewById(R.id.pass1);
        forgot = findViewById(R.id.forgot);
        login = findViewById(R.id.login_button);
        progress = findViewById(R.id.progress_login);
        login_auth = FirebaseAuth.getInstance();
        forgot.setOnClickListener(this);
        login.setOnClickListener(this);
        sign_text.setOnClickListener(this);
    }
    public void setAnimation(){
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.START);
        slide.setDuration(400);
        slide.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(slide);
        getWindow().setEnterTransition(slide);
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(LoginStudents.this , SignUpStudents.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.login_button:
                progress.setVisibility(View.VISIBLE);
                login_auth.signInWithEmailAndPassword(email_login.getText().toString(),pass_login.getText().toString()).addOnCompleteListener(this, task -> {
                    progress.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(LoginStudents.this, Success.class);
                        String email = email_login.getText().toString();
                        String pass = pass_login.getText().toString();
                        sharedPreferences = getSharedPreferences(pref, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(emails, email);
                        editor.putString(passwords, pass);
                        editor.apply();
                        startActivity(intent);
                        finish();
                    }

                    else {
                        Toast.makeText(LoginStudents.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.forgot:
                Intent intent1 = new Intent(LoginStudents.this,Home.class);
                startActivity(intent1);
                break;

            case R.id.register_text:
                Intent intent2 = new Intent(LoginStudents.this , SignUpStudents.class);
                startActivity(intent2);
                finish();
                break;
        }
    }
}

