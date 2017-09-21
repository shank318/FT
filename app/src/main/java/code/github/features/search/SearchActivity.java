package code.github.features.search;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import code.github.base.BaseActivity;
import code.github.di.MainComponent;
import code.github.di.MainModule;
import code.github.pojo.FilmLocation;
import code.github.pojo.Item;
import code.github.utils.Logger;
import code.github.utils.ViewUtil;

import static java.security.AccessController.getContext;

public class SearchActivity extends BaseActivity implements IUiView {

    @Inject
    Service service;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    MainComponent component;
    Presenter presenter;
    List<Item> searchItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initializeDependencies();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SearchAdapter searchAdapter = new SearchAdapter(getContext(),searchItems);
//        recyclerView.addItemDecoration(
//                new DividerItemDecoration(getCon));
        recyclerView.setAdapter(searchAdapter);
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

    }

    @Override
    public void onDataReceived(List<Item> searchItems) {

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
        presenter.detachView();
        ViewUtil.hideDialog(progressDialog);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    public void showNoInternetError(Throwable throwable) {
        showToast(throwable.getMessage());
    }
}
