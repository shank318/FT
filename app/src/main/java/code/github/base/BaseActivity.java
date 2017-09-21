package code.github.base;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import code.github.di.ApplicationComponent;

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
}
