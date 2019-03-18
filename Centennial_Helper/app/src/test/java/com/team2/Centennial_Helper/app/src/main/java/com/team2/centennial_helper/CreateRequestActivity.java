package com.team2.centennial_helper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CreateRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        Spinner spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>
                (CreateRequestActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.category));
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategory);
    }
}