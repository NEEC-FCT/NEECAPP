package porta.neec.fct.com.neecapp.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Veloso on 15/09/2017.
 */

public class abrirPorta extends StringRequest {

    private static final String REGISTER_LOGIN_URL = "https://neecapp.neec-fct.com/portav2.php";
    private Map<String, String> params;

    public abrirPorta(String email, String cargo, String token, String IMEI, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_LOGIN_URL, listener, null);
        params = new HashMap<>();

        params.put("email", email);
        params.put("cargo", cargo);
        params.put("token", token);
        params.put("IMEI", IMEI);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
