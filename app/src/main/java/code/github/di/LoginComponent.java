package code.github.di;

import code.github.features.login.LoginActivity;
import dagger.Component;

/**
 * Created by shank on 9/21/17.
 */

@Component(modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
