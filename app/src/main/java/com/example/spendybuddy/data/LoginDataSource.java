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
            LoggedInUser user = checkUserExists(username, password);
            if (user != null) {
                return new Result.Success<>(user);
            }
            return new Result.Error(new IOException("Invalid username/password"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public void addUser(String userId, String password) {
        LoggedInUser user = new LoggedInUser(userId, password);
        mDatabase.child("users_auth").child(userId).setValue(user);

    }

    private LoggedInUser checkUserExists(String username, String password) {
        final LoggedInUser[] user = {null};
        mDatabase.child("users_auth").child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    user[0] = (LoggedInUser) task.getResult().getValue();
                    if (!(user[0] != null && user[0].getPassword().equals(password))) {
                        user[0] = null;
                    }
                }
                else {
                    Log.e("firebase", "Error getting data", task.getException());
                }
            }
        });
        return user[0];
    }
}