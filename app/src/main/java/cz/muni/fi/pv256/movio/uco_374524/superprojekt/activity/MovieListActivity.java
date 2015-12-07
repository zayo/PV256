package cz.muni.fi.pv256.movio.uco_374524.superprojekt.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.R;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.adapter.GenreFilterAdapter;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.DataProvider;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.NetworkStateChangedReceiver;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.fragment.MovieDetailFragment;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.fragment.MoviesListFragment;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Genre;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.GenresWrapper;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.Log;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.RecyclerItemClickListener;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.viewmodel.IMovie;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.viewmodel.MoviesProvider;
import eu.inloop.viewmodel.base.ViewModelBaseActivity;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MovieListActivity extends ViewModelBaseActivity<IMovie, MoviesProvider>
  implements IMovie, RecyclerItemClickListener.OnItemClickListener,
  SwipeRefreshLayout.OnRefreshListener, NetworkStateChangedReceiver.NetworkStateChanged,
  GenreFilterAdapter.OnSelectionChanged {

  private static final String TAG = ".MovieListActivity";

  private MoviesListFragment mListFragment;
  private MovieDetailFragment mDetailFragment;

  private NetworkStateChangedReceiver mNetworkStateChangedReceiver =
    new NetworkStateChangedReceiver(this);
  private boolean mConnected = false;

  private GenreFilterAdapter mGenresAdapter;
  private CheckBox mGenresCheckboxAll;

  private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener =
    new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mGenresAdapter != null) {
          if (isChecked) {
            mGenresAdapter.selectAll();
          } else {
            mGenresAdapter.deselectAll();
          }
        }
      }
    };

  private ArrayList<Movie> mData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle actionBarDrawerToggle =
      new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
        R.string.drawer_close) {

        private String prevSelection = "";

        @Override
        public void onDrawerClosed(View drawerView) {
          // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
          super.onDrawerClosed(drawerView);
          //Test if selection has changed before load.
          if (!prevSelection.equalsIgnoreCase(mGenresAdapter.getCheckedIds())) {
            if (mListFragment != null) {
              mListFragment.showLoading();
            }
            getViewModel().requestData(mGenresAdapter.getCheckedIds());
          }
        }

        @Override
        public void onDrawerOpened(View drawerView) {
          super.onDrawerOpened(drawerView);
          prevSelection = mGenresAdapter.getCheckedIds();
        }
      };

    mGenresCheckboxAll = (CheckBox) findViewById(R.id.checkbox_all);
    mGenresCheckboxAll.setOnCheckedChangeListener(mOnCheckedChangeListener);

    final ListView lv = (ListView) findViewById(R.id.listview);
    mGenresAdapter = new GenreFilterAdapter(this, new ArrayList<Genre>(), this);
    lv.setAdapter(mGenresAdapter);

    DataProvider.get().loadGenres(new Callback<GenresWrapper>() {
      @Override
      public void onResponse(Response<GenresWrapper> response, Retrofit retrofit) {
        Log.d(TAG, "genres = [" + response.body() + "]");
        mGenresAdapter.clear();
        mGenresAdapter.addAll(response.body().genres);
        mGenresAdapter.notifyDataSetChanged();
      }

      @Override
      public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure ", t);
      }
    });

    //Setting the actionbarToggle to drawer layout
    drawerLayout.setDrawerListener(actionBarDrawerToggle);

    //calling sync state is necessay or else your hamburger icon wont show up
    actionBarDrawerToggle.syncState();

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

    getViewModel().requestData(mGenresAdapter.getCheckedIds());
    if (!mConnected) {
      Toast.makeText(this, "No internet connection, data could be inaccurate.", Toast.LENGTH_LONG)
        .show();
    }
  }

  @Override
  public void allSelected(boolean isAllSelected) {
    mGenresCheckboxAll.setOnCheckedChangeListener(null);
    mGenresCheckboxAll.setChecked(isAllSelected);
    mGenresCheckboxAll.setOnCheckedChangeListener(mOnCheckedChangeListener);
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
      getViewModel().requestData(mGenresAdapter.getCheckedIds());
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
