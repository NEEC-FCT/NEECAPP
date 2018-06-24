package porta.neec.fct.com.neecapp.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joaoveloso on 16/02/17.
 */

public class areaRequest extends StringRequest {

    private static final String REGISTER_LOGIN_URL = "http://jortec18app.neec-fct.com/area.php";
    private Map<String, String> params;

    public areaRequest(String name, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_LOGIN_URL, listener, null);
        params = new HashMap<>();

        params.put("username", name);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
