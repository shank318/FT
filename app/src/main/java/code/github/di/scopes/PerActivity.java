package code.github.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by shank on 9/22/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {}
