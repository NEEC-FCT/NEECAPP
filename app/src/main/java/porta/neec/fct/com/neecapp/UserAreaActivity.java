package porta.neec.fct.com.neecapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final TextView nome = (TextView) findViewById(R.id.textView);
        final TextView age = (TextView) findViewById(R.id.textView3);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String pontos = intent.getStringExtra("age");

        nome.setText(name);
    }
}
