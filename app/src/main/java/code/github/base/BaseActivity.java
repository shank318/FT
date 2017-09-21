package code.github.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import code.github.di.ApplicationComponent;
import code.github.features.login.LoginActivity;

/**
 * Created by shank on 06/09/17.
 */

public class BaseActivity extends AppCompatActivity {


    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }

    public ApplicationComponent getApplicationComponent(){
        return ((MyApplication)getApplication()).getApplicationComponent();
    }

    protected boolean isTextEmpty(String string){
        return TextUtils.isEmpty(string);
    }

    public void showLoginScreen(){
        Intent i= new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
