package com.team2.centennial_helper.common;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.team2.centennial_helper.R;
import com.team2.centennial_helper.employee.EmployeeHomeActivity;
import com.team2.centennial_helper.student.StudentHomeActivity;
import com.team2.centennial_helper.util.Util;

import androidx.annotation.NonNull;

public class LoginActivity extends Activity {
    private Button button;
    private EditText mEmail, mPw;
    private String email, password, error = "Field Required";
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

        if (email.equals("")) {
            mEmail.setError(error);
        } else if (password.equals("")) {
            mPw.setError(error);
        } else {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                Util.database.getReference("users/usertype/" + FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.getValue(Integer.class) == 0) {
                                            Util.setSharedPref(LoginActivity.this,0);
                                            startActivity(new Intent(LoginActivity.this, EmployeeHomeActivity.class));
                                        } else {
                                            Util.setSharedPref(LoginActivity.this,1);
                                            startActivity(new Intent(LoginActivity.this, StudentHomeActivity.class));
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            } else {

                                Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getLocalizedMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }


}
