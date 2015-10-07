package entropia.app.com.andoidcdb.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.entity.Control;
import entropia.app.com.andoidcdb.ui.fragment.AverageGainFragment;
import entropia.app.com.andoidcdb.ui.fragment.ContributionsFragment;
import entropia.app.com.andoidcdb.ui.fragment.ControlsFragment;
import entropia.app.com.andoidcdb.ui.fragment.SummaryFragment;

/**
 * Created by renan on 06/09/15.
 */


public class DrawerLayoutNew extends AppCompatActivity {

    @Bind(value = R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(value = R.id.navigation_view)
    NavigationView navigationView;

    @Bind(value = R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_drawer);

        loadValues();
        setToolbar();
        setDrawerLayout(savedInstanceState);
    }

    private void loadValues() {
        ButterKnife.bind(this);
    }

    private void setDrawerLayout(Bundle savedInstanceState) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                selectItem(menuItem.getItemId());
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.abc_action_bar_home_description,
                R.string.abc_action_bar_home_description
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if (savedInstanceState == null) {
            if (Control.getControl() != null){
                selectItem(SummaryFragment.ID_FRAGMENT);
                getSupportActionBar().setTitle(R.string.summary_fragment);
            } else {
                selectItem(ControlsFragment.ID_FRAGMENT);
                getSupportActionBar().setTitle(R.string.controls_fragment);
            }
        }
    }

    private void selectItem(int id) {
        Fragment fragment = getFragmentById(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        drawerLayout.closeDrawers();
    }

    private Fragment getFragmentById(int id){
        switch (id){
            case SummaryFragment.ID_FRAGMENT:
                return new SummaryFragment();
            case AverageGainFragment.ID_FRAGMENT:
                return new AverageGainFragment();
            case ContributionsFragment.ID_FRAGMENT:
                return new ContributionsFragment();
            case ControlsFragment.ID_FRAGMENT:
                return new ControlsFragment();
            default:
                return new SummaryFragment();
        }
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            super.onBackPressed();
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
