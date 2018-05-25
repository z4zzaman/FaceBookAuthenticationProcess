package com.example.monir.facebookauthentication;

/*
 *  ****************************************************************************
 *  * Created by : Md. Moniruzzaman Monir on 5/25/2018 at 8:53 PM.
 *  * Email : zzaman08@gmail.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md. Moniruzzaman Monir on 5/25/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class FaceBookAuthenticationApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
