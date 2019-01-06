package washerman.com.washerman_lite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.sharedpreference.SharedPreferenceTest;

/**
 * @author GIRISH
 */
public class MainActivity extends AppCompatActivity {
    private SharedPreferenceTest sharedPreference;
    private Boolean flag;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         *  Assigning Activity this to progress dialog.
         */
        progressDialog = new ProgressDialog(MainActivity.this);
        /**
         * Creating  new sharedPreference.
         */
        sharedPreference = new SharedPreferenceTest(MainActivity.this);
        flag = sharedPreference.getBool("flag");

        /**
         * Checking Network State
         */
       if (!isNetworkAvailable()){
           progressDialog.dismiss();
           Toast.makeText(MainActivity.this, "check internet connection ", Toast.LENGTH_SHORT).show();
       }else{
           /**
            * first time it user no in logged in state.....
            */
           if (!flag){
               finish();
               startActivity(new Intent(this,LoginActivity.class));
           }
       }
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
