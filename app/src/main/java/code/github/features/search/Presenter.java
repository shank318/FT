package code.github.features.search;

import android.util.Log;

import java.net.UnknownHostException;
import java.util.List;

import code.github.base.BasePresenter;
import code.github.networking.ConnectivityInterceptor;
import code.github.pojo.Repository;
import code.github.pojo.SearchResult;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by shank on 06/09/17.
 */

public class Presenter extends BasePresenter<IUiView> {

    Service service;
    public Presenter(Service service){
        this.service = service;
    }
    private Presenter(){

    }

    public void search(String query) {
            getView().showDialog();
            addSubscription(service.fetchSearchData(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .flatMap(new Func1<SearchResult, Observable<List<Repository>>>() {
                        @Override
                        public Observable<List<Repository>> call(SearchResult searchResult) {
                            return Observable.just(searchResult.getRepositories());
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSuccess, this::onLoadFailure));

    }

    public void getUserRepositories() {
        // Show dialoge only if there is no cache
        if(service.checkIfRepoExists()){
            getView().onDataReceived(service.readAllReopsFromRealm());
        }else{
        getView().showDialog();
        addSubscription(service.fetchUserRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap(service::writeToRealm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onLoadFailure));
        }
    }


    void onSuccess(List<Repository> reops) {
        getView().hideDialog();
        getView().onDataReceived(reops);
    }

    void onLoadFailure(Throwable throwable) {
        getView().hideDialog();
        if(throwable instanceof ConnectivityInterceptor.NoConnectivityException
                || throwable instanceof UnknownHostException){
            // No internet connection
            getView().showNoInternetError(throwable);
        }else{
            getView().showErrorMessage(throwable);
        }
    }

}
