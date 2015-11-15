package cz.muni.fi.pv256.movio.uco_374524.superprojekt.viewmodel;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.DataProvider;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;
import eu.inloop.viewmodel.AbstractViewModel;

/**
 * Created by prasniatko on 08/11/15.
 */
public class MoviesProvider extends AbstractViewModel<IMovie> {

  private static final String TAG = ".MoviesProvider";

  DataProvider.DataLoaded listener = new DataProvider.DataLoaded() {
    @Override
    public void onDataLoaded(ArrayList<Movie> data) {
      if (getView() != null) {
        getView().setData(data);
      }
    }
  };

  public void requestData(boolean force_reload) {
    DataProvider.get().loadData(force_reload, listener);
  }

  public void cancelLoading() {
    DataProvider.get().cancelLoading();
  }
}
