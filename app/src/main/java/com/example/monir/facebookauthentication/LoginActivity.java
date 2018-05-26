package com.example.monir.facebookauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    CallbackManager   callbackManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = (Button) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();

        // Callback registration
      /*  loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                if (isLoggedIn){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });*/

      loginButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("user_friends", "email", "user_birthday", "user_gender", "public_profile"));
              LoginManager.getInstance().registerCallback(callbackManager,
                      new FacebookCallback<LoginResult>() {
                          @Override
                          public void onSuccess(LoginResult loginResult) {
                              // App code
                            //  setFacebookData(loginResult);
                              Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                              intent.putExtra("AccessToken", loginResult.getAccessToken());
                              startActivity(intent);
                          }

                          @Override
                          public void onCancel() {
                              // App code
                          }

                          @Override
                          public void onError(FacebookException exception) {
                              // App code
                          }
                      });
          }
      });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
