package com.example.spendybuddy.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.spendybuddy.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private DatabaseReference mDatabase;


    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            mDatabase = FirebaseDatabase.getInstance().getReference();
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            writeNewUser(username, "test123");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    // TODO: Temporary method to check DB operations, move out later
    public void writeNewUser(String userId, String password) {
        LoggedInUser user = new LoggedInUser(userId, password);
        mDatabase.child("users_auth").child(userId).setValue(user);

    }
}