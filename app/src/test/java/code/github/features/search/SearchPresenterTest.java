package code.github.features.search;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import code.github.RxSchedulersOverrideRule;
import code.github.pojo.Repository;
import code.github.networking.ConnectivityInterceptor;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by shank on 9/21/17.
 */

public class SearchPresenterTest {
    Presenter presenter;
    @Mock
    Service mockedRepository;
    @Mock
    IUiView mockedView;

    @Mock
    List<Repository> repositories;
    @Rule
    public RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new Presenter(mockedRepository);
        presenter.attachView(mockedView);
    }

    @After
    public void tearDown() throws Exception {
        presenter.detachView();
    }

    @Test
    public void getUserReposTest() {
        TestScheduler testScheduler = new TestScheduler();
        TestSubscriber<List<Repository>> testSubscriber = new TestSubscriber<>();
        Observable<List<Repository>> responseObservable = Observable.just(repositories).subscribeOn(testScheduler);
        responseObservable.subscribe(testSubscriber);
        when(mockedRepository.fetchUserRepos()).thenReturn(responseObservable);
        presenter.getUserRepositories();
        testScheduler.triggerActions();
        verify(mockedView).showDialog();
    }


    @Test
    public void onLoadSuccessTest(){
        List<Repository> repositories = new ArrayList<>();
        presenter.onSuccess(repositories);
        verify(presenter.getView()).hideDialog();
        verify(presenter.getView()).onDataReceived(repositories);
        verify(presenter.getView(), times(1)).hideDialog();
        verify(presenter.getView(), never()).showErrorMessage(new Throwable());
    }

    @Test
    public void onLoadFailureTest(){
        Throwable t = new Throwable();
        presenter.onLoadFailure(t);
        verify(presenter.getView()).hideDialog();
        verify(presenter.getView()).showErrorMessage(t);
        verify(presenter.getView(), times(1)).hideDialog();
        verify(presenter.getView(), never()).showNoInternetError(t);
    }

    @Test
    public void onLoadFailureTestWhenNoInternet(){
        Exception exception = new ConnectivityInterceptor.NoConnectivityException();
        presenter.onLoadFailure(exception);
        verify(presenter.getView()).hideDialog();
        verify(presenter.getView(), never()).showErrorMessage(exception);
        verify(presenter.getView()).showNoInternetError(exception);
        verify(presenter.getView(), times(1)).hideDialog();
    }


}
