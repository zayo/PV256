package cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.tonicartos.superslim.GridSLM;

/**
 * Created by prasniatko on 27/10/15.
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
  public interface OnItemClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
  }

  private OnItemClickListener mListener;
  private GestureDetector mGestureDetector;

  public RecyclerItemClickListener(Context context, final RecyclerView recyclerView,
    OnItemClickListener listener) {
    mListener = listener;

    mGestureDetector =
      new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
          return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
          View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

          if (childView != null && mListener != null) {
            GridSLM.LayoutParams lp =
              (GridSLM.LayoutParams) childView.getLayoutParams();
            if (!lp.isHeader) {
              mListener
                .onItemLongClick(childView, recyclerView.getChildAdapterPosition(
                  childView));
            }
          }
        }
      });
  }

  @Override
  public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
    View childView = view.findChildViewUnder(e.getX(), e.getY());

    if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
      GridSLM.LayoutParams lp = (GridSLM.LayoutParams) childView.getLayoutParams();
      if (!lp.isHeader) {
        mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
      }
    }

    return false;
  }

  @Override
  public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
  }

  @Override
  public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

  }
}