package code.github.di;

import android.content.Context;

import code.github.base.MyApplication;
import code.github.constants.Constants;
import code.github.networking.githubauth.GithubApp;
import dagger.Module;
import dagger.Provides;

/**
 * Created by shank on 9/21/17.
 */

@Module
public class LoginModule {

    Context context;
    public LoginModule(Context context){
        this.context = context;
    }

    @Provides
    public GithubApp providesGitHub(){
        return new GithubApp(context, MyApplication.getInstance().getGithubSession(), Constants.CLIENT_ID,Constants.CLIENT_SECRET,Constants.CALLBACK_URL);
    }

}
