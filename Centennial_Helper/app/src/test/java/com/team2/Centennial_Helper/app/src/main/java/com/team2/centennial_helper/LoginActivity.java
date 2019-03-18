package com.team2.centennial_helper;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button button;
    private EditText mEmail, mPw;
    private String email, password, error="Field Required";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        initUi();

    }

    private void initUi() {
        mEmail = findViewById(R.id.txt_email);
        mPw = findViewById(R.id.txt_pwd);
        button = (Button) findViewById(R.id.btn_login2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        email = mEmail.getText().toString();
        password = mPw.getText().toString();

        if(email.equals("")){
            mEmail.setError(error);
        }
        else if(password.equals("")){
            mPw.setError(error);
        }
        else {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, StudentHomeActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed: "+task.getException().getLocalizedMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
