package com.taramt.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment {
    private final String SELECTED_MOVIE = "com.taramt.popularmovies.SELECTED_MOVIE";
    private final String LOG_TAG = MoviesFragment.class.getSimpleName();
    private GridView gridview = null;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.movies_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_popular:
                new FetchMoviesTask().execute(getString(R.string.popular));
                return true;
            case R.id.action_ratings:
                new FetchMoviesTask().execute(getString(R.string.ratings));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        gridview = (GridView) rootView.findViewById(R.id.gridview);
        new FetchMoviesTask().execute(getString(R.string.popular));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                PopularMovie movie = (PopularMovie)parent.getAdapter().getItem(position);
                Log.v(LOG_TAG, "Selected Movie" + movie);
                Intent intent = new Intent(getActivity(), MovieDetail.class);
                intent.putExtra(SELECTED_MOVIE, movie);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<PopularMovie>> {
        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
        private final String apiKey = getString(R.string.api_key);

        @Override
        protected ArrayList<PopularMovie> doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("api.themoviedb.org")
                        .appendPath("3")
                        .appendPath("discover")
                        .appendPath("movie")
                        .appendQueryParameter("api_key", apiKey)
                        .appendQueryParameter("sort_by", params[0]);

                String urlStr = builder.build().toString();
                URL url = new URL(urlStr);
                Log.v(LOG_TAG, "URL:" + urlStr);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                moviesJsonStr = buffer.toString();
                Log.v(LOG_TAG, "Movies Data: " + moviesJsonStr);
                return getMoviesDataFromJson(moviesJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error ", e);
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<PopularMovie> popularMovies) {
            super.onPostExecute(popularMovies);
            gridview.setAdapter(new ImageAdapter(getActivity(), popularMovies));
            Log.v(LOG_TAG, "Image Adapter Set");
        }

        private ArrayList<PopularMovie> getMoviesDataFromJson(String moviesJsonStr) throws JSONException {
            // These are the names of the JSON objects that need to be extracted.
            final String MDB_RESULTS = "results";
            final String MDB_BACKDROP = "poster_path";
            final String MDB_TITLE = "title";
            final String MDB_PLOT = "overview";
            final String MDB_RELEASE_DT = "release_date";
            final String MDB_VOTE_AVG = "vote_average";

            JSONObject moviesObject = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesObject.getJSONArray(MDB_RESULTS);
            ArrayList<PopularMovie> list = new ArrayList<PopularMovie>();
            for(int i = 0; i < moviesArray.length(); i++) {
                PopularMovie pMovie = new PopularMovie();
                JSONObject movie = moviesArray.getJSONObject(i);
                pMovie.setPoster(movie.getString(MDB_BACKDROP));
                pMovie.setPlot(movie.getString(MDB_PLOT));
                pMovie.setReleaseDate(movie.getString(MDB_RELEASE_DT));
                pMovie.setTitle(movie.getString(MDB_TITLE));
                pMovie.setVoteAverage(movie.getString(MDB_VOTE_AVG));
                list.add(pMovie);
            }
            return list;
        }
    }

}
