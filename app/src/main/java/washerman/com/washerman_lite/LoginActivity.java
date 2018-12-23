package washerman.com.washerman_lite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sharedpreference.SharedPreferenceTest;

public class LoginActivity extends AppCompatActivity {
    private EditText eEmail;
    private EditText ePassword;
    private CheckBox cCheckBox;
    private Button bSubmit;
    private TextView tForgetPassword;
    private TextView tRegisterHere;

    //shared prefrence
    private SharedPreferenceTest sharedPreference;
    private Boolean flag;
    // Creating Progress dialog.
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(LoginActivity.this);
        // Creating  new sharedPreference.
        sharedPreference = new SharedPreferenceTest(LoginActivity.this);

        flag = sharedPreference.getBool("flag");

        eEmail = (EditText) findViewById(R.id.email);
        ePassword = (EditText) findViewById(R.id.password);
        cCheckBox  = (CheckBox) findViewById(R.id.checkbox);
        bSubmit = (Button) findViewById(R.id.submit);
        tForgetPassword = (TextView) findViewById(R.id.forgetPassword);
        tRegisterHere = (TextView) findViewById(R.id.register);

        clickEvents();
    }

    private void clickEvents() {
        //show password
        cCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    // show password
                    ePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Log.i("checker", "true");
                }else {
                    // hide password
                    ePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        //For Login.......
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Submit Click", Toast.LENGTH_SHORT).show();
            }
        });

        //for ForgetPassword....
        tForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Forget password", Toast.LENGTH_SHORT).show();
            }
        });

        //For Register....
        tRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
}
