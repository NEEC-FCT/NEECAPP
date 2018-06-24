package porta.neec.fct.com.neecapp.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by Veloso on 12/07/2017.
 */

public class oradorequest extends StringRequest {

    private static final String REGISTER_LOGIN_URL = "http://jortec18app.neec-fct.com/oradores/orador.php";
    private Map<String, String> params;

    public oradorequest(Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_LOGIN_URL, listener, null);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
