package cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tonicartos.superslim.GridSLM;

/**
 * Adds spacing in between items in a grid. Meaning, there will be no margin added at the outer
 * edges of the grid.
 */
public class GridItemDecorator extends RecyclerView.ItemDecoration {

  private int insetHorizontal;
  private int insetVertical;
  private int gridSize;

  public GridItemDecorator(int space, int grid_size) {
    insetHorizontal = insetVertical = space;
    gridSize = grid_size;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
    RecyclerView.State state) {
    GridSLM.LayoutParams layoutParams = (GridSLM.LayoutParams) view.getLayoutParams();

    if (layoutParams.isHeader) {
      outRect.set(0, 0, 0, 0);
      return;
    }

    int start_index = layoutParams.getFirstPosition() + 1;

    // add edge margin only if item edge is not the grid edge
    int position = layoutParams.getViewLayoutPosition() - start_index;
    int itemSpanIndex = (position) % gridSize;
    // is left grid edge?
    outRect.left = itemSpanIndex == 0 ? 0 : insetHorizontal;
    // is top grid edge?
    outRect.top = itemSpanIndex == position ? 0 : insetVertical;
    outRect.right = 0;
    outRect.bottom = 0;
  }
}