package com.example.monir.facebookauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        AccessToken accessToken = intent.getParcelableExtra("AccessToken");
        setFacebookData(accessToken);
    }

    private void setFacebookData(final  AccessToken accessToken)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
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
}
