package com.example.jol.musicapplication.Service;

import android.content.Context;
import android.util.Log;

import com.example.jol.musicapplication.Models.LastFMArtistsResposne;
import com.example.jol.musicapplication.R;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.protocol.HTTP;

public class LastFMService {

    public void searchArtists(Context context, String searchText, final ServiceResponse serviceResponse) throws IOException {
        RequestParams params = new RequestParams();
        params.put("method", "artist.gettopalbums");
        params.put("api_key", context.getString(R.string.api_key));
        params.put("format", "json");
        params.put("artist", searchText);


        RestClient.get("",params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if(statusCode == 200) {
                        LastFMArtistsResposne artistsResposne = new Gson().fromJson(response.get("topalbums").toString(), LastFMArtistsResposne.class);
                        serviceResponse.onResponseReceived(artistsResposne);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("response failure", String.valueOf(statusCode));
            }
        });
    }
}
