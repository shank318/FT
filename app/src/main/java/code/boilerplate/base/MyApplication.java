package code.boilerplate.base;

import android.app.Application;

import com.facebook.stetho.Stetho;

import code.boilerplate.constants.Constants;
import code.boilerplate.di.ApiModule;
import code.boilerplate.di.ApplicationComponent;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by shank on 06/09/17.
 */

public class MyApplication extends Application {

    ApplicationComponent component;
    private static MyApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        setUpDagger();
        initializeRealm();
        mInstance = this;
        Stetho.initializeWithDefaults(this);
    }

    void setUpDagger(){
        component = DaggerApplicationComponent.builder()
                        .apiModule(new ApiModule(Constants.BASE_URL))
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
}
