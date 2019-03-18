package com.team2.centennial_helper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentHomeActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        button = (Button) findViewById(R.id.btn_createRequest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCreateRequestActivity();
            }
        });
    }

    public void openCreateRequestActivity(){
        Intent intent = new Intent(this, CreateRequestActivity.class);
        startActivity(intent);
    }
}
