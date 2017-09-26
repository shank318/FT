package code.github.di;

import code.github.features.search.Service;
import code.github.networking.GitHubAPI;
import code.github.networking.githubauth.GithubSession;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by shank on 06/09/17.
 */

@Module
public class MainModule {

    @Provides
    public Service providesService(GitHubAPI gitHubAPI, GithubSession session){
        return new Service(gitHubAPI,session);
    }

    @Provides
    public GitHubAPI providesApi(Retrofit retrofit){
        return retrofit.create(GitHubAPI.class);
    }
}
