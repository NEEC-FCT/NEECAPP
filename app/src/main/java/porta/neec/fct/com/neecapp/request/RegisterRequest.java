package porta.neec.fct.com.neecapp.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joaoveloso on 29/01/17.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://neecapp.neec-fct.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String email, String password, String hex, String IMEI, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        params.put("IMEI", IMEI);
        params.put("hex", hex);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
