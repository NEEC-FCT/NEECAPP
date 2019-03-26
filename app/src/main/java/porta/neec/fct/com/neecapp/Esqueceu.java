package porta.neec.fct.com.neecapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import porta.neec.fct.com.neecapp.request.EsqueceuRequest;

/**
 * Created by Veloso on 06/08/2017.
 */

public class Esqueceu extends AppCompatActivity {


    ProgressDialog progress;

    @Override


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.esqueceu);

        final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

        final EditText mail = (EditText) findViewById(R.id.email);
        final Button recuperar = (Button) findViewById(R.id.tvregister);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });


        recuperar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                String email = mail.getText().toString();
                Log.d("email", email);

                if (email.isEmpty()) {

                    dlgAlert.setMessage("O email não pode estar vazio");
                    dlgAlert.setTitle("NEEC");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                } else {

                    progress = ProgressDialog.show(Esqueceu.this, "Loading..",
                            "Verificando seja paciente", true);

                    // Response received from the server
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progress.dismiss();
                                JSONObject jsonResponse = new JSONObject(response);
                                dlgAlert.setMessage("Verifica o email pelo Código");
                                dlgAlert.setTitle("NEEC");
                                dlgAlert.setPositiveButton("OK", null);
                                dlgAlert.setCancelable(true);
                                dlgAlert.create().show();


                                Intent intent = new Intent(Esqueceu.this, Novasenha.class);
                                Esqueceu.this.startActivity(intent);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };


                    EsqueceuRequest loginRequest = new EsqueceuRequest(email, responseListener);
                    loginRequest.setRetryPolicy(new DefaultRetryPolicy(8000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(loginRequest);

                }
            }
        });

    }
}
