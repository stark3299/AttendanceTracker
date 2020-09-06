package com.example.android.attendance;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpStudents extends AppCompatActivity implements View.OnClickListener {
    TextView login_text, email_sign , pass_sign , confirm_sign;
    Button sign_up;
    CheckBox terms_checkbox;
    FirebaseAuth sign_auth;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_students);
        login_text = findViewById(R.id.login_text);
        email_sign = findViewById(R.id.email_id);
        pass_sign = findViewById(R.id.pass);
        confirm_sign = findViewById(R.id.confirm_pass);
        sign_up = findViewById(R.id.register_button);
        terms_checkbox = findViewById(R.id.terms);
        progress = findViewById(R.id.progress_sign);
        sign_auth = FirebaseAuth.getInstance();
        sign_up.setOnClickListener(this);
        login_text.setOnClickListener(this);
    }
    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_button:

                String email = email_sign.getText().toString().trim();
                String pass = pass_sign.getText().toString().trim();
                String pass1 = confirm_sign.getText().toString().trim();

                if (pass1.equals(pass)) {
                    if (terms_checkbox.isChecked()) {
                        progress.setVisibility(View.VISIBLE);
                        sign_auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, task -> {
                            progress.setVisibility(View.GONE);

                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUpStudents.this, "Error !!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(SignUpStudents.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Intent i = new Intent(SignUpStudents.this , Success.class);
                                    Toast.makeText(SignUpStudents.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                                    startActivity(i,options.toBundle());
                                    finish();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(this, "Please agree the terms and condition", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SignUpStudents.this, "Password Must Be Same", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_text:
                Intent i1 = new Intent(SignUpStudents.this , LoginStudents.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                startActivity(i1,options.toBundle());
                finish();
                break;
        }
    }
}
