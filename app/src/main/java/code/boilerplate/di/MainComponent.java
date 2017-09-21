package code.boilerplate.di;

import code.boilerplate.features.MainActivity;
import dagger.Component;

/**
 * Created by shank on 06/09/17.
 */

@Component(dependencies = ApplicationComponent.class,modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
