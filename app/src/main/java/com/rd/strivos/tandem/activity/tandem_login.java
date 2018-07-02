package com.rd.strivos.tandem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rd.strivos.tandem.R;

public class tandem_login extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tandem_new_login);

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            Intent i = new Intent(this, PhoneNumberLogin.class);
            startActivity(i);
        } else if (v == btnRegister) {
            Intent i = new Intent(this, RegisterPhoneNumber.class);
            startActivity(i);
        }
    }
}
