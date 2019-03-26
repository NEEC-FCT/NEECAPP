package porta.neec.fct.com.neecapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import porta.neec.fct.com.neecapp.request.LoginRequest;

public class LoginActivity extends AppCompatActivity {


    ProgressDialog progress;
    String IMEI;

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progress = new ProgressDialog(LoginActivity.this);
        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.READ_PHONE_STATE},
                1);



        //sem net
        if (!isInternetAvailable()) {
            Intent intent = new Intent(LoginActivity.this, semNet.class);

            LoginActivity.this.startActivity(intent);
        }


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();


        int idName = pref.getInt("intro", 0);


        File f = getFileStreamPath("data");
        if (f.length() != 0) {
            // empty or doesn't exist
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

            LoginActivity.this.startActivity(intent);
        }


        final EditText etUsername = (EditText) findViewById(R.id.email);
        final EditText etPassword = (EditText) findViewById(R.id.pass2);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.textView2);


        final Button bLogin = (Button) findViewById(R.id.tvregister);
        final TextView esqueceu = (TextView) findViewById(R.id.textView6);

        esqueceu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, Esqueceu.class);

                LoginActivity.this.startActivity(intent);

            }
        });


        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, Register.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {

                progress = ProgressDialog.show(LoginActivity.this, "Loading..",
                        "Verificando", true);


                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("login", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String mensagem = jsonResponse.getString("mensagem");
                            progress.dismiss();

                            if (success) {


                                String FILENAME = "data";


                                String cargo = jsonResponse.getString("cargo");
                                String nome = jsonResponse.getString("nome");
                                String token = jsonResponse.getString("token");

                                String MY_PREFS_NAME = "dbneec";
                                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                editor.putString("nome", nome);
                                editor.putString("token", token);
                                editor.putString("cargo", cargo);
                                editor.putString("IMEI", IMEI);
                                editor.putString("email", username);
                                Log.d("db", nome);
                                Log.d("db", cargo);
                                editor.apply();


                                FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                                try {
                                    fos.write(username.getBytes());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                LoginActivity.this.startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Falhou")
                                        .setNegativeButton(mensagem, null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                };

                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


                IMEI = telephonyManager.getDeviceId();




                LoginRequest loginRequest = new LoginRequest(username, password, IMEI, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //continue using `getImei()` or `getDeviceId()`
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Erro")
                            .setNegativeButton("É necessário para autenticar o Telemóvel no Servidord", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = getBaseContext().getPackageManager()
                                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                }
                            })
                            .create()
                            .show();
                }
                return;
            }
        }
    }

}
