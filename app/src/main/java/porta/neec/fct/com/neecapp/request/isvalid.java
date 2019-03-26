package porta.neec.fct.com.neecapp.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class isvalid extends StringRequest {

    private static final String REGISTER_LOGIN_URL = "https://neecapp.neec-fct.com/isvalid.php";
    private Map<String, String> params;

    public isvalid(String email, String cargo, String token, String IMEI, Response.Listener<String> listener) {
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
