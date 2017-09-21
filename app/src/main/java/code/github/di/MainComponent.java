package code.github.di;

import code.github.features.search.SearchActivity;
import dagger.Component;

/**
 * Created by shank on 06/09/17.
 */

@Component(dependencies = ApplicationComponent.class,modules = MainModule.class)
public interface MainComponent {
    void inject(SearchActivity searchActivity);
}
