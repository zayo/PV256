package cz.muni.fi.pv256.movio.uco_374524.superprojekt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.R;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.data.DataProvider;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.fragment.MovieDetailFragment;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.fragment.MoviesListFragment;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.RecyclerItemClickListener;

public class MovieListActivity extends AppCompatActivity {

    private static final String TAG = ".MovieListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MoviesListFragment mlf =
            (MoviesListFragment) getFragmentManager().findFragmentById(R.id.list_frag);

        final MovieDetailFragment mdf =
            (MovieDetailFragment) getFragmentManager().findFragmentById(R.id.details_frag);

        if (mlf != null) {
            mlf.setData(DataProvider.get().getData());
            mlf.addListOnClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.d(TAG, "onItemClick() called with: " + "view = [" + view + "], position = ["
                        + position + "]");
                    if (mdf == null) {
                        Intent intent =
                            new Intent(MovieListActivity.this, MovieDetailActivity.class);
                        intent.putExtra("movie", DataProvider.get().getData().get(position));
                        startActivity(intent);
                    } else {
                        mdf.setData(DataProvider.get().getData().get(position));
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    Log.d(TAG,
                        "onItemLongClick() called with: " + "view = [" + view + "], position = ["
                            + position + "]");
                    Toast.makeText(MovieListActivity.this,
                        DataProvider.get().getData().get(position).title, Toast.LENGTH_SHORT)
                        .show();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called with: " + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
