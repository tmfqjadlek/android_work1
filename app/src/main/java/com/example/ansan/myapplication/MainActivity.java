package com.example.ansan.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onclick(View v){
        if(isStoragePermissionGranted()==false){
            Toast.makeText(getApplicationContext(),"SD Card사용불가",Toast.LENGTH_LONG).show();
            return;
        }

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String folder = path  + "/seulbeom";
        String filename = folder + "/test.txt";
        File mydir = new File(folder); //이름정함
        switch (v.getId()){
            case R.id.button: //폴더생성
                mydir.mkdir();
                Toast.makeText(getApplicationContext(),"폴더생성완료",Toast.LENGTH_SHORT).show();
                break;

            case R.id.button2: //폴더삭제
                mydir.delete();
                Toast.makeText(getApplicationContext(),"폴더삭제완료",Toast.LENGTH_SHORT).show();
                break;

            case R.id.button3: //파일생성
                try {
                    FileOutputStream foss = new FileOutputStream(filename);
                    foss.write("안녕하세요".getBytes());
                    foss.close();
                    Toast.makeText(getApplicationContext(),"파일생성완료",Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button4: //파일읽기
                FileInputStream fos = null;
                try {
                    fos = new FileInputStream(filename);
                    byte arr[]=new byte[fos.available()];
                    fos.read();
                    fos.close();
                    String str = new String(arr);
                    Toast.makeText(getApplicationContext(),"파일내용" +str ,Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
    String TAG = "SDCARD";
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
}
