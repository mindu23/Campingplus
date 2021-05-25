package com.example.Camping_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

public class CampUploadActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    private ImageView addphoto_image;
    private Button button_camp_upload;
    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        addphoto_image = findViewById(R.id.addphoto_image);
        addphoto_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
                tempFile = getImage(REQUEST_CODE, intent);

            }
        });

        button_camp_upload = findViewById(R.id.camp_upload_button);
        button_camp_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FTPServerData imageServer = new FTPServerData();
                //CampUploadControl ftp_ivr = new CampUploadControl();
                boolean result = false;
                try {
                    result = upload(tempFile, imageServer.getFTPServerData());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("FTP result : " + result);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    addphoto_image.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    public File getImage(int requestCode, Intent data) {
        File tempFile = null;

        if (requestCode == 1) {

            Uri photoUri = data.getData();

            Cursor cursor = null;

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = {MediaStore.Images.Media.DATA};

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));
                System.out.println(tempFile);

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            setImage();
        }
        return tempFile;
    }

    private void setImage() {

        ImageView imageView = findViewById(R.id.addphoto_image);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        imageView.setImageBitmap(originalBm);

    }
    public boolean upload(File org, FTPServerData serverData)
            throws SocketException, IOException, Exception {

        FileInputStream fis = null;

        org.apache.commons.net.ftp.FTPClient clnt = new org.apache.commons.net.ftp.FTPClient();
        clnt.setControlEncoding("utf-8");

        try {
            clnt.connect(serverData.getfServerIp());
            //clnt.setBufferSize(1024*1024);
            int reply = clnt.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                throw new Exception("ftp connection refused");
            }

            clnt.setSoTimeout(1000 * 10);
            clnt.login(serverData.getfServerId(), serverData.getfSserverPassword());
            clnt.setFileType(FTP.BINARY_FILE_TYPE);


            clnt.enterLocalActiveMode();

            //clnt.enterLocalPassiveMode();
            //clnt.changeWorkingDirectory(defaultPath);
            //clnt.makeDirectory("");

            fis = new FileInputStream(org);
            return clnt.storeFile(serverData.getfServerPath(), fis);
        }
        finally {
            if (clnt.isConnected()) {
                clnt.disconnect();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }
}