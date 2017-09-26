package code.github.features.search;

import android.support.annotation.NonNull;
import android.util.Log;

import java.net.UnknownHostException;
import java.util.List;

import code.github.base.BasePresenter;
import code.github.networking.ConnectivityInterceptor;
import code.github.pojo.Repository;
import code.github.pojo.SearchResult;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
            getViewOrThrow().showDialog();
            addSubscription(service.fetchSearchData(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .flatMap(searchResult-> Observable.just(searchResult.getRepositories()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSuccess, this::onLoadFailure));

    }

    @NonNull
    public IUiView getViewOrThrow() {
        final IUiView view = getView();
        if (view == null) {
            throw new IllegalStateException("view not attached");
        }
        return view;
    }

    public void getUserRepositories() {
        // Show dialog only if there is no cache
        if(service.checkIfRepoExists()){
            getViewOrThrow().onDataReceived(service.readAllReopsFromRealm());
        }else{
            getViewOrThrow().showDialog();
        addSubscription(service.fetchUserRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap(service::writeToRealm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onLoadFailure));
        }
    }


    void onSuccess(List<Repository> reops) {
        getViewOrThrow().hideDialog();
        getViewOrThrow().onDataReceived(reops);
    }

    void onLoadFailure(Throwable throwable) {
        getViewOrThrow().hideDialog();
        if(throwable instanceof ConnectivityInterceptor.NoConnectivityException
                || throwable instanceof UnknownHostException){
            // No internet connection
            getViewOrThrow().showNoInternetError(throwable);
        }else{
            getViewOrThrow().showErrorMessage(throwable);
        }
    }

}
