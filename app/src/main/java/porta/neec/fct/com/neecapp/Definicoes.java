package porta.neec.fct.com.neecapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Definicoes extends Fragment {

    private static String[] open = {"NEEC SOM", "ESCAPE POD", "Human Habitat", "SEM SOM", "ELETRIC DOOR"};
    private static String[] loaditems = {"NEEC SOM", "ESCAPE POD", "Human Habitat", "SEM SOM", "ELETRIC DOOR"};
    private Spinner spinner;
    private Spinner load;
    private Boolean playLoad = false;
    private Boolean playOpen = false;

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.defenicoes, container, false);
    }


    public boolean contains(List<String> lista, String nome) {
        Iterator<String> iterador = lista.iterator();
        while (iterador.hasNext()) {
            if (iterador.next().contains(nome))
                return true;
        }

        return false;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Definições");

        String MY_PREFS_NAME = "dbneec";
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String loaded = prefs.getString("load", "Human Habitat");
        String opened = prefs.getString("open", "ESCAPE POD");

        System.out.println("loader " + loaded);
        System.out.println("open " + opened);

        List<String> openedLista = new LinkedList<String>();


        openedLista.add(opened);
        for (int i = 0; i < open.length; i++) {
            String nome = open[i];
            if (!contains(openedLista, nome))
                openedLista.add(nome);

        }


        //loader
        List<String> loaderLista = new LinkedList<String>();


        loaderLista.add(loaded);
        for (int i = 0; i < loaditems.length; i++) {
            String nome = loaditems[i];
            if (!contains(loaderLista, nome))
                loaderLista.add(nome);

        }


        if (!isInternetAvailable()) {
            Intent intent = new Intent(getActivity(), semNet.class);
            startActivity(intent);
        }


        spinner = (Spinner) view.findViewById(R.id.open);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, loaderLista.toArray(new String[0]));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                System.out.println("open " + selectedItem);

                if (playOpen) {

                    //play sound
                    if (selectedItem.contains("NEEC SOM")) {

                        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.pad_confirm);
                        mp.start();

                    } else if (selectedItem.contains("ESCAPE POD")) {

                        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.glados_escapepod);
                        mp.start();

                    } else if (selectedItem.contains("Human Habitat")) {

                        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.glados_human_habitath);
                        mp.start();

                    } else if (selectedItem.contains("ELETRIC DOOR")) {

                        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.electric_door);
                        mp.start();

                    }

                } else
                    playOpen = true;

                String MY_PREFS_NAME = "dbneec";
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("load", selectedItem);
                editor.apply();
                editor.commit();

            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //loads

        load = (Spinner) view.findViewById(R.id.load);
        ArrayAdapter<String> adapterr = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, openedLista.toArray(new String[0]));

        adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        load.setAdapter(adapterr);
        load.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();


                if (playLoad) {

                    //play sound
                    if (selectedItem.contains("NEEC SOM")) {

                        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.pad_confirm);
                        mp.start();

                    } else if (selectedItem.contains("ESCAPE POD")) {

                        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.glados_escapepod);
                        mp.start();

                    } else if (selectedItem.contains("Human Habitat")) {

                        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.glados_human_habitath);
                        mp.start();

                    } else if (selectedItem.contains("ELETRIC DOOR")) {

                        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.electric_door);
                        mp.start();

                    }
                } else
                    playLoad = true;

                System.out.println("loading " + selectedItem);
                String MY_PREFS_NAME = "dbneec";
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("open", selectedItem);
                editor.apply();
                editor.commit();


            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}
