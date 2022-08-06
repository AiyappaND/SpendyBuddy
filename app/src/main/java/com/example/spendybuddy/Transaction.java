package com.example.spendybuddy;

import static java.lang.Boolean.TRUE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class Transaction extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private EditText dollarAmount;
    private boolean terminateThread = false;
    private EditText note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        note = findViewById(R.id.note);
        dollarAmount = (EditText) findViewById(R.id.amount_ET);

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


}