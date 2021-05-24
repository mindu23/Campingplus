package com.example.Camping_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Camping_v1.R;

import java.io.InputStream;

public class CampUploadActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    private ImageView addphoto_image;
    private Button addphoto_btn_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        addphoto_image = findViewById(R.id.addphoto_image);

        addphoto_image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    addphoto_image.setImageBitmap(img);
                }catch(Exception e)
                {

                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

}