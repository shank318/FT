package code.github.di;

import code.github.features.search.Service;
import code.github.networking.GetSearchDataApi;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by shank on 06/09/17.
 */

@Module
public class MainModule {

    @Provides
    public Service providesService(GetSearchDataApi getSearchDataApi){
        return new Service(getSearchDataApi);
    }

    @Provides
    public GetSearchDataApi providesApi(Retrofit retrofit){
        return retrofit.create(GetSearchDataApi.class);
    }
}
