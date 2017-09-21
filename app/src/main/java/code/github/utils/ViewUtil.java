package code.github.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by shank on 4/29/17.
 */

public class ViewUtil {

    public static void setVisible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public static void setGone(View view) {
        view.setVisibility(View.GONE);
    }

    public static void showProgressDialog(ProgressDialog progressDialog){
        if(progressDialog!=null) {
            progressDialog.setMessage("Please wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }


    public static void hideDialog(ProgressDialog progressDialog){
        if(progressDialog!=null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
