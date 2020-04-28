package com.sstechcanada.resumeapp.fragments.jef;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.sstechcanada.resumeapp.R;
import com.sstechcanada.resumeapp.models.Order;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class BookFragment extends Fragment {

    EditText btnDatePicker, btnTimePicker, username, useremail;
    Button book;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private String name, email, date, timestamp, orderID;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.jef_book, container, false);
        btnDatePicker = rootView.findViewById(R.id.datePicker);
        username = rootView.findViewById(R.id.editTextName);
        useremail = rootView.findViewById(R.id.editTextEmail);
        book = rootView.findViewById(R.id.jefBook);
        progressBar = rootView.findViewById(R.id.JefprogressBar);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateProfile(currentUser);

        btnDatePicker.setOnClickListener(view -> {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (view1, year, monthOfYear, dayOfMonth) ->
                            btnDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year),
                    mYear, mMonth, mDay);
            datePickerDialog.show();
        });

//      btnTimePicker.setOnClickListener(this);
        book.setOnClickListener(view -> {
            date = btnDatePicker.getText().toString();
            saveOrder();
        });

        //get current timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        timestamp = sdf.format(new Date());


        return rootView;
    }

    private void updateProfile(FirebaseUser user) {

        if (user != null) {
            name = user.getDisplayName();
            username.setText(name);
            email = user.getEmail();
            useremail.setText(email);
        }
    }


    private void saveOrder() {

        if (name.isEmpty()) {
            username.setError(getString(R.string.input_error_name));
            username.requestFocus();
            return;
        }


        if (email.isEmpty()) {
            useremail.setError(getString(R.string.input_error_email));
            useremail.requestFocus();
            return;
        }

        if (date.isEmpty()){
            Toast.makeText(getActivity(), getString(R.string.input_error_date), Toast.LENGTH_SHORT).show();
            return;
        }

        //random order id
        Random random = new Random();
        int randomNumber = random.nextInt(1000 - 200) + 200;
        orderID = "JEF-"+ (String.valueOf(randomNumber)) + "-" + date;

        Order order = new Order(
                name,
                email,
                date,
                timestamp,
                orderID
        );

        FirebaseDatabase.getInstance().getReference("AdminOrders")
                .child(orderID)
                .setValue(order);

        FirebaseDatabase.getInstance().getReference("Orders")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(orderID)
                .setValue(order).addOnCompleteListener(task12 -> {
            progressBar.setVisibility(View.GONE);
            if (task12.isSuccessful()) {
                Toast.makeText(getActivity(), "Booking Successful", Toast.LENGTH_LONG).show();
            }

        });




    }

}

//
//        if (v == btnTimePicker) {
//
//            // Get Current Time
//            final Calendar c = Calendar.getInstance();
//            mHour = c.get(Calendar.HOUR_OF_DAY);
//            mMinute = c.get(Calendar.MINUTE);
//
//            // Launch Time Picker Dialog
//            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//                    new TimePickerDialog.OnTimeSetListener() {
//
//                        @Override
//                        public void onTimeSet(TimePicker view, int hourOfDay,
//                                              int minute) {
//
//                            txtTime.setText(hourOfDay + ":" + minute);
//                        }
//                    }, mHour, mMinute, false);
//            timePickerDialog.show();
//        }
//    }


