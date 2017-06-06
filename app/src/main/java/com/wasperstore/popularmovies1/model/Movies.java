package com.wasperstore.popularmovies1.model;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Habeex on 5/25/2017.
 */

public class Movies {
    private static final String TITLE_KEY = "original_title";
    private static final String OVERVIEW_KEY = "overview";
    private static final String RELEASE_DATE_KEY = "release_date";
    private static final String POSTER_PATH = "poster_path";
    private static final String VOTE_COUNT = "vote_count";
    private static final String VOTE_AVERAGE = "vote_average";

    public String overview;
    public String releaseDate;
    public String title;
    public String poster_path;
    public long voteCount;
    public double voteAverage;


    public Movies(JSONObject object) {
        try {
            title = object.getString(TITLE_KEY);
            releaseDate = object.getString(RELEASE_DATE_KEY);
            overview = object.getString(OVERVIEW_KEY);
            poster_path = object.getString(POSTER_PATH);
            voteCount = object.getLong(VOTE_COUNT);
            voteAverage = object.getDouble(VOTE_AVERAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // FIXME: Please consider this redundant, because all the fields are public
    /* public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }



    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    } */

}
