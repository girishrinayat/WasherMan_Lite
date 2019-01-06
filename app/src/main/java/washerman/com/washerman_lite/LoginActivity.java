package washerman.com.washerman_lite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sharedpreference.SharedPreferenceTest;
/**
 * @author GIRISH
 */
public class LoginActivity extends AppCompatActivity {
    private EditText eEmail;
    private String email;
    private EditText ePassword;
    private String password;
    private CheckBox cCheckBox;
    private Button bSubmit;
    private TextView tForgetPassword;
    private TextView tRegisterHere;
    private SharedPreferenceTest sharedPreference;
    private Boolean flag;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    public  static  final  String MyPref = "washermanlite";
    public  static  final  String Password = "Password_Key";
    public  static  final  String username = "username_key";
    public  static  final  String userID = "userId";
//    SharedPreferences applicationpreferences;
//    SharedPreferenceTest.Editor editor;
    String userId =  null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        /**
         *  Assigning Activity this to progress dialog.
         */
        progressDialog = new ProgressDialog(LoginActivity.this);
        /**
         * Creating  new sharedPreference.
         */
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

    /**
     * Button Click Operation handle here
     */
    private void clickEvents() {
        /**
         * Show password
         * or
         * Hide Password
         */
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
        /**
         * Login validation
         * and logic
         */
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable() ){
                    Toast.makeText(LoginActivity.this, "check internet connection ", Toast.LENGTH_SHORT).show();

                }else {
                    progressDialog.setMessage("Loading......");
                    progressDialog.show();
                    email = eEmail.getText().toString();
                    password = ePassword.getText().toString();
                    if (!email.equals("")&&!email.equals(null)&&!password.equals("")&&!password.equals(null)) {
                        //user checking through firebase auth
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            //Log.d(TAG, "signInWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            userId=user.getUid();
                                            checkIfEmailVerified();

                                            //   Toast.makeText(LoginActivity.this, "Login Sucess", Toast.LENGTH_SHORT).show();
//                                updateUI(user);
//                                        Log.d("User Login",user.toString());


                                        } else {
                                            FirebaseAuth.getInstance().signOut();
                                            // If sign in fails, display a message to the user.
//                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            // updateUI(null);
                                        }
                                    }
                                });
                    }else{
                        if (email.equals("")||email.equals(null)){
                            eEmail.setError("UserName cannot be empty!!!");
                            progressDialog.dismiss();
                        }
                        if (password.equals("")||password.equals(null)){
                            ePassword.setError("Password cannot be empty!!!");
                            progressDialog.dismiss();
                        }
                    }
                }
            }
        });

        /**
         * forget Password
         */
        tForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Forget password", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * register activity
         * intent to register activity
         */
        tRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.dismiss();
                finish();
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    private void checkIfEmailVerified()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user.isEmailVerified()) {
            // user is verified, so you can finish this activity or send user to activity which you want.
            finish();
            //first time
            sharedPreference.setStr("UserCode", email);
            sharedPreference.setStr("password", password);
            sharedPreference.setBool("flag",true);
            sharedPreference.setStr(userID, userId);
           //Login MainActivity
            progressDialog.dismiss();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }else{
            FirebaseAuth.getInstance().signOut();
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, "Please verify your email address check the link in your inbox.", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(LoginActivity.this,LoginActivity.class));

        }
    }

    //runtime network state
    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&activeNetwork.isConnectedOrConnecting();
    }
}
