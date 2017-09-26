package code.github.features.login;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import code.github.R;
import code.github.base.BaseActivity;
import code.github.di.DaggerLoginComponent;
import code.github.di.LoginComponent;
import code.github.di.LoginModule;
import code.github.features.search.SearchActivity;
import code.github.networking.githubauth.GitHubApp;

/**
 * Created by shank on 9/21/17.
 */

public class LoginActivity extends BaseActivity implements ILoginView  {

    @Inject
    GitHubApp app;
    LoginPresenter loginPresenter;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        // Build dagger dev
        LoginComponent component = DaggerLoginComponent.builder()
                .loginModule(new LoginModule(this)).build();
        component.inject(this);
        loginPresenter = new LoginPresenter(app);
        loginPresenter.attachView(this);
    }

    @OnClick(R.id.login)
    protected void onLogin(){
        loginPresenter.initLogin();
    }

    void startSearchActivity(){
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        app.dismissDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }

    @Override
    public void onError(String error) {
        showToast(error);
    }

    @Override
    public void onLoginSuccess() {
        startSearchActivity();
    }
}
