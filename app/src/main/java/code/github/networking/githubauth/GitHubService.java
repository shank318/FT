package code.github.networking.githubauth;

import java.util.HashMap;
import java.util.Map;

import code.github.networking.GitHubAPI;
import code.github.networking.githubauth.pojo.AccessToken;
import code.github.networking.githubauth.pojo.GitHubUser;
import code.github.pojo.SearchResult;
import rx.Observable;

/**
 * Created by shank on 9/26/17.
 */

public class GitHubService {

    OauthAPI api;

    public GitHubService(OauthAPI api) {
        this.api = api;
    }

    private GitHubService(){

    }

    public Observable<AccessToken> fetchAccessToken(String url, Map<String,String> map){
        return api.getAccessToken(url,map);
    }

    public Observable<GitHubUser> fetchUserInfo(String token){
        Map<String,String> map = new HashMap<>();
        map.put("access_token",token);
        return api.getUserInfo(map);
    }
}
