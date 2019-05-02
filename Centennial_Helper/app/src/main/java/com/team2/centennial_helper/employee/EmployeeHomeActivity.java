package com.team2.centennial_helper.employee;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.team2.centennial_helper.MainActivity;
import com.team2.centennial_helper.R;
import com.team2.centennial_helper.pojo.User;
import com.team2.centennial_helper.student.DisplayTickets;
import com.team2.centennial_helper.util.Util;

public class EmployeeHomeActivity extends Activity {

    private TextView mWelcomeLabel;
    private Button logout, viewTickets;
    private User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);

        logout = findViewById(R.id.btn_logout);
        viewTickets = findViewById(R.id.viewTicket);
        mWelcomeLabel = findViewById(R.id.label_tit_employee);

        Util.database.getReference("/users/employee/"+FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                mWelcomeLabel.setText("Welcome "+user.getFirstName()+" "+user.getLastName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Util.setSharedPref(EmployeeHomeActivity.this, -1);
                startActivity(new Intent(EmployeeHomeActivity.this,MainActivity.class));
                finish();
            }
        });

        viewTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null && user.getDepartment() != -1){
                    startActivity(new Intent(EmployeeHomeActivity.this, DisplayTickets.class)
                            .putExtra("department_type",user.getDepartment()));
                }

            }
        });
    }
}
