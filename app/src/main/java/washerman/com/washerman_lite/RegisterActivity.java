package washerman.com.washerman_lite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private EditText eFullName;
    private EditText eEmail;
    private EditText ePassword;
    private EditText eConfirmPassword;
    private EditText eMobileNo;
    private Button bSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        eFullName = (EditText) findViewById(R.id.name);
        eEmail = (EditText) findViewById(R.id.email);
        ePassword = (EditText) findViewById(R.id.password);
        eConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        eMobileNo = (EditText) findViewById(R.id.mobile);
        bSubmit = (Button) findViewById(R.id.submit);

    }
}
