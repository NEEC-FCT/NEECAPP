package porta.neec.fct.com.neecapp.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by veloso on 22/09/2017.
 */

public class SenhaRepostrequest extends StringRequest {

    private static final String REGISTER_LOGIN_URL = "https://neecapp.neec-fct.com/check.php";
    private Map<String, String> params;

    public SenhaRepostrequest(String password, String senha, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_LOGIN_URL, listener, null);
        params = new HashMap<>();

        params.put("password", password);
        params.put("senha", senha);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
