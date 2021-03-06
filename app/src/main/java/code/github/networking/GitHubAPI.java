package code.github.networking;

import java.util.List;
import java.util.Map;


import code.github.pojo.Repository;
import code.github.pojo.SearchResult;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by shank on 06/09/17.
 */

public interface GitHubAPI {
    @GET("search/repositories")
    Observable<SearchResult> getSearchData(@QueryMap Map<String,String> map);

    @GET("user/repos")
    Observable<List<Repository>> getUserReops( @QueryMap Map<String,String> map);

}
