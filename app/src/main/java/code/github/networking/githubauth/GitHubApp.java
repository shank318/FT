package code.github.networking.githubauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import code.github.constants.Constants;
import code.github.di.ApiModule;
import code.github.di.DaggerApplicationComponent;
import code.github.networking.ConnectivityInterceptor;
import code.github.networking.githubauth.GithubDialog.OAuthDialogListener;
import code.github.networking.githubauth.di.AppComponent;
import code.github.networking.githubauth.di.AppModule;
import code.github.networking.githubauth.di.DaggerAppComponent;
import code.github.networking.githubauth.pojo.AccessToken;
import code.github.networking.githubauth.pojo.GitHubUser;
import code.github.pojo.Repository;
import code.github.utils.Logger;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import javax.inject.Inject;


/**
 * Created by shank on 9/21/17.
 */
public class GitHubApp {
	@Inject
	GithubSession mSession;

	@Inject
	GitHubService service;
	private Context context;
	private GithubDialog mDialog;
	private OAuthAuthenticationListener mListener;
	private ProgressDialog mProgress;
	private CompositeSubscription subscriptions = new CompositeSubscription();
	private String mAccessToken;
	Map<String,String> map = new HashMap<>();
	private static final String TAG = "GitHubAPI";

	public void buildComponent(){
		AppComponent component = DaggerAppComponent.builder()
				.appModule(new AppModule(Constant.API_URL,context))
				.build();
		component.inject(this);
	}


	public GitHubApp(Context context, String clientId, String clientSecret,
                     String callbackUrl) {
		this.context = context;
		buildComponent();

		// Build Map
		map.put("client_id",clientId);
		map.put("client_secret",clientSecret);
		map.put("redirect_uri",callbackUrl);

		String mAuthUrl = Constant.AUTH_URL + "client_id=" + clientId + "&redirect_uri="
				+ callbackUrl;

		OAuthDialogListener listener = new OAuthDialogListener() {
			@Override
			public void onComplete(String code) {
				map.put("code",code);
				getAccessToken();
			}

			@Override
			public void onError(String error) {
				mListener.onFail("Authorization failed");
			}
		};

		mDialog = new GithubDialog(context, mAuthUrl, listener, callbackUrl);
		mProgress = new ProgressDialog(context);
		mProgress.setCancelable(false);
	}

	private void getAccessToken() {
		showDialog("Getting access token ...");
		addSubscription(service.fetchAccessToken(Constant.TOKEN_URL,map)
				.subscribeOn(Schedulers.io())
				.observeOn(Schedulers.computation())
				.flatMap(accessToken ->{
					mAccessToken = accessToken.getAccessToken();
					return service.fetchUserInfo(accessToken.getAccessToken());
				})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::onSuccess, this::onLoadFailure));
	}

	void onSuccess(GitHubUser user) {
		hideDialog();
		Logger.debug("Logged in As: "+mAccessToken);
		mSession.storeAccessToken(mAccessToken, user.getId(), user.getLogin());
		mListener.onSuccess();
	}

	void onLoadFailure(Throwable throwable) {
		hideDialog();
		mListener.onFail(throwable.getMessage());
	}



	void showDialog(String message){
		if(mProgress!=null){
			mProgress.setMessage(message);
			mProgress.show();
		}
	}

	void hideDialog(){
		if(mProgress!=null){
			mProgress.dismiss();
		}
	}

	public boolean hasAccessToken() {
		return (mSession.getAccessToken() == null) ? false : true;
	}

	public void setListener(OAuthAuthenticationListener listener) {
		mListener = listener;
	}

	public String getUserName() {
		return mSession.getUsername();
	}

	public void authorize() {
		if(mDialog!=null) mDialog.show();
	}


	public void resetAccessToken() {
		mSession.resetAccessToken();
	}

	public void dismissDialog(){
		context = null;
		subscriptions.clear();
		if(mProgress!=null && mProgress.isShowing()){
			mProgress.dismiss();
		}
		mProgress=null;
	}

	public interface OAuthAuthenticationListener {
		void onSuccess();
		void onFail(String error);
	}

	protected void addSubscription(Subscription subscription){
		subscriptions.add(subscription);
	}
}