package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.example.assignment1.Presenter.TrendingPresenter;
import com.example.assignment1.View.TrendingFragment;
import com.example.assignment1.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    private TrendingPresenter mTrendingPresenter = new TrendingPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TrendingFragment trendingFragment =  (TrendingFragment) getSupportFragmentManager().findFragmentById(R.id.contentView);

        if (trendingFragment == null) {
            // Create the fragment
            trendingFragment = TrendingFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), trendingFragment, R.id.contentView);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ) {
            case android.R.id.home :

        }

        return super.onOptionsItemSelected(item);
    }
}
