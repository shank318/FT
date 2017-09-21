package code.github.features.search;

import java.util.List;

import code.github.pojo.Repository;
import code.github.pojo.SearchResult;

/**
 * Created by shank on 06/09/17.
 */

public interface IUiView {
    void showErrorMessage(Throwable throwable);

    void onDataReceived(List<Repository> searchRepositories);
    void hideDialog();

    void showDialog();

    void onEmptySearchResult();

    void showNoInternetError(Throwable throwable);
}
