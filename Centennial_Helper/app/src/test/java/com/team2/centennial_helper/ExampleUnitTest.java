package com.team2.centennial_helper;

import com.google.firebase.auth.FirebaseAuth;
import com.team2.centennial_helper.unit_test.Auth;
import com.team2.centennial_helper.util.Util;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void check_correctEmail() {
        assertTrue(Util.isValidEmail("pmangla@my.centennialcollege.ca"));
    }

    @Test
    public void check_invalidEmail() {
        assertTrue(Util.isValidEmail("pmangla@my"));
    }

    @Test
    public void check_correctPw() {
        assertTrue(Util.isValidPw("password123"));
    }

    @Test
    public void check_invalidPw() {
        assertTrue(Util.isValidPw("pw123"));
    }

    @Test
    public void check_loggedInUser(){

        FirebaseAuth firebaseAuth = Mockito.mock(FirebaseAuth.class);
        Mockito.when(firebaseAuth.getUid()).thenReturn("123456789");

        Auth auth = new Auth(firebaseAuth);
        assertTrue(auth.getId());
    }

    @Test
    public void check_notLoggedInUser(){

        FirebaseAuth firebaseAuth = Mockito.mock(FirebaseAuth.class);
        Mockito.when(firebaseAuth.getUid()).thenReturn("");

        Auth auth = new Auth(firebaseAuth);
        assertTrue(auth.getId());
    }
}