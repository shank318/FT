package code.boilerplate.di;

import code.boilerplate.features.Service;
import code.boilerplate.networking.GetDataApi;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by shank on 06/09/17.
 */

@Module
public class MainModule {

    @Provides
    public Service providesService(GetDataApi getDataApi){
        return new Service(getDataApi);
    }

    @Provides
    public GetDataApi providesApi(Retrofit retrofit){
        return retrofit.create(GetDataApi.class);
    }
}
