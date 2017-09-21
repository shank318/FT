package code.github.base;

/**
 * Created by shank on 06/09/17.
 */

public interface IMvpPresenter<T> {

    void attachView(T view);
    void detachView();
    boolean isViewAttached();
    T getView();
}
