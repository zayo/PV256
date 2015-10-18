package cz.muni.fi.pv256.movio.uco_374524.superprojekt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Random;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.R;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.adapter.MovieAdapter;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = ".MainActivity";
    private static final String LOREM = "Lorem ipsum dolor sit amet, a donec consectetuer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,
            "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
        GridView gridView = (GridView) findViewById(R.id.moviews_grid_view);
        ViewStub emptyView = (ViewStub) findViewById(R.id.empty);
        ViewStub noConnView = (ViewStub) findViewById(R.id.no_connection);

        ArrayList<Movie> data = generateData();
        if (data == null) {
            gridView.setVisibility(View.INVISIBLE);
            noConnView.setVisibility(View.VISIBLE);
        } else if (data.isEmpty()) {
            gridView.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            gridView.setAdapter(new MovieAdapter(this, 0, data));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(MainActivity.this, MovieDetailActivity.class));
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

    private ArrayList<Movie> generateData() {

        Random random = new Random(System.currentTimeMillis());
        int items = random.nextInt(100);
        boolean empty = items <= 10;
        boolean no_conn = !empty && items <= 20;

        if (no_conn) {
            return null;
        }
        if (empty) {
            return new ArrayList<>();
        }

        ArrayList<Movie> data = new ArrayList<>(items);

        for (int i = 0; i < items; i++) {
            data.add(new Movie(0L, "", LOREM.substring(random.nextInt(LOREM.length())),
                random.nextBoolean() ? "comedy" : "action"));
        }

        return data;
    }
}
