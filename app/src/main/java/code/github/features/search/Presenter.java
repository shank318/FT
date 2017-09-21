package code.github.features.search;

import java.net.UnknownHostException;
import java.util.List;

import code.github.base.BasePresenter;
import code.github.networking.ConnectivityInterceptor;
import code.github.pojo.FilmLocation;
import code.github.pojo.SearchResult;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shank on 06/09/17.
 */

public class Presenter extends BasePresenter<IUiView> {

    Service service;
    public Presenter(Service service){
        this.service = service;
    }

    public void getData(String query) {
        getView().showDialog();
        addSubscription(service.fetchContactsFromInternet(query)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadSuccess, this::onLoadFailure));
    }

    void onLoadSuccess(SearchResult result) {

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
