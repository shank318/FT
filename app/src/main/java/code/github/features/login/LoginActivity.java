package code.github.features.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import code.github.base.BaseActivity;

/**
 * Created by shank on 9/21/17.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
