package code.github.networking.githubauth;

import java.util.Map;

import code.github.networking.githubauth.pojo.AccessToken;
import code.github.networking.githubauth.pojo.GitHubUser;
import code.github.pojo.SearchResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by shank on 9/26/17.
 */

public interface OauthAPI {

    @GET
    Observable<AccessToken> getAccessToken(@Url String url, @QueryMap Map<String,String> map );

    @GET("user")
    Observable<GitHubUser> getUserInfo(@QueryMap Map<String,String> map );
}
