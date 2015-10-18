package cz.muni.fi.pv256.movio.uco_374524.superprojekt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.R;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;

/**
 * Created by prasniatko on 18/10/15.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    private static final String TAG = ".MovieAdapter";

    public static final int TYPES_COUNT = 2;
    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;
    public static final int TYPE_1_RES = R.layout.movie_type_1;
    public static final int TYPE_2_RES = R.layout.movie_type_2;

    private LayoutInflater mLayoutInflater;

    public MovieAdapter(Context context, int unused, List<Movie> objects) {
        super(context, unused, objects);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getViewTypeCount() {
        return TYPES_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).mGenre == Movie.Genre.ACTION ? TYPE_1 : TYPE_2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        int view_type = getItemViewType(position);
        int layoutResourceId;

        switch (view_type) {
            case TYPE_1:
                layoutResourceId = TYPE_1_RES;
                break;
            case TYPE_2:
                layoutResourceId = TYPE_2_RES;
                break;
            default:
                layoutResourceId = TYPE_1_RES;
        }

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(layoutResourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.movieName = (TextView) convertView.findViewById(R.id.movie_name);
            viewHolder.movieImage = (ImageView) convertView.findViewById(R.id.movie_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Movie objectItem = getItem(position);

        if (objectItem != null) {
            viewHolder.movieName.setText(objectItem.mTitle);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView movieImage;
        TextView movieName;
    }
}
