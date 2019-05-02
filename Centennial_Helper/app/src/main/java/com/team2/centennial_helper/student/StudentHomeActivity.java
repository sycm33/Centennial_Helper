package com.team2.centennial_helper.student;

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
import com.team2.centennial_helper.common.LoginActivity;
import com.team2.centennial_helper.pojo.User;
import com.team2.centennial_helper.util.Util;

public class StudentHomeActivity extends Activity {
    private Button logout, createRequest, ticketHistory;
    private TextView mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        createRequest = findViewById(R.id.btn_createRequest);
        ticketHistory = findViewById(R.id.btn_TicketHistory);
        logout = findViewById(R.id.btn_logout);

        mName = findViewById(R.id.label_name);

        Util.database.getReference("/users/student/"+FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot!=null){
                    User user = dataSnapshot.getValue(User.class);
                    mName.setText("Welcome, "+user.getFirstName()+" "+user.getLastName()+"!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Util.setSharedPref(StudentHomeActivity.this, -1);
                startActivity(new Intent(StudentHomeActivity.this,MainActivity.class));
                finish();
            }
        });

        createRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentHomeActivity.this, CreateTicketActivity.class));
            }
        });

        ticketHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentHomeActivity.this, DisplayTickets.class));
            }
        });

    }

}
