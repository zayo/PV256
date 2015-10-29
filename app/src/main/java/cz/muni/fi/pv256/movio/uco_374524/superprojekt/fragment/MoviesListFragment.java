package cz.muni.fi.pv256.movio.uco_374524.superprojekt.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.tonicartos.superslim.LayoutManager;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.R;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.adapter.MovieGridAdapter;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.ItemDecoration;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.RecyclerItemClickListener;

/**
 * Created by prasniatko on 26/10/15.
 */
public class MoviesListFragment extends Fragment {

    private static final String TAG = ".MoviesListFragment";

    public static final int COLUMN_COUNT = 3;

    protected RecyclerView mRecyclerView;
    protected ViewStub mEmptyView;
    protected ViewStub mNoConnView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.movies_grid_view);
        mEmptyView = (ViewStub) root.findViewById(R.id.empty);
        mNoConnView = (ViewStub) root.findViewById(R.id.no_connection);

        //mGridLayoutManager = new GridLayoutManager(getActivity(), COLUMN_COUNT);
        mRecyclerView.setLayoutManager(new LayoutManager(getActivity()));

        return root;
    }

    public void setData(ArrayList<Movie> data) {

        setNoConnectionViewVisible(data == null);
        setEmptyViewVisible(data != null && data.isEmpty());
        setRecyclerViewVisible(data != null && !data.isEmpty());

        if (data != null) {
            DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
            mRecyclerView.addItemDecoration(
                new ItemDecoration(getResources().getDimensionPixelSize(R.dimen.grid_spacing),
                    COLUMN_COUNT));
            mRecyclerView.setAdapter(
                new MovieGridAdapter(data, dm.widthPixels / COLUMN_COUNT, COLUMN_COUNT));
        }
    }

    public void addListOnClickListener(Context ctx,
        RecyclerItemClickListener.OnItemClickListener listener) {
        if (mRecyclerView != null) {
            mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ctx, mRecyclerView, listener));
        }
    }

    public void removeOnClickListener(RecyclerItemClickListener listener) {
        if (mRecyclerView != null) {
            mRecyclerView.removeOnItemTouchListener(listener);
        }
    }

    private void setEmptyViewVisible(boolean isVisible) {
        mEmptyView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setNoConnectionViewVisible(boolean isVisible) {
        mNoConnView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setRecyclerViewVisible(boolean isVisible) {
        mRecyclerView.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }
}
