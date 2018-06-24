package porta.neec.fct.com.neecapp.request;

/**
 * Created by joaoveloso on 29/01/17.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest extends StringRequest {

    private static final String REGISTER_LOGIN_URL = "http://neecapp.neec-fct.com/LoginV2.php";
    private Map<String, String> params;

    public LoginRequest(String email, String password, String IMEI, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_LOGIN_URL, listener, null);
        params = new HashMap<>();

        params.put("email", email);
        params.put("password", password);
        params.put("IMEI", IMEI);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
