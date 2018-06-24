package porta.neec.fct.com.neecapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import porta.neec.fct.com.neecapp.request.SenhaRepostrequest;

/**
 * Created by veloso on 22/09/2017.
 */

public class Novasenha extends AppCompatActivity {

    ProgressDialog progress;


    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novapass);


        final EditText codigo = (EditText) findViewById(R.id.codigo);
        final EditText pass1 = (EditText) findViewById(R.id.pass1);
        final EditText pass2 = (EditText) findViewById(R.id.pass2);


        final Button bRegister = (Button) findViewById(R.id.tvregister);


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String code = codigo.getText().toString();
                final String password = pass1.getText().toString();
                final String password2 = pass2.getText().toString();


                if (password.length() <= 5) {
                    AlertDialog.Builder burlder = new AlertDialog.Builder(Novasenha.this);

                    burlder.setMessage("A Password tem de ter no minimo 6 letras")
                            .setNegativeButton("Tenta novamente", null)
                            .create()
                            .show();


                } else {


                    if (!password.matches(password2)) {
                        AlertDialog.Builder burrlder = new AlertDialog.Builder(Novasenha.this);

                        burrlder.setMessage("Password não são iguais")
                                .setNegativeButton("Tenta novamente", null)
                                .create()
                                .show();

                    }


                    progress = ProgressDialog.show(Novasenha.this, "Loading..",
                            "Verificando", true);


                    Response.Listener<String> responListerner = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonOResponse = new JSONObject(response);
                                boolean sucess = jsonOResponse.getBoolean("success");
                                progress.dismiss();
                                if (sucess) {
                                    Toast.makeText(getApplicationContext(), "Password alterado com Sucesso", Toast.LENGTH_SHORT).show();
                                    SystemClock.sleep(730);
                                    Intent intent = new Intent(Novasenha.this, LoginActivity.class);
                                    Novasenha.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder bulder = new AlertDialog.Builder(Novasenha.this);

                                    bulder.setMessage("Falha no codigo Inserido")
                                            .setNegativeButton("Tenta novamente", null)
                                            .create()
                                            .show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };


                    SenhaRepostrequest registerrequest = new SenhaRepostrequest(password, code, responListerner);
                    RequestQueue queue = Volley.newRequestQueue(Novasenha.this);
                    queue.add(registerrequest);

                }
            }

        });


    }
}
