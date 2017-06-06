/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wasperstore.popularmovies1.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {


    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_API_URL =
            "https://api.themoviedb.org/3/";
    private static final String API_KEY =
            "?api_key=d907161b6c5e62b72221f75c41aa4e99";
    private static final String DEFAULT_SORT_TYPE =
            "movie/popular";

    private static final String BASE_IMAGE_URL =
            "http://image.tmdb.org/t/p/";
    private static final String DEFAULT_IMAGE_SIZE =
            "w185/";


    public static URL buildUrl(String sort) {
        Uri builtUri = Uri.parse(BASE_API_URL + sort + API_KEY).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildMovieUrl(String path) {
        URL url = null;
        String movieAds = BASE_IMAGE_URL + DEFAULT_IMAGE_SIZE + path;
        try {
            url = new URL(movieAds);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built image URL " + url);
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}