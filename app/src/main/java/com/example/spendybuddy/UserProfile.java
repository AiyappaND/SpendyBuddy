package com.example.spendybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spendybuddy.data.model.LoggedInUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserProfile extends AppCompatActivity {
    private String username;
    private DatabaseReference mDatabase;
    private Button changePasswordButton;
    private TextView emailText;
    private TextView usernameText;
    private TextView fullnameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        changePasswordButton = findViewById(R.id.editPasswordButton);
        emailText = findViewById(R.id.emailValue);
        usernameText = findViewById(R.id.usernameValue);
        fullnameText = findViewById(R.id.fullnameValue);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("user_details").child(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                HashMap<String, String> result = (HashMap<String, String>) task.getResult().getValue();
                usernameText.setText(username);
                assert result != null;
                fullnameText.setText(result.get("fullName"));
                emailText.setText(result.get("emailId"));
            }
            else {
                Toast.makeText(getApplicationContext(), "Something went wrong, try again later" ,
                        Toast.LENGTH_LONG).show();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                navigateToChangePassword();
            }
        });

    }

    public void navigateToChangePassword() {
        mDatabase.child("users_auth").child(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                HashMap<String, String> result = (HashMap<String, String>) task.getResult().getValue();
                String password = result.get("password");
                showPasswordChangePopup(password);
            }
            else {
                Toast.makeText(getApplicationContext(), "Something went wrong, try again later" ,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showPasswordChangePopup(String password) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserProfile.this);
        alertDialog.setTitle("Change Password");
        final EditText oldPass = new EditText(UserProfile.this);
        final EditText newPass = new EditText(UserProfile.this);
        final EditText confirmPass = new EditText(UserProfile.this);


        oldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        oldPass.setHint("Old Password");
        newPass.setHint("New Password");
        confirmPass.setHint("Confirm Password");
        LinearLayout ll=new LinearLayout(UserProfile.this);
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(oldPass);

        ll.addView(newPass);
        ll.addView(confirmPass);
        alertDialog.setView(ll);
        alertDialog.setPositiveButton("Yes", null);
        alertDialog.setNegativeButton("No", null);

        AlertDialog alert11 = alertDialog.create();

        alert11.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button positiveButton = alert11.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!(oldPass.getText().toString().equals(password))) {
                            showToast("Old password incorrect");
                        }
                        else if (!(newPass.getText().toString()
                                .equals(confirmPass.getText().toString()))) {
                            showToast("Passwords do not match");
                        }
                        else if (newPass.getText().toString().length() < 5) {
                            showToast("New password length should be > 5");
                        }
                        else {
                            LoggedInUser user = new LoggedInUser(username, newPass.getText().toString());
                            mDatabase.child("users_auth").child(user.getUserId()).setValue(user);
                            showToast("Password successfully changed");
                            dialog.dismiss();
                        }
                    }
                });

                Button negativeButton = alert11.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        alert11.show();
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}