package code.github.base;

import code.github.constants.Constants;
import code.github.di.ApiModule;
import code.github.di.DaggerApplicationComponent;
import io.appflate.restmock.RESTMockServer;

/**
 * Created by shank on 9/21/17.
 */

public class MyTestApplication extends MyApplication {

    @Override
    public void buildComponent() {
        component = DaggerApplicationComponent.builder()
                .apiModule(new ApiModule(Constants.BASE_URL, this))
                .build();
        component.inject(this);
    }
}
