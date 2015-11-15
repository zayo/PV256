package cz.muni.fi.pv256.movio.uco_374524.superprojekt.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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

  private ImageView mTitleImage;
  private ImageView mTitleBackgroundImage;

  private TextView mTitleTranslated;
  private TextView mTitleOriginal;
  private TextView mYear;
  private TextView mOverView;

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

    mTitleImage = (ImageView) root.findViewById(R.id.movie_image);
    mTitleBackgroundImage = (ImageView) root.findViewById(R.id.movie_background_image);

    mTitleTranslated = (TextView) root.findViewById(R.id.movie_title);
    mTitleOriginal = (TextView) root.findViewById(R.id.movie_title_original);
    mYear = (TextView) root.findViewById(R.id.movie_year);
    mOverView = (TextView) root.findViewById(R.id.movie_overview);

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

    Picasso.with(mTitleBackgroundImage.getContext())
      .load("http://image.tmdb.org/t/p/w1280" + movie.backdropPath)
      .error(R.drawable.no_poster)
      .placeholder(R.drawable.no_poster)
      .into(mTitleBackgroundImage);

    Picasso.with(mTitleImage.getContext())
      .load("http://image.tmdb.org/t/p/w780" + movie.coverPath)
      .error(R.drawable.no_poster)
      .placeholder(R.drawable.no_poster)
      .into(mTitleImage);

    mTitleTranslated.setText(movie.title);
    mTitleOriginal.setText(movie.originalTitle);
    mOverView.setText(movie.overview);
    mYear.setText(movie.releaseDate.substring(0, 4));

    mCastContainer.removeAllViews();
    ArrayList<Actor> actors = movie.actors;
    for (int i = 0, actorsSize = actors.size(); i < actorsSize; i++) {
      Actor a = actors.get(i);
      View item = mLayoutInflater.inflate(R.layout.view_cast_item, mCastContainer, false);
      TextView name = (TextView) item.findViewById(R.id.actor_name);
      SvgMaskedImageView icon = (SvgMaskedImageView) item.findViewById(R.id.actor_image);
      name.setText(a.mName);
      icon.setImageResource(R.drawable.no_poster);
      mCastContainer.addView(item);
    }

    mEmptyView.setVisibility(View.INVISIBLE);
    mContentView.setVisibility(View.VISIBLE);
  }

  public boolean isFragmentUIActive() {
    return isAdded() && !isDetached() && !isRemoving();
  }
}
