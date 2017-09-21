package code.github.di;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.io.IOException;

import code.github.base.MyApplication;
import code.github.networking.ConnectivityInterceptor;
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
    public ApiModule(String baseUrl){
        this.baseUrl = baseUrl;
    }

    @Provides
    public Gson providesGson(){
        return new Gson();
    }

    @Provides
    public Retrofit providesRetrofit(){
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "token " + token)
                        .addHeader("Accept", "application/vnd.github.mercy-preview+json")
                        .build();
                return chain.proceed(newRequest);
            }
        }).addInterceptor(new ConnectivityInterceptor(MyApplication.getInstance()))
                .addNetworkInterceptor(new StethoInterceptor()).build();


        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

}
