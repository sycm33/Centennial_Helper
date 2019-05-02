package com.team2.centennial_helper.common;

import android.app.Activity;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.team2.centennial_helper.R;
import com.team2.centennial_helper.employee.EmployeeHomeActivity;
import com.team2.centennial_helper.pojo.User;
import com.team2.centennial_helper.student.StudentHomeActivity;
import com.team2.centennial_helper.util.Util;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends Activity {

    private Button button, register;
    private String firstName, lastName, email, pw;
    private EditText mFirstName, mLastName, mEmail, mPw;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef = Util.database.getReference("users");
    private Spinner chooseUser, chooseDepartment;
    private TextView departmentLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        initUi();
    }

    private void initUi() {

        mFirstName = findViewById(R.id.txt_FirstName);
        mLastName = findViewById(R.id.txt_LastName);
        mEmail = findViewById(R.id.txt_email);
        mPw = findViewById(R.id.txt_password);
        register = findViewById(R.id.btn_register);
        chooseUser = findViewById(R.id.chooseUserType);
        chooseDepartment = findViewById(R.id.chooseDepartment);
        departmentLabel = findViewById(R.id.departmentLabel);

        List<String> userTypes = new ArrayList<String>();
        userTypes.add("Employee");
        userTypes.add("Student");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, userTypes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseUser.setAdapter(dataAdapter);

        chooseUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    chooseDepartment.setVisibility(View.VISIBLE);
                    departmentLabel.setVisibility(View.VISIBLE);
                }
                else {
                    chooseDepartment.setVisibility(View.GONE);
                    departmentLabel.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> departmentType = new ArrayList<String>();
        departmentType.add("Finance");
        departmentType.add("Time Table Change");
        departmentType.add("Add/Drop a course");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, departmentType);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseDepartment.setAdapter(dataAdapter1);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();

                /*if(!email.endsWith("@my.centennialcollege.ca")){
                    mEmail.setError("please login with centennial college email address");
                    return;
                }*/
                pw = mPw.getText().toString();
                firstName = mFirstName.getText().toString();
                lastName = mLastName.getText().toString();
                signupUser();
            }
        });
    }

    private void signupUser() {

        mAuth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            registerUserToDb();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Signup failed: "+task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Signup failed: "+e.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registerUserToDb() {

        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        if(chooseUser.getSelectedItemPosition() == 0){
            user.setUserType(0);
            user.setDepartment(chooseDepartment.getSelectedItemPosition());
            myRef.child("employee/"+FirebaseAuth.getInstance().getUid()).setValue(user);
            myRef.child("usertype/"+FirebaseAuth.getInstance().getUid()).setValue(0);
            startActivity(new Intent(RegisterActivity.this, EmployeeHomeActivity.class));
            finish();
        }
        else {
            user.setUserType(1);
            user.setDepartment(-1);
            myRef.child("student/"+FirebaseAuth.getInstance().getUid()).setValue(user);
            myRef.child("usertype/"+FirebaseAuth.getInstance().getUid()).setValue(1);
            startActivity(new Intent(RegisterActivity.this, StudentHomeActivity.class));
            finish();
        }

    }

}
