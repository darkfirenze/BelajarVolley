package tes.volley.volleynet;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Kucing Imut on 8/24/15.
 */
public class VolleyAppController extends Application {


    public static final String TAG = VolleyAppController.class
            .getSimpleName();

    private RequestQueue mReqQueue;

    private static VolleyAppController volleyAppController;


    @Override
    public void onCreate() {
        super.onCreate();
        volleyAppController = this;
    }


    public static synchronized VolleyAppController getInstance() {
        return volleyAppController;
    }


    public RequestQueue getmReqQueue() {

        if (mReqQueue == null) {
            mReqQueue = Volley.newRequestQueue(getApplicationContext(), new OkhttpStack());
        }

        return mReqQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getmReqQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getmReqQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mReqQueue != null) {
            mReqQueue.cancelAll(tag);
        }
    }



}
