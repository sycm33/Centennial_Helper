package com.team2.centennial_helper.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.team2.centennial_helper.R;
import com.team2.centennial_helper.pojo.TicketInfo;
import com.team2.centennial_helper.util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.cardview.widget.CardView;

public class CreateTicketActivity extends Activity {

    private TextInputEditText mStudentNumber, mProgramName, mCourseName, mDiscription;
    private TextView studentNumber, programName, courseName, discription, ticketType;

    private Spinner mOption;
    private MaterialButton mSubmit, mAccept;
    private String key = "", comments;
    private LinearLayout mainLayout;
    private LinearLayout createTicket, displayTicket;
    private StateProgressBar stateProgressBar;
    private String[] descriptionData = {"Submitted","In-Progress","Ticket Closed"};
    private EditText mComments;
    private TicketInfo ticketInfo = null;
    private CardView actionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);
        initUi();

        Intent intent = getIntent();
        ticketInfo = (TicketInfo) intent.getSerializableExtra("ticket_info");

        if(ticketInfo != null){

            createTicket.setVisibility(View.GONE);
            displayTicket.setVisibility(View.VISIBLE);

            studentNumber.setText(ticketInfo.getStudentNo());
            discription.setText(ticketInfo.getDiscription());
            programName.setText(ticketInfo.getProgramName());
            courseName.setText(ticketInfo.getCourseName());

            if(ticketInfo.getTicketType() == 0){
                ticketType.setText("Finance");
            }
            else if(ticketInfo.getTicketType() == 1){
                ticketType.setText("Time Table Change");
            }
            else if(ticketInfo.getTicketType() == 2){
                ticketType.setText("Add/Drop a course");
            }

            if (ticketInfo.getUid().equals(FirebaseAuth.getInstance().getUid())){
                mAccept.setVisibility(View.GONE);
            }

            if(ticketInfo.getTicketStatus()==0){
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                if (ticketInfo.getUid().equals(FirebaseAuth.getInstance().getUid())){
                    mComments.setHint("Comments from college employee will be provided here once the ticket is accepted");
                    mComments.setEnabled(false);
                }
            }
            else if(ticketInfo.getTicketStatus()==1){
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                mComments.setEnabled(false);
                mComments.setText(ticketInfo.getComment());
                mAccept.setText("Close Ticket");
            }
            else if(ticketInfo.getTicketStatus()==2){
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                mComments.setText(ticketInfo.getComment());
                mComments.setEnabled(false);
                mAccept.setText("Ticket Closed");
                mAccept.setEnabled(false);
            }


            key = ticketInfo.getTicketKey();


        }
        else {
            createTicket.setVisibility(View.VISIBLE);
            displayTicket.setVisibility(View.GONE);
        }
    }

    private void initUi() {

        mStudentNumber = findViewById(R.id.ctStudentNum);
        mDiscription = findViewById(R.id.ctDisc);
        mProgramName = findViewById(R.id.ctProgramName);
        mCourseName = findViewById(R.id.ctCourseName);

        studentNumber = findViewById(R.id.labelSn);
        discription = findViewById(R.id.labelDiscription);
        programName = findViewById(R.id.labelPn);
        courseName = findViewById(R.id.labelCn);
        ticketType = findViewById(R.id.labelTt);

        createTicket = findViewById(R.id.createTicketLayout);
        displayTicket = findViewById(R.id.displayTicketLayout);

        actionView = findViewById(R.id.ticketActions);
        stateProgressBar = findViewById(R.id.state_progress_bar);
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);

        mOption = findViewById(R.id.ctCategory);
        List<String> options = new ArrayList<String>();
        options.add("Finance");
        options.add("Time Table Change");
        options.add("Add/Drop a course");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, options);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOption.setAdapter(dataAdapter);

        mSubmit = findViewById(R.id.ctCreateTicket);

        mComments = findViewById(R.id.ticketComment);
        mAccept = findViewById(R.id.acceptTicket);

        submitTicket();
        acceptTicket();

    }

    private void acceptTicket() {

        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ticketInfo.getTicketStatus() == 0){
                    String comment = mComments.getText().toString();

                    if(comment.equals("")){
                        mComments.setError("Please provide your comments");
                    }
                    else {
                        ticketInfo.setComment(comment);
                        ticketInfo.setTicketStatus(1);
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        mComments.setEnabled(false);
                        mAccept.setText("Close Ticket");
                        Util.database.getReference("/tickets/"+ticketInfo.getTicketKey()).setValue(ticketInfo);
                    }
                }
                else {
                    ticketInfo.setTicketStatus(2);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                    Util.database.getReference("/tickets/"+ticketInfo.getTicketKey()).setValue(ticketInfo);

                }

            }
        });
    }


    private void submitTicket(){

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TicketInfo ticketInfo = new TicketInfo();
                ticketInfo.setStudentNo(mStudentNumber.getText().toString());
                ticketInfo.setDiscription(mDiscription.getText().toString());
                ticketInfo.setProgramName(mProgramName.getText().toString());
                ticketInfo.setCourseName(mCourseName.getText().toString());
                ticketInfo.setTicketType(mOption.getSelectedItemPosition());
                ticketInfo.setUid(FirebaseAuth.getInstance().getUid());
                ticketInfo.setTicketStatus(0);

                if(key.equals("")){
                    key = Util.database.getReference("/tickets/").push().getKey();
                }

                ticketInfo.setTicketKey(key);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                ticketInfo.setTime(formatter.format(date));


                Util.database.getReference("/tickets/"+key).setValue(ticketInfo)
                        .addOnCompleteListener(CreateTicketActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                startActivity(new Intent(CreateTicketActivity.this, DisplayTickets.class));
                                Toast.makeText(CreateTicketActivity.this, "Ticket Added", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            }
        });
    }


}
