package washerman.com.washerman_lite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author GIRISH
 */
public class RegisterActivity extends AppCompatActivity {
    private EditText eFullName;
    private String fullName;
    private EditText eEmail;
    private String email;
    private EditText ePassword;
    private String password;
    private EditText eConfirmPassword;
    private String cPassword;
    private EditText eMobileNo;
    private String mobileNo;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private Button bSubmit;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /**
         *  Assigning Activity this to progress dialog.
         */
        progressDialog = new ProgressDialog(RegisterActivity.this);
        eFullName = (EditText) findViewById(R.id.name);
        eEmail = (EditText) findViewById(R.id.email);
        ePassword = (EditText) findViewById(R.id.password);
        eConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        eMobileNo = (EditText) findViewById(R.id.mobile);
        bSubmit = (Button) findViewById(R.id.submit);
        mAuth = FirebaseAuth.getInstance();
        clickEvents();
    }

    private void clickEvents() {
        if (!isNetworkAvailable()){
            progressDialog.dismiss();
            Toast.makeText(RegisterActivity.this, "check internet connection ", Toast.LENGTH_SHORT).show();
        }else{
            bSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.setMessage("Loading......");
                    progressDialog.show();
                    boolean status = validation();
                    if (status == true) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("", "createUserWithEmail:success");
                                            sendVerificationEmail();
                                        } else {
                                            progressDialog.dismiss();
                                            // If sign in fails, display a message to the user.
                                            Log.w("", "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(RegisterActivity.this, "Authentication failed."+task.getException(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            });
        }
    }
    private void sendVerificationEmail()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId =  user.getUid();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            myRef = database.getReference("User Details").child("User").child(userId);
                            // store in Real time database of firebase
                            myRef.child("Name").setValue(fullName);
                            myRef.child("Email").setValue(email);
                            myRef.child("Mobile").setValue(mobileNo);
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Email verification link send sucessfully check your inbox.", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                        }else{
                            progressDialog.dismiss();
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            FirebaseAuth.getInstance().signOut();
                            //restart this activity
                            Toast.makeText(RegisterActivity.this, "Error while sending verification link check email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validation() {
        int count = 0;
        fullName = eFullName.getText().toString();
        email = eEmail.getText().toString();
        password = ePassword.getText().toString();
        cPassword = eConfirmPassword.getText().toString();
        mobileNo = eMobileNo.getText().toString();

        if (fullName.length()==0){
            eFullName.setError("Field Cannot be Empty");
            count++;
        }
        if(email.length()==0){
            eEmail.setError("Field Cannot be Empty");
            count++;
        }
        if (password.length()==0){
            ePassword.setError("Field Cannot be Empty");
            count++;
        }
        if (cPassword.length()==0){
            eConfirmPassword.setError("Field Cannot be Empty");
            count++;
        }
        if (!(password.equals(cPassword))){
            eConfirmPassword.setError("Password not matched");
            count++;
        }
        if (mobileNo.length()==0){
            eMobileNo.setError("Field Cannot be Empty");
            count++;
        }

        if (count>0){
         return false;
        }
        return true;
    }

    /**
     * runtime network state
     * @return network state
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&activeNetwork.isConnectedOrConnecting();
    }
}
