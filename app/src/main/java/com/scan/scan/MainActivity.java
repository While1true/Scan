package com.scan.scan;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends ScanAcitvity {
    private BufferedWriter stringWriter;
    private TextView editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        editText.setMovementMethod(ScrollingMovementMethod.getInstance());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},200);
        }else {
            readFromPath(editText);
        }
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (stringWriter != null) {
                        stringWriter.close();
                    }

                    File directory = Environment.getExternalStorageDirectory();
                    File file = new File(directory, "scan.txt");
                    file.delete();
                    editText.setText("");
                    stringWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void readFromPath(TextView editText) {
        File directory = Environment.getExternalStorageDirectory();
        File file = new File(directory, "scan.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedReader bufferedReader = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String read = null;
            while ((read = bufferedReader.readLine()) != null) {
                stringBuilder.append(read+"\n");
            }
            editText.setText(stringBuilder);
//            editText.setSelection(stringBuilder.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    stringWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CoreApp.getmScanner().close();
    }

    @Override
    protected void getScanCode(final String code, Integer scanType) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    editText.setText(editText.getText() + code + "\n");
//                    editText.setSelection(editText.getText().length());
                }
            });
            stringWriter.write(code);
            stringWriter.newLine();
            stringWriter.flush();
            playBeepSound();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        readFromPath(editText);
    }
}
