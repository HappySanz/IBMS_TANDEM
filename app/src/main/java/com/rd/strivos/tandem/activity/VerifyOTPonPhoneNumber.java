package com.rd.strivos.tandem.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.rd.strivos.tandem.R;

public class VerifyOTPonPhoneNumber extends AppCompatActivity {

    private TextView txtMobileNumber;
    String MobileUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number_using_otp);

        Bundle b = getIntent().getExtras();
        MobileUser = b.getString("MobileNumber");

        txtMobileNumber = findViewById(R.id.txtOTPNo);

        txtMobileNumber.setText(MobileUser);

    }
}
