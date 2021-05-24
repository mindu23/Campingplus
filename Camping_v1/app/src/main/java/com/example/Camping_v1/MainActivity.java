package com.example.Camping_v1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.Camping_v1.R;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    Button button_image_load;

    //userdata 선언
    UserData userData = new UserData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();
        //ab.setTitle("ActionBar Title by setTitle()");
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);

        viewPager = (ViewPager)findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        //userdata 받아오기 Intenet에서
        Intent intent = getIntent();
        userData.putUserNum(intent.getStringExtra("UserNum"));

        button_image_load = findViewById(R.id.button_image_load);
        button_image_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPhotoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        if(userData.getUserNum() == null || userData.getUserNum() == "0") {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.main_menu, menu);
            //System.out.println("userNum is "+ intent.getStringExtra("UserNum"));
        }
        else{
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.login_menu, menu);
            //System.out.println("userNum is "+ intent.getStringExtra("UserNum"));
        }
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
                searchIntent.putExtra("UserNum", userData.getUserNum());
                startActivity(searchIntent);
                break;
            case R.id.btn_myPage:
                Toast.makeText(this, "myMenu button click", Toast.LENGTH_SHORT).show();
                Intent myMenuIntent = new Intent(this, MainActivity.class);
                //Intent intent = getIntent();
                //String UserNum = intent.getExtras().getString("UserNum");
                myMenuIntent.putExtra("UserNum", userData.getUserNum());
                startActivity(myMenuIntent);
                break;
            case R.id.btn_logout:
                Toast.makeText(this, "logout button click", Toast.LENGTH_SHORT).show();
                Intent LogoutIntent = new Intent(this, MainActivity.class);
                startActivity(LogoutIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}