package porta.neec.fct.com.neecapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by joaoveloso on 12/02/17.
 */

public class historico extends Fragment {

    private WebView webView;

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final SharedPreferences preferences = this.getActivity().getSharedPreferences("dbneec", Context.MODE_PRIVATE);

        String cargo = preferences.getString("cargo", "erro");

        if (cargo.contains("Membro") || cargo.contains("membro")) {
            return inflater.inflate(R.layout.forbiden, container, false);
        }


        return inflater.inflate(R.layout.historico, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Histórico");


        if (!isInternetAvailable()) {
            Intent intent = new Intent(getActivity(), semNet.class);
            startActivity(intent);
        }


        final SharedPreferences preferences = this.getActivity().getSharedPreferences("dbneec", Context.MODE_PRIVATE);

        String cargo = preferences.getString("cargo", "erro");

        if (!cargo.contains("Membro") || !cargo.contains("membro")) {


            webView = (WebView) getView().findViewById(R.id.webview);

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);


            //improve webView performance
            webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);


            webView.getSettings().setAppCacheEnabled(false);
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webSettings.setDomStorageEnabled(true);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setUseWideViewPort(true);
            webSettings.setSavePassword(true);
            webSettings.setSaveFormData(true);
            webSettings.setEnableSmoothTransition(true);
            //timeline

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());

            webView.loadUrl("http://neecapp.neec-fct.com/historico/");



        }


    }

}
