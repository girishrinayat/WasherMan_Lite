package washerman.com.washerman_lite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText eEmail;
    private EditText ePassword;
    private CheckBox cCheckBox;
    private Button bSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eEmail = (EditText) findViewById(R.id.email);
        ePassword = (EditText) findViewById(R.id.password);
        cCheckBox  = (CheckBox) findViewById(R.id.checkbox);
        bSubmit = (Button) findViewById(R.id.submit);
    }
}
