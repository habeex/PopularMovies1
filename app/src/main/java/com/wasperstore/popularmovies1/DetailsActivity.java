package com.wasperstore.popularmovies1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wasperstore.popularmovies1.utilities.NetworkUtils;

public class DetailsActivity extends AppCompatActivity {
    ImageView imageView;
    TextView title, textView, release_date, vote_count, vote_average,  vote_averageText;
    RatingBar ratingBar;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("this");
        Intent intent = this.getIntent();

        title = (TextView) findViewById(R.id.title);
        imageView = (ImageView) findViewById(R.id.image);
        textView = (TextView) findViewById(R.id.overview);
        release_date = (TextView) findViewById(R.id.release_date);
        vote_count = (TextView) findViewById(R.id.vote_count);
        vote_average = (TextView) findViewById(R.id.vote_average);
        vote_averageText = (TextView) findViewById(R.id.average_vote);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);


        // Set Collapsing Toolbar tour_layout to the screen
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        // Set title of Detail page
        collapsingToolbar.setTitle(" ");


        String url = NetworkUtils.buildMovieUrl(intent.getStringExtra("poster_path")).toString();
        Picasso.with(context).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);

        title.setText(intent.getStringExtra("title"));
        textView.setText(intent.getStringExtra("overview"));
        release_date.setText(intent.getStringExtra("release_date"));

        vote_count.setText("(" + intent.getStringExtra("vote_count") + ")");
        vote_averageText.setText(intent.getStringExtra("vote_average") + "/10");

        double average = Double.parseDouble(intent.getStringExtra("vote_average"));
        float mRating_score = (float)(average/10)*5;
        vote_average.setText(String.format("%.1f",mRating_score));
        ratingBar.setRating(mRating_score);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
