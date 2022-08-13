package com.example.spendybuddy;

import static java.lang.Boolean.TRUE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.spendybuddy.data.model.TransactionType;
import com.example.spendybuddy.utils.RTDB;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class Transaction extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private EditText dollarAmount;
    private EditText note;
    private boolean terminateThread = false;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    Button mCaptureBtn;
    ImageView mImageView;
    Button submitButton;
    Uri image_uri;
    RTDB db;
    com.example.spendybuddy.data.model.Transaction m = new com.example.spendybuddy.data.model.Transaction();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        dollarAmount = (EditText) findViewById(R.id.amount_ET);
        note = findViewById(R.id.note);

        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.capture_image_btn);


        // This are for the bucket
        Spinner categorySpinner = findViewById(R.id.categoryDropDown);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Transaction.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(myAdapter);

        // Where the money is from
        Spinner fromSpinner = findViewById(R.id.fromBucket);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(Transaction.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cashFrom));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(myAdapter2);

        FloatingActionButton home = findViewById(R.id.home_button);
        home.setOnClickListener(view -> {
            Intent intent = new Intent(Transaction.this, LandingPageActivity.class);
            startActivity(intent);

        });

        // Code for taking picture
        // Reference: https://www.youtube.com/watch?v=LpL9akTG4hI
        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else{
                        // Per mission already granted
                        openCamera();
                    }

                }
                else{
                    openCamera();
                }
            }
        });
        submitButton = findViewById(R.id.Submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double newAmount = Double.valueOf(String.valueOf(dollarAmount.getText()));
                m.setAmount(newAmount);
                String newDescription = String.valueOf(note.getText());
                m.setDescription(newDescription);
                String newType = String.valueOf(categorySpinner.getSelectedItem());
                m.setTransactionType(TransactionType.valueOf(newType));
                String newDate = String.valueOf(dateButton.getText());
                m.setDate(newDate);

                if(image_uri != null){
                    uploadToFirebase(image_uri);

                }else {
                    db.updateTransaction(m.getId(), m);
                }
                finish();
            }
        });






    }
    private void uploadToFirebase(Uri uri){
        final StorageReference filref = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
        filref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        m.setImage(String.valueOf(uri));
                        db.updateTransaction(m.getId(), m);
                    }
                });
            }
        });}

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }



    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        Log.d("inside Initdatepicker", "initDatePicker: ");
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }


    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    // To do: Come back to this one. Can't get the dollar sign to work yet
    public void changeDollarFormat(View view) {

        dollarAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                dollarAmount.setText("$" + dollarAmount.getText().toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    // For the image
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore. Images.Media.TITLE, "New Pict");
        values.put(MediaStore. Images.Media.DESCRIPTION, "From the camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    // Hand permission result
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == IMAGE_CAPTURE_CODE){
            mImageView.setImageURI(image_uri);
        }
    }


}