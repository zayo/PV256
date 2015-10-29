package cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tonicartos.superslim.GridSLM;

/**
 * Created by prasniatko on 27/10/15.
 */
public class ItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = ".ItemDecoration";

    private int mSizeGridSpacingPx;
    private int mGridSize;

    private boolean mNeedLeftSpacing = false;

    public ItemDecoration(int gridSpacingPx, int gridSize) {
        mSizeGridSpacingPx = gridSpacingPx;
        mGridSize = gridSize;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
        RecyclerView.State state) {

        GridSLM.LayoutParams params = (GridSLM.LayoutParams) view.getLayoutParams();

        //index of first group item
        int start_index = params.getFirstPosition() + 1;

        if (params.isHeader) { // header layout
            /* if (start_index != 1) {
                outRect.top = mSizeGridSpacingPx;
            } */
            return;
        }

        int frameWidth =
            (int) ((parent.getWidth() - (float) mSizeGridSpacingPx * (mGridSize - 1)) / mGridSize);
        int padding = parent.getWidth() / mGridSize - frameWidth;
        int itemPosition = params.getViewAdapterPosition() - start_index; // normalize index
        if (itemPosition < mGridSize) {
            outRect.top = 0;
        } else {
            outRect.top = mSizeGridSpacingPx;
        }

        if (itemPosition % mGridSize == 0) {
            outRect.left = 0;
            outRect.right = padding;
            mNeedLeftSpacing = true;
        } else if ((itemPosition + 1) % mGridSize == 0) {
            mNeedLeftSpacing = false;
            outRect.right = 0;
            outRect.left = padding;
        } else if (mNeedLeftSpacing) {
            mNeedLeftSpacing = false;
            outRect.left = mSizeGridSpacingPx - padding;
            if ((itemPosition + 2) % mGridSize == 0) {
                outRect.right = mSizeGridSpacingPx - padding;
            } else {
                outRect.right = mSizeGridSpacingPx / 2;
            }
        } else if ((itemPosition + 2) % mGridSize == 0) {
            mNeedLeftSpacing = false;
            outRect.left = mSizeGridSpacingPx / 2;
            outRect.right = mSizeGridSpacingPx - padding;
        } else {
            mNeedLeftSpacing = false;
            outRect.left = mSizeGridSpacingPx / 2;
            outRect.right = mSizeGridSpacingPx / 2;
        }
        outRect.bottom = 0;
    }
}
