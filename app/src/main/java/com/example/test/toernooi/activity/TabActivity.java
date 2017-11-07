package com.example.test.toernooi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.test.toernooi.R;
import com.example.test.toernooi.activity.speler.AddSpelerActivity;
import com.example.test.toernooi.activity.toernooi.AddToernooiActivity;
import com.example.test.toernooi.adapter.SectionsPageAdapter;
import com.example.test.toernooi.adapter.SpelerAdapter;
import com.example.test.toernooi.adapter.ToernooiAdapter;
import com.example.test.toernooi.fragment.SpelerFragment;
import com.example.test.toernooi.fragment.ToernooiFragment;
import com.example.test.toernooi.model.Toernooi;
import com.facebook.stetho.Stetho;

import java.util.List;

public class TabActivity extends AppCompatActivity {

    private View toernooitab;
    private View spelertab;

    private RecyclerView list;
    private ToernooiAdapter mToernooiAdapter;
    private SpelerAdapter mSpelerAdapter;
    private List<Toernooi> mToernooien;

    private SectionsPageAdapter adapter;
    private ViewPager mViewPager;

    private static final String TITLE_TOERNOOI_FRAGMENT = "TOERNOOI";
    private static final String TITLE_SPELER_FRAGMENT = "SPELER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        Stetho.initializeWithDefaults(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPager(mViewPager);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //floating action button. Voor ander tabblad moet de button een andere pagina openenen.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getPageTitle(tabLayout.getSelectedTabPosition()) == TITLE_TOERNOOI_FRAGMENT) {
                    Intent intent = new Intent(TabActivity.this, AddToernooiActivity.class);
                    startActivity(intent);
                } else if (adapter.getPageTitle(tabLayout.getSelectedTabPosition()) == TITLE_SPELER_FRAGMENT){
                    Intent intent = new Intent(TabActivity.this, AddSpelerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void setUpViewPager(ViewPager viewpager) {
        adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ToernooiFragment(), TITLE_TOERNOOI_FRAGMENT);
        adapter.addFragment(new SpelerFragment(), TITLE_SPELER_FRAGMENT);
        viewpager.setAdapter(adapter);
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
