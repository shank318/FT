package code.github.di;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by shank on 06/09/17.
 */
@Singleton
@Component(modules=ApiModule.class)
public interface ApiComponent {
}
