package code.github.features.search;

import java.util.List;

import code.github.pojo.FilmLocation;
import code.github.pojo.Item;

/**
 * Created by shank on 06/09/17.
 */

public interface IUiView {
    void showErrorMessage(Throwable throwable);

    void onDataReceived(List<Item> searchItems);

    void hideDialog();

    void showDialog();

    void onEmptySearchResult();

    void showNoInternetError(Throwable throwable);
}
