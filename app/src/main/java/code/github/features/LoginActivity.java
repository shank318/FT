package code.github.features;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import code.github.R;
import code.github.base.BaseActivity;
import code.github.base.MyApplication;
import code.github.constants.Constants;
import code.github.di.DaggerLoginComponent;
import code.github.di.DaggerMainComponent;
import code.github.di.LoginComponent;
import code.github.di.LoginModule;
import code.github.di.MainModule;
import code.github.features.search.SearchActivity;
import code.github.networking.githubauth.GithubApp;
import code.github.utils.Logger;
import code.github.utils.ViewUtil;

/**
 * Created by shank on 9/21/17.
 */

public class LoginActivity extends BaseActivity  {

    @Inject
    GithubApp app;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        LoginComponent component = DaggerLoginComponent.builder()
                .loginModule(new LoginModule(this)).build();
        component.inject(this);
        if (app.hasAccessToken()) {
            startSearchActivity();
        }
    }

    @OnClick(R.id.login)
    protected void onLogin(){
        app.setListener(listener);
        if (app.hasAccessToken()) {
            startSearchActivity();
        } else {
            app.authorize();
        }
    }

    void startSearchActivity(){
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
    }


    @Override
    protected void onPause() {
        super.onPause();
        app.dismissDialog();
    }

    private GithubApp.OAuthAuthenticationListener listener = new GithubApp.OAuthAuthenticationListener() {

        @Override
        public void onSuccess() {
            Logger.debug("Connected as " + app.getUserName());
            startSearchActivity();
        }

        @Override
        public void onFail(String error) {
            showToast(error);
        }
    };

}
