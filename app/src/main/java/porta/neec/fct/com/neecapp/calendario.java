package porta.neec.fct.com.neecapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by joaoveloso on 20/02/17.
 */

public class calendario extends Fragment {

    private WebView webView;

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calendario, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Calendário");


        if (!isInternetAvailable()) {
            Intent intent = new Intent(getActivity(), semNet.class);
            startActivity(intent);
        }


        webView = (WebView) getView().findViewById(R.id.webview);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        //timeline

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://calendar.google.com/calendar/embed?src=campus.fct.unl.pt_iub6qrmtik1umvac1un2a8efb4%40group.calendar.google.com&ctz=Europe%2FLisbon");
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);

        webView.getSettings().setAppCacheEnabled(true);

    }


}
