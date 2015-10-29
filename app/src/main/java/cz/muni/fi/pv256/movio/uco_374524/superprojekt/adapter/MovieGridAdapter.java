package cz.muni.fi.pv256.movio.uco_374524.superprojekt.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonicartos.superslim.GridSLM;
import com.tonicartos.superslim.LayoutManager;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.R;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;

/**
 * Created by prasniatko on 26/10/15.
 */
public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.Holder> {

    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_CONTENT = 1;

    private ArrayList<Movie> mItems;
    private int grid_column_width;
    private int grid_column_count;

    public MovieGridAdapter(ArrayList<Movie> data, int column_width, int column_count) {
        mItems = data;
        grid_column_width = column_width;
        grid_column_count = column_count;
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).isHeader ? VIEW_TYPE_HEADER : VIEW_TYPE_CONTENT;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        Holder holder;
        if (viewType == VIEW_TYPE_HEADER) {
            view = inflater.inflate(R.layout.view_movie_header, parent, false);
            holder = new HeaderVH(view);
        } else {
            view = inflater.inflate(R.layout.view_movie_item, parent, false);
            holder = new ContentVH(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Movie item = mItems.get(position);
        holder.bindItem(item);

        GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(holder.itemView.getLayoutParams());
        if (item.isHeader) {
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.isHeader = true;
            lp.headerDisplay = LayoutManager.LayoutParams.HEADER_INLINE;
        } else {
            lp.isHeader = false;
        }
        lp.setSlm(GridSLM.ID);
        lp.setColumnWidth(grid_column_width);
        lp.setFirstPosition(position - (position % 7));
        lp.setNumColumns(grid_column_count);
        holder.itemView.setLayoutParams(lp);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public abstract class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }

        public abstract void bindItem(Movie item);
    }

    public class ContentVH extends Holder {

        ImageView mImageView;

        public ContentVH(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.movie_image);
        }

        @Override
        public void bindItem(Movie item) {
            mImageView.setImageResource(R.drawable.test);
        }
    }

    public class HeaderVH extends Holder {

        TextView mTextView;

        public HeaderVH(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.section_name);
        }

        @Override
        public void bindItem(Movie item) {
            mTextView.setText(item.title);
        }
    }
}
