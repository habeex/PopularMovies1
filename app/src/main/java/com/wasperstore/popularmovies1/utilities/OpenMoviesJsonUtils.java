package com.wasperstore.popularmovies1.utilities;

import android.content.Context;


import com.wasperstore.popularmovies1.model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by Habeex on 5/25/2017.
 */

public final class OpenMoviesJsonUtils {

    private static final String MOVIES_LIST = "results";


    // FIXME: Please remove everything that are not needed here
    public static String[] getSimpleMoviesStringsFromJson(Context context, String moviesJsonStr)
            throws JSONException {


        final String OWM_MESSAGE_CODE = "cod";


        /* String array to hold each day's weather String */
        String[] parsedMoviesData = null;

        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        /* Is there an error? */
        if (moviesJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = moviesJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray moviesArray = moviesJson.getJSONArray(MOVIES_LIST);

        parsedMoviesData = new String[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {
            String title;
            String overview;
            String releaseDate;


            JSONObject resultsItems = moviesArray.getJSONObject(i);

            /*
             * We ignore all the datetime values embedded in the JSON and assume that
             * the values are returned in-order by day (which is not guaranteed to be correct).
             */

            title = resultsItems.getString("title");
            overview = resultsItems.getString("overview");
            releaseDate = resultsItems.getString("release_date");

            /*
             * Temperatures are sent by Open Weather Map in a child object called "temp".
             *
             * Editor's Note: Try not to name variables "temp" when working with temperature.
             * It confuses everybody. Temp could easily mean any number of things, including
             * temperature, temporary and is just a bad variable name.
             */


            parsedMoviesData[i] = title + "\n\n" + overview + "\n\n" + releaseDate;
        }

        return parsedMoviesData;
    }

    public static Movies[] getSimpleMoviesObjectFromString(String moviesJsonStr)
            throws JSONException {

        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        // TODO: I dont know how their errors are formated Thats why I'm not handling error cases
        JSONArray moviesList = moviesJson.getJSONArray(MOVIES_LIST);

        Movies[] movies = new Movies[moviesList.length()];

        for (int i = 0; i < moviesList.length(); i++) {
            movies[i] = new Movies(moviesList.getJSONObject(i));
        }

        return movies;
    }


}

