package football.focus.footfragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Timer;
import java.util.TimerTask;

import football.focus.footfragments.league.LeagueFragment;
import football.focus.footfragments.Util.GetData;
import football.focus.footfragments.Util.SaveData;
import football.focus.footfragments.fixtures.FixtureFragment;
import football.focus.footfragments.login.LoginActivity;
import football.focus.footfragments.news.NewsFragment;
import football.focus.footfragments.profile.ProfileFragment;
import football.focus.footfragments.squad.SquadFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Albion Rovers F.C.");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_camera);

        NewsFragment nf = new NewsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.flContent, nf);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("About")
                    .setMessage("Albion Rovers F.C.\n\nDeveloped by\nec1529114\n\nFor\nGraded Unit 2\nHND Software Development\nEdinburgh College\n\nContent Source:\nhttp://albionroversfc.co.uk\n\n\u00A9 2019")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Class fragmentClass = null;

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            fragmentClass = NewsFragment.class;
            updateFragment(fragmentClass);
        } else if (id == R.id.nav_gallery) {
            fragmentClass = SquadFragment.class;
            updateFragment(fragmentClass);
        } else if (id == R.id.nav_slideshow) {
            fragmentClass = FixtureFragment.class;
            updateFragment(fragmentClass);
        } else if (id == R.id.nav_share) {
            fragmentClass = ProfileFragment.class;
            updateFragment(fragmentClass);
        } else if (id == R.id.nav_refresh) {
            new GetData(MainActivity.this).execute("https://www.sedhna.com/rovers/api/news", "newsFile");
            new GetData(MainActivity.this).execute("https://www.sedhna.com/rovers/api/players", "playersFile");
            new GetData(MainActivity.this).execute("https://www.sedhna.com/rovers/api/fixtures", "fixturesFile");
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    updateFragment(getSupportFragmentManager().findFragmentById(R.id.flContent).getClass());
                }
            };
            Timer t = new Timer();
            t.schedule(task,1000);
        } else if (id == R.id.nav_league_table) {
            fragmentClass = LeagueFragment.class;
            updateFragment(fragmentClass);
        } else if (id == R.id.nav_send) {
            //Log out
            SaveData.delete("token", MainActivity.this);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return true;
    }

    private void updateFragment(Class fragmentClass)
    {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


}
