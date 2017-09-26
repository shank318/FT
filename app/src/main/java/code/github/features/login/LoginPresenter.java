package code.github.features.login;

import android.support.annotation.NonNull;
import android.view.View;

import code.github.base.BasePresenter;
import code.github.features.search.IUiView;
import code.github.networking.githubauth.GitHubApp;
import code.github.utils.Logger;

/**
 * Created by shank on 9/22/17.
 */

public class LoginPresenter extends BasePresenter<ILoginView> {

    GitHubApp gitHubApp;

    public LoginPresenter(GitHubApp gitHubApp){
        this.gitHubApp = gitHubApp;
    }

    boolean isUserLoggedIn(){
        return gitHubApp.hasAccessToken();
    }

    public void initLogin(){
        gitHubApp.setListener(listener);
        gitHubApp.authorize();
    }


    private GitHubApp.OAuthAuthenticationListener listener = new GitHubApp.OAuthAuthenticationListener() {

        @Override
        public void onSuccess() {
            Logger.debug("Connected as " + gitHubApp.getUserName());
            ILoginView view = getView();
            if(view!=null)view.onLoginSuccess();

        }

        @Override
        public void onFail(String error) {
            ILoginView view = getView();
            if(view!=null)view.onError(error);

        }
    };


}
