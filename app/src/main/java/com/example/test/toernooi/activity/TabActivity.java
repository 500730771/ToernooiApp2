package com.example.test.toernooi.activity;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.test.toernooi.R;
import com.example.test.toernooi.activity.speler.AddSpelerActivity;
import com.example.test.toernooi.activity.toernooi.AddToernooiActivity;
import com.example.test.toernooi.adapter.SectionsPageAdapter;
import com.example.test.toernooi.data.DataSource;
import com.example.test.toernooi.fragment.SensorFragment;
import com.example.test.toernooi.fragment.SpelerFragment;
import com.example.test.toernooi.fragment.ToernooiFragment;
import com.example.test.toernooi.utility.ConfirmDeleteDialog;
import com.facebook.stetho.Stetho;

public class TabActivity extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener {
    private SectionsPageAdapter adapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    private static final String TITLE_TOERNOOI_FRAGMENT = "TOERNOOI";
    private static final String TITLE_SPELER_FRAGMENT = "SPELER";
    private static final String TITLE_SENSOR_FRAGMENT = "SENSOR";

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

        tabLayout = (TabLayout) findViewById(R.id.tabs);
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
        adapter.addFragment(new SensorFragment(), TITLE_SENSOR_FRAGMENT);
        viewpager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Deze methode wordt uitgevoerd als er op de knop wordt geklikt helemaal bovenin het menu.
     * Er wordt een p;op up button laten zien of alle spelers/toernooien moeten worden verwijderd.
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_delete_all) {
            // Show the ConfirmDeleteDialog
            DialogFragment dialog = new ConfirmDeleteDialog();
            Bundle bundle = new Bundle();
            if (adapter.getPageTitle(tabLayout.getSelectedTabPosition()) == TITLE_TOERNOOI_FRAGMENT) {
                bundle.putString("message", getString(R.string.dialog_toernooi_deletion_all));
            } else if (adapter.getPageTitle(tabLayout.getSelectedTabPosition()) == TITLE_SPELER_FRAGMENT) {
                bundle.putString("message", getString(R.string.dialog_speler_deletion_all));
            }
            bundle.putString("positiveButton", getString(R.string.dialog_deletion_positive));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "ConfirmDeleteDialog");
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Als er op verwidjerknop wordt geklikt en deze wordt bevestigd dat wordt dit uitgevoerd.
     * Er wordt gekeken op welke fragment er zit en afhankelijk daarvan worden of de spelers of alle teornoien verwijderd.
     * @param dialog
     */
    public void onDialogPositiveClick(DialogFragment dialog)
    {
        //User clicked on the confirm button of the Dialog, delete the game from Database
        DataSource dataSource = new DataSource(this);

        if (adapter.getPageTitle(tabLayout.getSelectedTabPosition()) == TITLE_TOERNOOI_FRAGMENT) {
            //Delete all toernooien
            dataSource.deleteAllToernooien();
            //toernooien have been deleted, go back to the MainActivity
            showDeletedAllToast(getString(R.string.toernooien_deleted));
        } else if (adapter.getPageTitle(tabLayout.getSelectedTabPosition()) == TITLE_SPELER_FRAGMENT){
            //Delete all spelers
            dataSource.deleteAllSpelers();
            //spelers have been deleted, go back to the MainActivity
            showDeletedAllToast(getString(R.string.spelers_deleted));
        }
        //refresh activity
        finish();
        startActivity(getIntent());
    }

    /**
     * Als er op de knop cancel geklikt wordt dan gebeurt er niks
     *
     * @param dialog
     */
    public void onDialogNegativeClick(DialogFragment dialog)
    {
        //Do nothing, Dialog will disappear
    }

    /**
     * Deze message wordt laten zien als de popup message is geconfirmed
     * @param message = de message die wordt laten zien in een toast
     */
    private void showDeletedAllToast(String message)
    {
        Context context = getApplicationContext();
        String text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
