package com.team2.centennial_helper.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.team2.centennial_helper.MainActivity;
import com.team2.centennial_helper.R;
import com.team2.centennial_helper.employee.EmployeeHomeActivity;
import com.team2.centennial_helper.student.StudentHomeActivity;
import com.team2.centennial_helper.util.Util;

public class MiddlewareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middleware);

        if(Util.getSharedPreferences(this).getInt("user_type",-1) == -1){
            startActivity(new Intent(this, MainActivity.class));
        }
        else if(Util.getSharedPreferences(this).getInt("user_type",-1) == 0){
            startActivity(new Intent(this, EmployeeHomeActivity.class));
        }
        else if(Util.getSharedPreferences(this).getInt("user_type",-1) == 1){
            startActivity(new Intent(this, StudentHomeActivity.class));
        }

        finish();
    }
}
