package com.theironyard.moviedirectory.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.theironyard.moviedirectory.Model.Movie;
import com.theironyard.moviedirectory.R;
import com.theironyard.moviedirectory.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.squareup.picasso.Request.*;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie movie;
    private TextView movieTitle;
    private ImageView movieImage;
    private TextView movieYear;
    private TextView director;
    private TextView actors;
    private TextView category;
    private TextView rating;
    private TextView writers;
    private TextView plot;
    private TextView boxOffice;
    private TextView runTime;

    private RequestQueue queue;
    private String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        queue = Volley.newRequestQueue(this);

        movie = (Movie) getIntent().getSerializableExtra("movie");
        movieId = movie.getImdbId();

        setUpUI();
        getMovieDetails(movieId);

    }

    private void getMovieDetails(String id) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET,
                Constants.URL + id+Constants.API_KEY, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if (response.has("Ratings")) {
                        JSONArray ratings = response.getJSONArray("Ratings");

                        String source = null;
                        String value = null;
                        if (ratings.length() > 0) {

                            JSONObject mRatings = ratings.getJSONObject(ratings.length() - 1);
                            source = mRatings.getString("Source");
                            value = mRatings.getString("Value");
                            rating.setText(source + " : " + value);

                        }else {
                            rating.setText("Ratings: N/A");
                        }

                        movieTitle.setText(response.getString("Title"));
                        movieYear.setText("Released: " + response.getString("Released"));
                        director.setText("Director: " + response.getString("Director"));
                        writers.setText("Writers: " + response.getString("Writer"));
                        plot.setText("Plot: " + response.getString("Plot"));
                        runTime.setText("Runtime: " + response.getString("Runtime"));
                        actors.setText("Actors: " + response.getString("Actors"));

                        Picasso.with(getApplicationContext())
                                .load(response.getString("Poster"))
                                .into(movieImage);

                        boxOffice.setText("Box Office: " + response.getString("BoxOffice"));

                    }



                }catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error:", error.getMessage());

            }
        });
        queue.add(jsonObjectRequest);

    }

    private void setUpUI() {

        movieTitle = (TextView) findViewById(R.id.movieTitleIDDets);
        movieImage = (ImageView) findViewById(R.id.movieImageIDDets);
        movieYear = (TextView) findViewById(R.id.movieReleaseIDDets);
        director = (TextView) findViewById(R.id.directedByDet);
        category = (TextView) findViewById(R.id.movieCatIDDets);
        rating = (TextView) findViewById(R.id.movieRatingIDDets);
        writers = (TextView) findViewById(R.id.writersDet);
        plot = (TextView) findViewById(R.id.plotDet);
        boxOffice = (TextView) findViewById(R.id.boxOfficeDet);
        runTime = (TextView) findViewById(R.id.runtimeDet);
        actors = (TextView) findViewById(R.id.actorsDet);

    }

}
