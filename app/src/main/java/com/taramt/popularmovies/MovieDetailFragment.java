package com.taramt.popularmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    public final String SELECTED_MOVIE = "com.taramt.popularmovies.SELECTED_MOVIE";

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Log.v("Movie Detail", "Movie detail fragment inflated");

        Intent intent = getActivity().getIntent();
        PopularMovie movie = (PopularMovie)intent.getSerializableExtra(SELECTED_MOVIE);
        Log.v("Movie Detail", "Movie Details: " + movie);

        TextView movieTitle = (TextView)rootView.findViewById(R.id.movieTitle);
        movieTitle.setText(movie.getTitle());

        TextView moviePlot = (TextView)rootView.findViewById(R.id.moviePlot);
        moviePlot.setText(movie.getPlot());

        TextView userRatings = (TextView)rootView.findViewById(R.id.userRatings);
        userRatings.setText(movie.getVoteAverage() + "/10");

        TextView releaseDate = (TextView)rootView.findViewById(R.id.releaseDate);
        releaseDate.setText(movie.getReleaseDate());

        ImageView imageView = (ImageView)rootView.findViewById(R.id.moviePic);
        String baseURL = "http://image.tmdb.org/t/p/w185";
        Picasso.with(getActivity()).load(baseURL + movie.getPoster()).into(imageView);

        return rootView;
    }
}