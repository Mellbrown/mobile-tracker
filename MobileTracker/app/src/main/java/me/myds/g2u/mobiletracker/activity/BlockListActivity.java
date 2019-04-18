package me.myds.g2u.mobiletracker.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.FragmentTransaction;
import me.myds.g2u.mobiletracker.R;
import me.myds.g2u.mobiletracker.db.BlocksDB;

public class BlockListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private FragmentManager fragmentManager;
    private BlockListFragment blockListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_list);

        BlocksDB.init(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mobile Tracker");

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_saved_blocks:{
                    Intent intent = new Intent(BlockListActivity.this, SavedBlockActivity.class);
                    startActivity(intent);
                }break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        blockListView = new BlockListFragment();
        blockListView.setOnChangeSelectableListener(selectable -> {
            if (selectable) {
                toolbar.getMenu().findItem(R.id.btn_save_blocks).setVisible(true);
                toolbar.getMenu().findItem(R.id.btn_save_blocks).setEnabled(true);
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            } else {
                toolbar.getMenu().findItem(R.id.btn_save_blocks).setVisible(false);
                toolbar.getMenu().findItem(R.id.btn_save_blocks).setEnabled(false);
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_block_list, blockListView);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.on_select_blocks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_save_blocks:
                blockListView.saveSelected();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (blockListView.mAdpater.isSelectable()) {
            blockListView.mAdpater.setSelectable(false);
        } else {
            super.onBackPressed();
        }
    }
}
