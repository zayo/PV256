package cz.muni.fi.pv256.movio.uco_374524.superprojekt.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.R;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.fragment.MovieDetailFragment;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;

/**
 * Created by prasniatko on 27/10/15.
 */
public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final MovieDetailFragment mdf =
            (MovieDetailFragment) getFragmentManager().findFragmentById(R.id.details_frag);

        mdf.setData((Movie) getIntent().getExtras().getParcelable("movie"));
    }
}
