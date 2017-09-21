package code.github.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by shank on 06/09/17.
 */

public class BasePresenter<T> implements IMvpPresenter<T> {

    private T view;
    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    public void attachView(T view){
        this.view = view;
    }


    @Override
    public void detachView() {
        this.view = null;
        subscriptions.clear();
    }

    @Override
    public boolean isViewAttached(){
        return view != null;
    }

    @Override
    public T getView(){
        return view;
    }

    protected void addSubscription(Subscription subscription){
        subscriptions.add(subscription);
    }

    public ViewNotAttachedException checkViewAttached(){
        return new ViewNotAttachedException();
    }

    private static class ViewNotAttachedException extends RuntimeException{
        public ViewNotAttachedException() {
            super("Please call Presenter.attachView(mvpView) before using Presenter");
        }
    }

}

