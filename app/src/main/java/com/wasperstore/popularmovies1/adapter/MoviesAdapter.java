package com.wasperstore.popularmovies1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wasperstore.popularmovies1.DetailsActivity;
import com.wasperstore.popularmovies1.R;
import com.wasperstore.popularmovies1.model.Movies;
import com.wasperstore.popularmovies1.utilities.NetworkUtils;


/**
 * Created by Habeex on 5/25/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private Context mContext;
    private Movies[] moviesData;

    public MoviesAdapter(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movies_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {
//        Movies weatherForThisDay = moviesData[position];
//        holder.textView.setText(weatherForThisDay);
        Movies movie = moviesData[position];
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        if (null == moviesData)
            return 0;
        return moviesData.length;
    }

    public void setMoviesData(Movies[] mdata) {
        moviesData = mdata;
        notifyDataSetChanged();
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder {



        public final TextView title;
        public final TextView num_of_votes;
        public final TextView rating_score;
        public final ImageView poster_path;
        public final RatingBar ratingBar;


        public MoviesAdapterViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            rating_score = (TextView) view.findViewById(R.id.rating_score);
            num_of_votes = (TextView) view.findViewById(R.id.num_of_votes);
            poster_path = (ImageView) view.findViewById(R.id.poster_path);
            ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Movies movies = moviesData[pos];
                        Intent intent = new Intent(mContext, DetailsActivity.class);
                        intent.putExtra("poster_path",movies.poster_path);
                        intent.putExtra("title", movies.title);
                        intent.putExtra("overview", movies.overview);
                        intent.putExtra("release_date", movies.releaseDate);
                        intent.putExtra("vote_count", String.valueOf(movies.voteCount));
                        intent.putExtra("vote_average", String.valueOf(movies.voteAverage));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
        }

        void bind(Movies movie) {
            title.setText(movie.title);
            num_of_votes.setText("(" + movie.voteCount + ")");
            float mRating_score = (float)(movie.voteAverage/10)*5;
            rating_score.setText(String.format("%.1f",mRating_score));
            ratingBar.setRating(mRating_score);



            String url = NetworkUtils.buildMovieUrl(movie.poster_path).toString();
            Picasso.with(mContext).load(url)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(poster_path);
        }

    }
}
