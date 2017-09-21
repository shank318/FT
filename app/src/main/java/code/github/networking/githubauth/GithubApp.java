package code.github.networking.githubauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import code.github.networking.githubauth.GithubDialog.OAuthDialogListener;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


/**
 * Created by shank on 9/21/17.
 */
public class GithubApp {
	private GithubSession mSession;
	private GithubDialog mDialog;
	private OAuthAuthenticationListener mListener;
	private ProgressDialog mProgress;
	private String mAuthUrl;
	private String mTokenUrl;
	private String mAccessToken;

	/**
	 * Callback url, as set in 'Manage OAuth Costumers' page
	 * (https://developer.github.com/)
	 */

	public static String mCallbackUrl = "";
	private static final String AUTH_URL = "https://github.com/login/oauth/authorize?";
	private static final String TOKEN_URL = "https://github.com/login/oauth/access_token?";
	private static final String API_URL = "https://api.github.com";

	private static final String TAG = "GitHubAPI";

	public GithubApp(Context context,String clientId, String clientSecret,
			String callbackUrl) {
		mSession = new GithubSession(context);
		mAccessToken = mSession.getAccessToken();
		mCallbackUrl = callbackUrl;
		mTokenUrl = TOKEN_URL + "client_id=" + clientId + "&client_secret="
				+ clientSecret + "&redirect_uri=" + mCallbackUrl;
		mAuthUrl = AUTH_URL + "client_id=" + clientId + "&redirect_uri="
				+ mCallbackUrl;

		OAuthDialogListener listener = new OAuthDialogListener() {
			@Override
			public void onComplete(String code) {
				getAccessToken(code);
			}

			@Override
			public void onError(String error) {
				mListener.onFail("Authorization failed");
			}
		};

		mDialog = new GithubDialog(context, mAuthUrl, listener);
		mProgress = new ProgressDialog(context);
		mProgress.setCancelable(false);
	}

	private void getAccessToken(final String code) {
		showDialog("Getting access token ...");
		 new Thread() {
			@Override
			public void run() {
				Log.i(TAG, "Getting access token");
				int what = 0;

				try {
					URL url = new URL(mTokenUrl + "&code=" + code);
					Log.i(TAG, "Opening URL " + url.toString());
					HttpURLConnection urlConnection = (HttpURLConnection) url
							.openConnection();
					urlConnection.setRequestMethod("GET");
					urlConnection.setDoInput(true);
					urlConnection.setDoOutput(true);
					urlConnection.connect();
					String response = streamToString(urlConnection
							.getInputStream());
					Log.i(TAG, "response " + response);
					Map<String,String> map = getQueryMap(response);
					mAccessToken = map.get("access_token");
					Log.i(TAG, "Got access token: " + mAccessToken);
				} catch (Exception ex) {
					what = 1;
					ex.printStackTrace();
				}

				mHandler.sendMessage(mHandler.obtainMessage(what, 1, 0));
			}
		}.start();
	}

	public static Map<String, String> getQueryMap(String query)
	{
		String[] params = query.split("&");
		Map<String, String> map = new HashMap<>();
		for (String param : params)
		{
			String[] queryParams = param.split("=");
			if(queryParams.length==0) continue;
			if(queryParams.length==2){
				String name = param.split("=")[0];
				String value = param.split("=")[1];
				map.put(name, value);
			}
		}
		return map;
	}

	private void fetchUserName() {
		if(mProgress!=null)mProgress.setMessage("Finalizing ...");
		new Thread() {
			@Override
			public void run() {
				Log.i(TAG, "Fetching user info");
				int what = 0;

				try {
					URL url = new URL(API_URL + "/user?access_token="
							+ mAccessToken);

					Log.d(TAG, "Opening URL " + url.toString());
					HttpURLConnection urlConnection = (HttpURLConnection) url
							.openConnection();
					urlConnection.setRequestMethod("GET");
					urlConnection.setDoInput(true);
					urlConnection.connect();
					String response = streamToString(urlConnection
							.getInputStream());

					System.out.println(response);
					JSONObject jsonObj = (JSONObject) new JSONTokener(response)
							.nextValue();
					String id = jsonObj.getString("id");
					String login = jsonObj.getString("login");
					Log.i(TAG, "Got user name: " + login);
					mSession.storeAccessToken(mAccessToken, id, login);
				} catch (Exception ex) {
					what = 1;
					ex.printStackTrace();
				}

				mHandler.sendMessage(mHandler.obtainMessage(what, 2, 0));
			}
		}.start();
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.arg1 == 1) {
				if (msg.what == 0) {
					fetchUserName();
				} else {
					hideDialog();
					mListener.onFail("Failed to get access token");
				}
			} else {
				hideDialog();
				mListener.onSuccess();
			}
		}
	};

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
		return (mAccessToken == null) ? false : true;
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

	private String streamToString(InputStream is) throws IOException {
		String str = "";

		if (is != null) {
			StringBuilder sb = new StringBuilder();
			String line;

			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));

				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}

				reader.close();
			} finally {
				is.close();
			}

			str = sb.toString();
		}

		return str;
	}

	public void resetAccessToken() {
		if (mAccessToken != null) {
			mSession.resetAccessToken();
			mAccessToken = null;
		}
	}

	public void dismissDialog(){
		mDialog.dismissDialog();
		if(mProgress!=null && mProgress.isShowing()){
			mProgress.dismiss();
		}
		mProgress=null;
	}

	public interface OAuthAuthenticationListener {
		void onSuccess();
		void onFail(String error);
	}
}