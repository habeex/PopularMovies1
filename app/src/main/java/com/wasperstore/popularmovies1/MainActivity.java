package com.wasperstore.popularmovies1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.wasperstore.popularmovies1.adapter.MoviesAdapter;
import com.wasperstore.popularmovies1.model.Movies;
import com.wasperstore.popularmovies1.utilities.NetworkUtils;
import com.wasperstore.popularmovies1.utilities.OpenMoviesJsonUtils;

import org.json.JSONException;

import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MoviesAdapter moviesAdapter;


    private TextView mErrorMessageDisplay;
    FrameLayout frameLayout;

    private ProgressBar mLoadingIndicator;
    private String DEFAULT_SORT = "movie/popular";
    private String SORT_POPULAR = "movie/popular";
    private String SORT_RATED = "movie/top_rated";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        frameLayout = (FrameLayout) findViewById(R.id.activity_main);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        LinearLayoutManager layoutManager
                = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        moviesAdapter = new MoviesAdapter(this);

        mRecyclerView.setAdapter(moviesAdapter);

        UpdateUI();

    }

    public void UpdateUI (){
        showWeatherDataView();
        final String SORT = "movie/popular";
        new FetchMovieTask().execute(DEFAULT_SORT);

    }


    private void showWeatherDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);

        Snackbar snackbar = Snackbar.make(frameLayout, "No Internet Connect, Please" +
                " Turn ON data and click RETRY",Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RETRY", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUI();
            }
        });
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.Popular) {
            DEFAULT_SORT = SORT_POPULAR;
            moviesAdapter.setMoviesData(null);
            UpdateUI();

        } else if (itemThatWasClickedId == R.id.Rated) {
            DEFAULT_SORT = SORT_RATED;
            moviesAdapter.setMoviesData(null);
            UpdateUI();
        }
        return super.onOptionsItemSelected(item);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {


            if (strings.length == 0) {
                return null;
            }

            String movies = strings[0];
            URL moviesRequestUrl = NetworkUtils.buildUrl(movies);

            try {
                String jsonMoviesResponse = NetworkUtils
                        .getResponseFromHttpUrl(moviesRequestUrl);

//                String[] simpleJsonMovieData = OpenMoviesJsonUtils
//                        .getSimpleMoviesStringsFromJson(MainActivity.this, jsonMoviesResponse);

//                return simpleJsonMovieData;
                return jsonMoviesResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(String moviesString) {

            mLoadingIndicator.setVisibility(View.GONE);
            if (moviesString != null) {

                Movies[] movies = null;
                try {
                    movies = OpenMoviesJsonUtils
                            .getSimpleMoviesObjectFromString(moviesString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                moviesAdapter.setMoviesData(movies);

            } else {
                showErrorMessage();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
    }
}
