package code.github.networking.githubauth.di;

import android.content.Context;
import android.os.Build;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Singleton;

import code.github.base.MyApplication;
import code.github.networking.ConnectivityInterceptor;
import code.github.networking.GitHubAPI;
import code.github.networking.githubauth.GitHubService;
import code.github.networking.githubauth.GithubSession;
import code.github.networking.githubauth.OauthAPI;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shank on 9/26/17.
 */

@Module
public class AppModule {

    private String baseUrl;
    private Context context;
    public AppModule(String baseUrl, Context context){
        this.baseUrl = baseUrl;
        this.context = context;
    }

    @Provides
    public Retrofit providesRetrofit(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ConnectivityInterceptor(MyApplication.getInstance()))
                .addInterceptor(chain -> {
                    Request.Builder requestBuilder = chain.request().newBuilder();
                    requestBuilder.header("Accept", "application/json");
                    return chain.proceed(requestBuilder.build());
                })
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    public GitHubService providesGithubService(OauthAPI oauthAPI){
        return new GitHubService(oauthAPI);
    }

    @Provides
    public OauthAPI providesApi(Retrofit retrofit){
        return retrofit.create(OauthAPI.class);
    }

    @Provides
    public GithubSession providesGitHubSession(){
        return new GithubSession(context);
    }

}
