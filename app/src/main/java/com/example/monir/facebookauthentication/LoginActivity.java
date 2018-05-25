package com.example.monir.facebookauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

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
                              setFacebookData(loginResult);
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

    private void setFacebookData(final LoginResult loginResult)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        // Here also possible to get data from object
                        try {
                            Log.i("Response",response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String gender = response.getJSONObject().getString("gender");
                            String birthday = response.getJSONObject().getString("birthday");



                            Profile profile = Profile.getCurrentProfile();
                            String id = profile.getId();
                            String link = profile.getLinkUri().toString();
                            Log.i("Link",link);
                            if (Profile.getCurrentProfile()!=null)
                            {
                                Log.i("Login", "ProfilePic:: " + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                            }

                            Log.i("Login" + " Email", email);
                            Log.i("Login"+ " FirstName", firstName);
                            Log.i("Login" + " LastName", lastName);
                            Log.i("Login" + " Gender", gender);
                            Log.i("Login" + " Birthday", birthday);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,gender,birthday, first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();

    /*    replace parameters.putString("fields", "id,email,first_name,last_name");
        with parameters.putString("fields", "id,email,first_name,last_name,friends");
        Add below logic to get friends Data*/

/*        if (object.has("friends")) {
            JSONObject friend = object.getJSONObject("friends");
            JSONArray data = friend.getJSONArray("data");
            for (int i=0;i<data.length();i++){
                Log.i("idddd",data.getJSONObject(i).getString("id"));
            }
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
