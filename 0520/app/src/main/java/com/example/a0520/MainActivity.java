package com.example.a0520;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Integer> imageList;
    private static final int DP = 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();
        //ab.setTitle("ActionBar Title by setTitle()");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);

        this.initializeData();

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);

        float density = getResources().getDisplayMetrics().density;
        int margin = (int) (DP * density);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);

        viewPager.setAdapter(new ViewPagerAdapter(this, imageList));
    }

    public void initializeData(){
        imageList = new ArrayList<>();

        imageList.add(R.drawable.camping1);
        imageList.add(R.drawable.camping2);
        imageList.add(R.drawable.camping3);
        imageList.add(R.drawable.camping4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.btn_login:
                Toast.makeText(this, "login button click", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.btn_signin:
                Toast.makeText(this, "signin button click", Toast.LENGTH_SHORT).show();
                Intent signinIntent = new Intent(this, JoinActivity.class);
                startActivity(signinIntent);
                break;
            case R.id.btn_search:
                Toast.makeText(this, "search button click", Toast.LENGTH_SHORT).show();
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}