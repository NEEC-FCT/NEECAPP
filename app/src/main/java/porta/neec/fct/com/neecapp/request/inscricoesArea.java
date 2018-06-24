package porta.neec.fct.com.neecapp.request;

/**
 * Created by joaoveloso on 29/01/17.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class inscricoesArea extends StringRequest {

    private static final String REGISTER_LOGIN_URL = "http://jortec18app.neec-fct.com/inscrever.php";
    private Map<String, String> params;

    public inscricoesArea(String email, int inscrito, int almoco, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_LOGIN_URL, listener, null);
        params = new HashMap<>();

        params.put("email", email);
        params.put("inscrito", Integer.toString(inscrito));
        params.put("almoco", Integer.toString(almoco));

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
