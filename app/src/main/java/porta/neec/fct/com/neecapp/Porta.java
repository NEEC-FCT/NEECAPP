package porta.neec.fct.com.neecapp;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import porta.neec.fct.com.neecapp.request.abrirPorta;
import porta.neec.fct.com.neecapp.request.isvalid;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.WIFI_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Porta extends Fragment {


    ProgressDialog progress;

    private View view;
    private WebView webView;

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.porta, container, false);
        return view;


    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ;


        getActivity().setTitle("Porta");
        Log.d("cargo", "oi");


        if (!isInternetAvailable()) {
            Intent intent = new Intent(getActivity(), semNet.class);
            startActivity(intent);
        }


        // Response received from the server
        Response.Listener<String> responseeListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    boolean success = jsonResponse.getBoolean("success");
                    System.out.println("check" + jsonResponse.toString());

                    if (!success) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Erro")
                                .setNegativeButton("Por favor volte a fazer login", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        File f = getActivity().getFileStreamPath("data");
                                        f.delete();


                                        Intent myIntent = new Intent(getActivity(), LoginActivity.class);
                                        getActivity().startActivity(myIntent);
                                    }
                                })
                                .create()
                                .show();


                    }

                } catch (JSONException e) {
                    progress.dismiss();
                    e.printStackTrace();
                }
            }
        };


        String MY_PREFS_NAME = "dbneec";
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String email = prefs.getString("email", "erro");
        String token = prefs.getString("token", "erro");
        String IMEI = prefs.getString("IMEI", "erro");
        String cargo = prefs.getString("cargo", "erro");


        isvalid loginRequest = new isvalid(email, cargo, token, IMEI, responseeListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(loginRequest);


        final SharedPreferences preferences = this.getActivity().getSharedPreferences("dbneec", Context.MODE_PRIVATE);


        final ImageView cadeado = (ImageView) view.findViewById(R.id.cadeado);

        final CheckBox check = (CheckBox) view.findViewById(R.id.checkBox);


        cadeado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (check.isChecked()) {

                    check.setChecked(false);


                    if (!isInternetAvailable()) {
                        Intent intent = new Intent(getActivity(), semNet.class);
                        startActivity(intent);
                    }


                    String cargo = preferences.getString("cargo", "erro");

                    Log.d("cargo", cargo);

                    if (cargo.contains("Junior") || cargo.contains("junior")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Erro")
                                .setNegativeButton("Os Juniores não podem abrir a Porta.", null)
                                .create()
                                .show();

                    }


                    if (cargo.contains("Senior") || cargo.contains("senior")) {

                        progress = ProgressDialog.show(getContext(), "Loading..",
                                "Enviando Pedido", true);

                        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        String ssid = wifiInfo.getSSID();
                        String MAC = wifiInfo.getBSSID();

                        if (ssid.contains("<unknown ssid>") || ssid == null || MAC == null) {
                            MAC = "<unknown MAC>";
                            ssid = "<unknown SSID>";

                        }

                        Log.d("ssid", ssid);
                        Log.d("ssid", MAC);


                        if (ssid.contains("NEEC") && MAC.contains("30:b5:c2:c2:db:66")) {

                            // Response received from the server
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);

                                        boolean success = jsonResponse.getBoolean("success");

                                        if (success) {
                                            progress.dismiss();

                                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                            builder.setMessage("Sucesso")
                                                    .setNegativeButton("Porta vai se destrancar Sozinha esperar 5 segundos", null)
                                                    .create()
                                                    .show();


                                        } else {
                                            progress.dismiss();
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                            builder.setMessage("Erro")
                                                    .setNegativeButton("Contactar admin", null)
                                                    .create()
                                                    .show();
                                        }

                                    } catch (JSONException e) {
                                        progress.dismiss();
                                        e.printStackTrace();
                                    }
                                }
                            };


                            String MY_PREFS_NAME = "dbneec";
                            SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

                            String email = prefs.getString("email", null);
                            String token = prefs.getString("token", null);
                            String IMEI = prefs.getString("IMEI", null);


                            abrirPorta loginRequest = new abrirPorta(email, cargo, token, IMEI, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(getActivity());
                            queue.add(loginRequest);

                        } else {
                            progress.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Erro")
                                    .setNegativeButton("Os Seniores tem de estar ligados ao Router do NEEC.", null)
                                    .create()
                                    .show();

                        }


                    }


                    if (!cargo.contains("Senior") && !cargo.contains("Junior")) {
                        progress = ProgressDialog.show(getContext(), "Loading..",
                                "Enviando Pedido", true);

                        // Response received from the server
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);

                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        progress.dismiss();


                                        String MY_PREFS_NAME = "dbneec";
                                        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

                                        String loaded = prefs.getString("open", "ESCAPE POD");
                                        ;


                                        if (loaded.contains("NEEC SOM")) {

                                            MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.pad_confirm);
                                            mp.start();

                                        } else if (loaded.contains("ESCAPE POD")) {

                                            MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.glados_escapepod);
                                            mp.start();

                                        } else if (loaded.contains("Human Habitat")) {

                                            MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.glados_human_habitath);
                                            mp.start();

                                        } else if (loaded.contains("ELETRIC DOOR")) {

                                            MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.electric_door);
                                            mp.start();

                                        } else {

                                        }

                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        builder.setMessage("Sucesso")
                                                .setNegativeButton("Porta vai se destrancar Sozinha esperar 5 segundos e Puxar", null)
                                                .create()
                                                .show();


                                    } else {
                                        progress.dismiss();

                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        builder.setMessage("Erro")
                                                .setNegativeButton("Contactar admin", null)
                                                .create()
                                                .show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };


                        String MY_PREFS_NAME = "dbneec";

                        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

                        String email = prefs.getString("email", null);
                        String token = prefs.getString("token", null);
                        String IMEI = prefs.getString("IMEI", null);


                        abrirPorta loginRequest = new abrirPorta(email, cargo, token, IMEI, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(loginRequest);


                    }
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Segurança")
                            .setNegativeButton("Clica na Checkbox abaixo deste aviso", null)
                            .create()
                            .show();
                }

            }
        });


    }

}
