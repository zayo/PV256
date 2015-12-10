package cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils;

import java.util.ArrayList;

/**
 * Created by prasniatko on 10/12/15.
 */
public class HeaderArrayList<T> extends ArrayList<T> {
  private int header_repeater = 7;

  public HeaderArrayList(){
    super();
  }

  public HeaderArrayList(int size){
    super(size);
  }

  public void setHeaderEvery(int count){
    header_repeater = count;
  }

  public int getHeaderEvery(){
    return header_repeater;
  }
}
