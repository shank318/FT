package code.github.features.search;

import java.util.HashMap;
import java.util.Map;

import code.github.networking.GetSearchDataApi;
import code.github.pojo.SearchResult;
import rx.Observable;

/**
 * Created by shank on 06/09/17.
 */

public class Service {

    GetSearchDataApi searchDataApi;
    public Service(GetSearchDataApi searchDataApi) {
        this.searchDataApi = searchDataApi;
    }

    public Observable<SearchResult> fetchContactsFromInternet(String query) {
        Map<String,String> map = new HashMap<>();
        map.put("q",query);
        map.put("sort","stars");
        map.put("order","desc");
        return searchDataApi.getSearchData(map);
    }


}
