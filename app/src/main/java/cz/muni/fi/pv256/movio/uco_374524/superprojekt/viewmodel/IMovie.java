package cz.muni.fi.pv256.movio.uco_374524.superprojekt.viewmodel;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;
import eu.inloop.viewmodel.IView;

/**
 * Created by prasniatko on 08/11/15.
 */
public interface IMovie extends IView {
  void setData(ArrayList<Movie> data);
}
