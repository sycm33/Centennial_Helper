package com.team2.centennial_helper.unit_test;

import com.google.firebase.auth.FirebaseAuth;

public class Auth {

    private FirebaseAuth firebaseAuth;

    public Auth(FirebaseAuth auth){
        firebaseAuth = auth;
    }

    public boolean getId(){

        if(firebaseAuth.getUid().length()>0){
            return true;
        }
        else {
            return false;
        }
    }
}
