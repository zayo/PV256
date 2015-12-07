package cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection;

import java.util.HashMap;
import java.util.Map;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.DateUtils;

/**
 * Created by prasniatko on 15/11/15.
 */
public class DiscoverHelper {

  public static final String API = "/discover/movie?";

  private String url;

  private DiscoverHelper(String url, Builder builder) {
    this.url = (url + API).replaceAll("//", "/");
    String separator = "";
    for (Map.Entry<String, String> entry : builder.params.entrySet()) {
      this.url += separator;
      this.url += entry.getKey() + "=" + entry.getValue();
      separator = "&";
    }
  }

  public static class Builder {

    private String url;
    private HashMap<String, String> params = new HashMap<>();

    public Builder(String base, String api_key) {
      if (base == null) {
        throw new NullPointerException("Base url == null");
      }
      if (api_key == null) {
        throw new NullPointerException("Api key == null");
      }
      url = base;
      params.put("api_key", api_key);
      params.put("sort_by", "popularity.desc");
    }

    public Builder setDateFrom(String dateFrom) {
      params.put("primary_release_date.gte", dateFrom);
      return this;
    }

    public Builder setDateFrom(long milis) {
      params.put("primary_release_date.gte", DateUtils.format(DateUtils.DEFAULT_DAY, milis));
      return this;
    }

    public Builder setDateTo(String dateTo) {
      params.put("primary_release_date.lte", dateTo);
      return this;
    }

    public Builder setDateTo(long milis) {
      params.put("primary_release_date.lte", DateUtils.format(DateUtils.DEFAULT_DAY, milis));
      return this;
    }

    public Builder setDateTo(long milis, int days_after) {
      params.put("primary_release_date.lte", DateUtils.format(DateUtils.DEFAULT_DAY,
        milis + DateUtils.getMilis(DateUtils.Type.DAY, days_after)));
      return this;
    }

    public Builder withGenres(boolean exclusive, int... genres) {
      String array = "";
      String separator = "";
      for (int i = 0, genresLength = genres.length; i < genresLength; i++) {
        int genre = genres[i];
        array += separator;
        array += genre;
        separator = exclusive ? "," : "|";
      }
      params.put("with_genres", array);
      return this;
    }

    public String build() {
      return new DiscoverHelper(url, this).url;
    }
  }
}
