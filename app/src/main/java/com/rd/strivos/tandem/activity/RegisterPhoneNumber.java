package com.rd.strivos.tandem.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.rd.strivos.tandem.R;
import com.rd.strivos.tandem.utils.WebServices;

import org.apache.http.client.HttpResponseException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;

public class RegisterPhoneNumber extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterPhoneNumber.class.getName();

    private TextInputLayout inputPhoneNo;
    private EditText etPhoneNo;
    private Button btnVerify;
    private String mobileModel;
    private String mobileIMEI;
    private int RandomNumber;
    private String PhoneNo;
    private String otp;

    String result;

    private static final String SOAP_ACTION = "http://223.30.140.163:82/";
    private static final String NAMESPACE = "http://223.30.140.163:82/";
    private static final String URL = "http://223.30.140.163:82/InboxDetails.asmx";
    private static final String CONNANAME = "Data Source=strivos.database.windows.net;Initial Catalog=Tandem_STARS;User ID=strivos_user@Strivos;Password=e5y+JPk4?z";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_registered_ph_no);

        inputPhoneNo = findViewById(R.id.input_layout_phone_no);
        etPhoneNo = findViewById(R.id.et_phone_no);
        btnVerify = findViewById(R.id.btn_verify);
        btnVerify.setOnClickListener(this);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mobileIMEI = telephonyManager.getDeviceId();
        mobileModel = android.os.Build.MODEL;
        RandomNumber = randInt(1000, 5000);
    }


    private class myAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            //for linear parameter
            SoapObject request = new SoapObject(NAMESPACE, "isRegisteredPhone");
//            request.addProperty("Celsius", "48"); // adding method property here serially
            request.addProperty("CheckPhoneNumber", PhoneNo);
            request.addProperty("Model", mobileModel);
            request.addProperty("IMEINo", mobileIMEI);
            request.addProperty("OTP", otp);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            /*envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            envelope.dotNet = true;*/
            envelope.bodyOut = request;
            envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            envelope.setOutputSoapObject(request);

//            HttpTransportSE httpTransport = new HttpTransportSE(URL);
//            httpTransport.debug = true;

            AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
                    URL);

            try {
//                httpTransport.call(SOAP_ACTION, envelope);
                androidHttpTransport.call(SOAP_ACTION, envelope);
            } catch (HttpResponseException e) {
                // TODO Auto-generated catch block
                Log.e("HTTPLOG", e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e("IOLOG", e.getMessage());
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                Log.e("XMLLOG", e.getMessage());
                e.printStackTrace();
            } //send request

            Object result = null;
            try {
                result = (Object) envelope.getResponse();
                Log.i("RESPONSE", String.valueOf(result)); // see output in the console
            } catch (SoapFault e) {
                // TODO Auto-generated catch block
                Log.e("SOAPLOG", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }

    private static int randInt(int min, int max) {

        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    private boolean isValidMobile() {
        String mobile = etPhoneNo.getText().toString().trim();
        if (!Pattern.matches("[a-zA-Z]+", mobile)) {
            if (mobile.length() < 10 || mobile.length() > 13) {
                inputPhoneNo.setError(getString(R.string.err_msg_mobile));
                requestFocus(etPhoneNo);
                return false;
            } else {
                inputPhoneNo.setErrorEnabled(false);
            }
        } else {
            inputPhoneNo.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnVerify) {
            PhoneNo = etPhoneNo.getText().toString().trim();
            final String MobileModel = mobileModel;
            final String MobileIMEI = mobileIMEI;
            otp = String.valueOf(RandomNumber);

            Log.i(TAG, "Phone No : " + PhoneNo);

            if (!isValidMobile()) {
                btnVerify.setText("Verify");
                return;
            }

            try {
                /*Intent i = new Intent(this, VerifyOTPonPhoneNumber.class);
                i.putExtra("MobileNumber", PhoneNo);
                startActivity(i);*/
//                result = WebServices.checkRegisteredPhone(PhoneNo, MobileModel, MobileIMEI, otp);

                myAsyncTask myRequest = new myAsyncTask();
                myRequest.execute();

            } catch (Exception e) {
                Log.e("tag", e.getMessage());
            }
        }
    }
}
