package com.example.spendybuddy.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.spendybuddy.LandingPageActivity;
import com.example.spendybuddy.R;
import com.example.spendybuddy.data.model.LoggedInUser;
import com.example.spendybuddy.data.model.UserDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.spendybuddy.databinding.SignupBinding;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private SignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        binding = SignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText usernameEditText = binding.signupUserId;
        final EditText passwordEditText = binding.signupPassword;
        final Button signupButton = binding.confirmSignupButton;
        final EditText emailEditText = binding.signupEmail;
        final EditText fullNameEditText = binding.signupFullName;
        setContentView(binding.getRoot());

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String fullName = fullNameEditText.getText().toString().trim();

                if (username.isEmpty()
                    || password.isEmpty()
                    || email.isEmpty()
                    || fullName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty",
                            Toast.LENGTH_LONG).show();
                }
                else if (password.length() < 5) {
                    Toast.makeText(getApplicationContext(), "Password needs to be at least 5" +
                            " characters", Toast.LENGTH_LONG).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getApplicationContext(), "Not a valid email address" ,
                            Toast.LENGTH_LONG).show();
                }
                else if (checkInvalidUsernameChars(username)) {
                    Toast.makeText(getApplicationContext(), "Following characters not allowed in username: # . $ [ ]" ,
                            Toast.LENGTH_LONG).show();
                }
                else {
                    mDatabase.child("users_auth").child(username).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.getResult().getValue() != null){
                                Toast.makeText(getApplicationContext(), "Username exists, choose another username" ,
                                        Toast.LENGTH_LONG).show();
                            }
                            else {
                                createNewUser(username, password, fullName, email);
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Something went wrong, try again later" ,
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }

    private void createNewUser(String username, String password, String fullName, String email) {
        LoggedInUser user = new LoggedInUser(username, password);
        UserDetails userDeets = new UserDetails(username, fullName, email);
        addUserToDatabase(user, userDeets);
        Intent landingPageIntent = new Intent(getApplicationContext(),
                LandingPageActivity.class);
        landingPageIntent.putExtra("username", user.getUserId());
        startActivity(landingPageIntent);
    }

    private void addUserToDatabase(LoggedInUser user, UserDetails userDeets) {
        mDatabase.child("users_auth").child(user.getUserId()).setValue(user);
        mDatabase.child("user_details").child(user.getUserId()).setValue(userDeets);
    }

    private boolean checkInvalidUsernameChars(String username) {
        String[] invalid_characters = new String[] {".", "#", "$", "[", "]"};
        for (String s:
             invalid_characters) {
            if (username.contains(s)) {
                return true;
            }
        }
        return false;
    }
}