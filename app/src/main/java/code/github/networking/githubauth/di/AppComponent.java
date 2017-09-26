package code.github.networking.githubauth.di;

import code.github.di.ApiModule;
import code.github.di.ApplicationModule;
import code.github.networking.githubauth.GitHubApp;
import dagger.Component;

/**
 * Created by shank on 9/26/17.
 */

@Component(modules={AppModule.class})
public interface AppComponent {

    void inject(GitHubApp gitHubApp);
}
