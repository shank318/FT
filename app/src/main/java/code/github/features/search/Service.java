package code.github.features.search;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import code.github.base.MyApplication;
import code.github.networking.GitHubAPI;
import code.github.pojo.Repository;
import code.github.pojo.SearchResult;
import code.github.utils.GsonUtil;
import code.github.utils.Logger;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;

/**
 * Created by shank on 06/09/17.
 */

public class Service {

    GitHubAPI searchDataApi;
    public Service(GitHubAPI searchDataApi) {
        this.searchDataApi = searchDataApi;
    }

    private Service(){

    }
    public Observable<SearchResult> fetchSearchData(String query) {
        Map<String,String> map = new HashMap<>();
        map.put("q",query);
        map.put("sort","stars");
        map.put("order","desc");
        map.put("per_page","10");
        map.put("access_token", MyApplication.getInstance().getGithubSession().getAccessToken());
        return searchDataApi.getSearchData(map);
    }

    public Observable<List<Repository>> fetchUserRepos() {
        Map<String,String> map = new HashMap<>();
        map.put("access_token", MyApplication.getInstance().getGithubSession().getAccessToken());
        return searchDataApi.getUserReops(map);
    }

    public RealmResults<Repository> readAllReopsFromRealm() {
        return Realm.getDefaultInstance().where(Repository.class)
                .findAll();
    }

    public boolean checkIfRepoExists(){
        Repository realmContact = Realm.getDefaultInstance().where(Repository.class).findFirst();
        return realmContact!=null ? true : false;
    }

    public Observable<List<Repository>> writeToRealm(List<Repository> repositories) {
        Realm realm = Realm.getDefaultInstance();
        JSONArray contactsJsonArray = new JSONArray();
        Gson gson = GsonUtil.getGson();
        try {
            String jsonString = gson.toJson(
                    repositories,
                    new TypeToken<ArrayList<Repository>>() {}.getType());
            Log.e("gw",jsonString+"");
            contactsJsonArray = new JSONArray(jsonString);
            realm.beginTransaction();
            realm.createOrUpdateAllFromJson(Repository.class, contactsJsonArray);
            realm.commitTransaction();
        } catch (JSONException e) {
            Logger.error(e.getMessage());
        }finally {
            realm.close();
        }
        return Observable.just(repositories);
    }

}
