package com.example.spendybuddy.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.util.Patterns;

import com.example.spendybuddy.data.LoginRepository;
import com.example.spendybuddy.data.Result;
import com.example.spendybuddy.data.model.LoggedInUser;
import com.example.spendybuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private DatabaseReference mDatabase;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        final LoggedInUser[] user = {null};
        mDatabase.child("users_auth").child(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                HashMap<String, String> result = (HashMap<String, String>) task.getResult().getValue();
                user[0] = new LoggedInUser(result.get("userId"), result.get("password"));
                if ((user[0].getPassword().equals(password))) {
                    loginResult.setValue(new LoginResult(new LoggedInUserView(user[0].getUserId())));
                }
                else {
                    loginResult.setValue(new LoginResult(R.string.login_failed));
                }
            }
            else {
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isUserNameValid(String username) {
        String[] invalid_characters = new String[] {".", "#", "$", "[", "]"};
        if (username == null) {
            return false;
        }
        for (String s : invalid_characters) {
            if (username.contains(s)) {
                return false;
            }
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}