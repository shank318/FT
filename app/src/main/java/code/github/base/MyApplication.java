package code.github.base;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import code.github.constants.Constants;
import code.github.di.ApiModule;
import code.github.di.ApplicationComponent;
import code.github.di.DaggerApplicationComponent;
import code.github.networking.githubauth.GithubSession;
import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by shank on 06/09/17.
 */

public class MyApplication extends Application {

    ApplicationComponent component;
    private static MyApplication mInstance;

    @Inject
    GithubSession githubSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        buildComponent();
        mInstance = this;
        initializeRealm();
    }

    public void buildComponent(){
        component = DaggerApplicationComponent.builder()
                        .apiModule(new ApiModule(Constants.BASE_URL, this))
                        .build();
        component.inject(this);
    }

    private void initializeRealm() {
        // Realm setup
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);
    }


    public static MyApplication getInstance(){
        return mInstance;
    }

    public ApplicationComponent getApplicationComponent() {
        return component;
    }

    public GithubSession getGithubSession(){
        return githubSession;
    }
}
