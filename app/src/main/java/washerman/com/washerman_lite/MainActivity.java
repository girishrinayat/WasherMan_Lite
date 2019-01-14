package washerman.com.washerman_lite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sharedpreference.SharedPreferenceTest;

/**
 * @author GIRISH
 */
public class MainActivity extends AppCompatActivity {
    private SharedPreferenceTest sharedPreference;
    private Boolean flag;
    private ProgressDialog progressDialog;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
     *assign layout to toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    /**
     * event for toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                finish();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                return true;

            case R.id.history:
                finish();
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                return true;

            case R.id.logout:
                sharedPreference.logouts();
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                return true;

            case R.id.exit:
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

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
