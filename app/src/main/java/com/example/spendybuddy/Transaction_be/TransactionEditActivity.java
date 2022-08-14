package com.example.spendybuddy.Transaction_be;

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
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.spendybuddy.LandingPageActivity;
import com.example.spendybuddy.R;
import com.example.spendybuddy.TransactionActivity;
import com.example.spendybuddy.data.model.Transaction;
import com.example.spendybuddy.data.model.TransactionType;
import com.example.spendybuddy.utils.RTDB;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.Calendar;

public class TransactionEditActivity extends AppCompatActivity {
    Transaction m;
    DatabaseReference db ;

    TextView amount;
    private DatePickerDialog datePickerDialog;
    Button dateButton;
    EditText note;
    Spinner fromBucket;
    Spinner categorySpinner;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    Button mCaptureBtn;
    Uri image_uri;
    ImageView mImageView;
    Uri location_uri;
    Button submitButton;
    String username;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            username = extras.getString("username");
        }
        setContentView(R.layout.activity_transaction_edit);
        m = (Transaction) getIntent().getSerializableExtra("transaction");
        System.out.println("HERE");
        System.out.println(m);
        System.out.println(m.getId()+ "," + m.getAmount()+ "," + m.getTransactionType()+ "," + m.getDate()+ "," + m.getDescription()+ "," + m.getAccount_id());
        db = FirebaseDatabase.getInstance().getReference("transactions");
        initDatePicker();
        if(m.getDate() != null);{
            String date=  m.getDate();

        }
        if(m.getDescription() != null){
            String date=  m.getDescription();
        }
        else{
            String date = "";
        }

        // Where the money is from
        Spinner fromSpinner = findViewById(R.id.fromBucket);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(TransactionEditActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cashFrom));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(myAdapter2);

        TransactionType type = m.getTransactionType();
        Double amountDouble = m.getAmount();
        submitButton = findViewById(R.id.Submit_button);
        amount = findViewById(R.id.amount_ET);
        amount.setText(String.valueOf(amountDouble));
        dateButton = findViewById(R.id.datePickerButton);
        categorySpinner = findViewById(R.id.categoryDropDown);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(TransactionEditActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(myAdapter);
        String.valueOf(categorySpinner.getSelectedItem());

        int spinnerpostion = myAdapter.getPosition(String.valueOf(type));
        categorySpinner.setSelection(spinnerpostion);
        note = findViewById(R.id.note);
        note.setText(m.getDescription());
        mCaptureBtn = findViewById(R.id.capture_image_btn);
        mImageView = findViewById(R.id.image_view);
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
       if(m.getImage() != null){
           if (!m.getImage().equals("")) {
               Glide.with(this).load(m.getImage()).into(mImageView);
           }
       }

        FloatingActionButton home = findViewById(R.id.home5_button);
        home.setOnClickListener(view -> {
            Intent intent = new Intent(TransactionEditActivity.this, LandingPageActivity.class);
            Bundle bundle = getIntent().getExtras();
            bundle.putString("username",username);
            intent.putExtras(bundle);
            startActivity(intent);

        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double newAmount = Double.valueOf(String.valueOf(amount.getText()));
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
                    updateTransaction(db, m.getId(), m);
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
                     System.out.println(1);
                     location_uri = uri;

                     m.setImage(String.valueOf(location_uri));
                     updateTransaction(db, m.getId(), m);
                 }
             });
         }
     }

        );
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

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

    public void updateTransaction(DatabaseReference dbr, String transactionId, Transaction transaction){
        DatabaseReference ref = dbr.child(transactionId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().setValue(transaction).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Updated transaction " + transactionId);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error: Failed to update transaction");
            }
        });
    }


}