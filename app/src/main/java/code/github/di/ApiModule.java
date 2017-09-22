package code.github.di;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Singleton;

import code.github.base.MyApplication;
import code.github.networking.ConnectivityInterceptor;
import code.github.networking.githubauth.GithubSession;
import code.github.utils.Logger;
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
 * Created by shank on 06/09/17.
 */
@Module
public class ApiModule {
    private String baseUrl;
    private Context context;
    public ApiModule(String baseUrl, Context context){
        this.baseUrl = baseUrl;
        this.context = context;
    }

    @Singleton
    @Provides
    public Gson providesGson(){
        return new Gson();
    }

    @Singleton
    @Provides
    public Retrofit providesRetrofit(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ConnectivityInterceptor(MyApplication.getInstance()))
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public GithubSession providesGitHubSession(){
        return new GithubSession(context);
    }

}
