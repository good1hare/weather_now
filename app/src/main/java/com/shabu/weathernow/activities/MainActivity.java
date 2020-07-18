package com.shabu.weathernow.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.shabu.weathernow.R;

public class MainActivity extends AppCompatActivity {

    private CardsFragment mCardsFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentManager = getSupportFragmentManager();

        mCardsFragment = new CardsFragment();
        mFragmentManager.beginTransaction().replace(R.id.fragment_container, mCardsFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_add:
                mCardsFragment.showAlertForAddCity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void toastError(){
        Toast.makeText(this, "Ой, вы сделали что-то не так :(", Toast.LENGTH_SHORT).show();
    }
}