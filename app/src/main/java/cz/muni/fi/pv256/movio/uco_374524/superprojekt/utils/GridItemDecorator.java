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

    int firstAfterHeader = layoutParams.getFirstPosition() + 1;
    int currentPosition = layoutParams.getViewLayoutPosition();

    int column = (currentPosition - firstAfterHeader) % gridSize;
    int row = (currentPosition - firstAfterHeader) / gridSize;

    outRect.top = row > 0 ? insetVertical : 0;
    outRect.left = column > 0 ? insetHorizontal : 0;
    outRect.right = 0;
    outRect.bottom = 0;
  }
}