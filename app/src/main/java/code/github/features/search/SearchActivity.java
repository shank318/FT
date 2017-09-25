package code.github.features.search;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import code.github.R;
import code.github.base.BaseActivity;
import code.github.base.MyApplication;
import code.github.di.DaggerMainComponent;
import code.github.di.MainComponent;
import code.github.di.MainModule;
import code.github.networking.githubauth.GithubSession;
import code.github.pojo.Repository;
import code.github.utils.Logger;
import code.github.utils.SimpleItemDecorator;
import code.github.utils.ViewUtil;
import retrofit2.Retrofit;

public class SearchActivity extends BaseActivity implements IUiView {

    @Inject
    Service service;

    @BindView(R.id.empty_view)
    View emptytView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    MainComponent component;
    Presenter presenter;
    List<Repository> searchRepositories = new ArrayList<Repository>();
    SearchAdapter searchAdapter;
    @BindView(R.id.searchbox)
    EditText searchBox;
    @BindView(R.id.snakbar)
    CoordinatorLayout snackbarHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        GithubSession githubSession = MyApplication.getInstance().getGithubSession();
        if(githubSession==null || githubSession.getAccessToken()==null) {
            showLoginScreen();
        }
        progressDialog = new ProgressDialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("GitHub: "+ githubSession.getUsername());
        initializeDependencies();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(this, searchRepositories);
        recyclerView.addItemDecoration(new SimpleItemDecorator(16));
        recyclerView.setAdapter(searchAdapter);
    }

    @OnClick(R.id.search_button)
    protected void onSearch(View v){
        if(searchBox.getText().toString().trim().length()>0){
            presenter.search(searchBox.getText().toString());
        }
        ViewUtil.hideKeyboard(this);
    }




    void initializeDependencies(){
        // injection
        component = DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainModule(new MainModule()).build();
        component.inject(this);

        // presenter
        presenter = (Presenter) getLastCustomNonConfigurationInstance();
        Logger.debug("Presenter: "+presenter);
        if (presenter == null) {
            presenter = new Presenter(service);
        }
        presenter.attachView(this);
        presenter.getUserRepositories();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showErrorMessage(Throwable throwable) {
        showToast(throwable.getMessage());
    }

    @Override
    public void onDataReceived(List<Repository> repositories) {
        if(repositories.size()==0) {
            recyclerView.setVisibility(View.GONE);
            emptytView.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            emptytView.setVisibility(View.GONE);
        }
        searchRepositories.clear();
        searchRepositories.addAll(repositories);
        if(searchAdapter!=null) searchAdapter.notifyDataSetChanged();
    }
    @Override
    public void showDialog() {
        ViewUtil.showProgressDialog(progressDialog);

    }

    @Override
    public void hideDialog() {
        ViewUtil.hideDialog(progressDialog);
    }

    @Override
    public void onEmptySearchResult() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.debug("On destroy called");
        presenter.detachView();
        ViewUtil.hideDialog(progressDialog);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(searchAdapter!=null) searchAdapter.notifyDataSetChanged();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    public void showNoInternetError(Throwable throwable) {
        final Snackbar snackbar = Snackbar
                .make(snackbarHolder, throwable.getMessage(), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.try_again), view -> {
                    presenter.getUserRepositories();
                });

        snackbar.show();
    }
}
