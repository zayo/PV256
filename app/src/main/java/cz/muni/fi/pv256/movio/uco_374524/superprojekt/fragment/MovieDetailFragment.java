package cz.muni.fi.pv256.movio.uco_374524.superprojekt.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.R;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Actor;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.Log;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.view.SvgMaskedImageView;

/**
 * Created by prasniatko on 26/10/15.
 */
public class MovieDetailFragment extends Fragment {

    private static final String TAG = ".MovieDetailFragment";

    private TextView mTranslatedName;
    private TextView mYear;

    private View mContentView;
    private View mEmptyView;
    private LinearLayout mCastContainer;

    private LayoutInflater mLayoutInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        View root = mLayoutInflater.inflate(R.layout.fragment_detail, container);

        mContentView = root.findViewById(R.id.content_view);
        mEmptyView = root.findViewById(R.id.empty);
        mContentView.setVisibility(View.INVISIBLE);
        mEmptyView.setVisibility(View.VISIBLE);

        mTranslatedName = (TextView) root.findViewById(R.id.info_name);
        mYear = (TextView) root.findViewById(R.id.info_year);

        mCastContainer = (LinearLayout) root.findViewById(R.id.cast_container);

        return root;
    }

    public void setData(Movie movie) {
        Log.d(TAG, "setData() called with: " + "movie = [" + movie + "]");
        if (movie == null) {
            mContentView.setVisibility(View.INVISIBLE);
            mEmptyView.setVisibility(View.VISIBLE);
            return;
        }
        mTranslatedName.setText(movie.title);
        mYear.setText(String.valueOf(movie.releaseDate));

        mCastContainer.removeAllViews();
        for (Actor a : movie.actors) {
            View item = mLayoutInflater.inflate(R.layout.view_cast_item, mCastContainer, false);
            TextView name = (TextView) item.findViewById(R.id.actor_name);
            SvgMaskedImageView icon = (SvgMaskedImageView) item.findViewById(R.id.actor_image);
            name.setText(a.mName);
            icon.setImageResource(R.drawable.test);
            mCastContainer.addView(item);
        }

        mEmptyView.setVisibility(View.INVISIBLE);
        mContentView.setVisibility(View.VISIBLE);
    }

    public boolean isFragmentUIActive() {
        return isAdded() && !isDetached() && !isRemoving();
    }
}
