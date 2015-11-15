package cz.muni.fi.pv256.movio.uco_374524.superprojekt.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.R;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.NetworkStateChangedReceiver;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.fragment.MovieDetailFragment;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.fragment.MoviesListFragment;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.Log;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.RecyclerItemClickListener;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.viewmodel.IMovie;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.viewmodel.MoviesProvider;
import eu.inloop.viewmodel.base.ViewModelBaseActivity;

public class MovieListActivity extends ViewModelBaseActivity<IMovie, MoviesProvider>
  implements IMovie, RecyclerItemClickListener.OnItemClickListener,
  SwipeRefreshLayout.OnRefreshListener, NetworkStateChangedReceiver.NetworkStateChanged {

  private static final String TAG = ".MovieListActivity";

  private MoviesListFragment mListFragment;
  private MovieDetailFragment mDetailFragment;

  private NetworkStateChangedReceiver mNetworkStateChangedReceiver =
    new NetworkStateChangedReceiver(this);
  private boolean mConnected = false;

  private ArrayList<Movie> mData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setModelView(this);

    mListFragment =
      (MoviesListFragment) getFragmentManager().findFragmentById(R.id.list_frag);
    mDetailFragment =
      (MovieDetailFragment) getFragmentManager().findFragmentById(R.id.details_frag);

    if (mListFragment != null) {
      mListFragment.showLoading();
      mListFragment.addListOnClickListener(this, this);
      mListFragment.setOnRefreshListener(this);
    }

    mConnected = NetworkStateChangedReceiver.isNetworkAvailable(this);

    getViewModel().requestData(false);
    if (!mConnected) {
      Toast.makeText(this, "No internet connection, data could be inaccurate.", Toast.LENGTH_LONG)
        .show();
    }
  }

  @Override
  public Class<MoviesProvider> getViewModelClass() {
    return MoviesProvider.class;
  }

  @Override
  public void setData(ArrayList<Movie> data) {
    Log.d(TAG,
      "setData() called with: " + "data = [" + data + "]");
    if (mListFragment != null) {
      mData = data;
      mListFragment.setData(mData);
    }
  }

  @Override
  public void onRefresh() {
    if (mConnected) {
      //noinspection ConstantConditions
      getViewModel().requestData(true);
    } else {
      Toast.makeText(this, "No internet connection, data could be inaccurate.", Toast.LENGTH_LONG)
        .show();
    }
  }

  @Override
  public void onItemClick(View view, int position) {
    Log.d(TAG, "onItemClick() called with: " + "view = [" + view + "], position = ["
      + position + "]");
    if (mDetailFragment == null || !mDetailFragment.isFragmentUIActive()) {
      Intent intent =
        new Intent(MovieListActivity.this, MovieDetailActivity.class);
      intent.putExtra("movie", mData.get(position));
      startActivity(intent);
    } else {
      mDetailFragment.setData(mData.get(position));
    }
  }

  @Override
  public void onItemLongClick(View view, int position) {
    Log.d(TAG,
      "onItemLongClick() called with: " + "view = [" + view + "], position = ["
        + position + "]");
    Toast.makeText(MovieListActivity.this, mData.get(position).title, Toast.LENGTH_SHORT)
      .show();
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "onResume() called with: " + "");
    registerReceiver(mNetworkStateChangedReceiver,
      new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "onPause() called with: " + "");
    unregisterReceiver(mNetworkStateChangedReceiver);
    getViewModel().cancelLoading();
  }

  @Override
  public void isConnected(boolean isConnected) {
    mConnected = isConnected;
  }
}
