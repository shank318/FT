package code.github.di;

import code.github.base.MyApplication;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by shank on 06/09/17.
 */
@Component(modules={ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {
    void inject(MyApplication application);
    Retrofit getRetrofit();
}