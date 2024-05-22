package ru.mirea.makarovra.notebook;

import android.content.pm.PackageManager;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 200;
    private boolean isWork = false;

    EditText quote;
    EditText fileName;
    Button saveButton;
    Button downloadButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quote=findViewById(R.id.editTextText1);
        fileName= findViewById(R.id.editTextText2);
        saveButton=findViewById(R.id.button1);
        downloadButton=findViewById(R.id.button2);

        final int storagePermissionStatus = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Log.i(TAG, String.valueOf(storagePermissionStatus));
        Log.i(TAG, String.valueOf(PackageManager.PERMISSION_GRANTED));
        if (storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[] {
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWork&&isExternalStorageReadable()) {
                    readFileFromExternalStorage();
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, String.valueOf(isExternalStorageWritable()));
                if (isWork&&isExternalStorageWritable()) {
                    writeFileToExternalStorage();
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, String.valueOf(requestCode));
        if (requestCode == 200) {
            Log.i(TAG, String.valueOf(grantResults.length));
            Log.i(TAG, String.valueOf(grantResults[0]));
            if (grantResults.length > 0 || grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isWork=true;
            }
        }
    }

    public void writeFileToExternalStorage() {

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        Log.i(TAG, String.valueOf(path));
        File file = new File(path, fileName.getText()+ ".txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter output = new OutputStreamWriter(fileOutputStream);
            output.write(String.valueOf(quote.getText()));
            output.close();
        } catch (IOException e) {
            Log.w("ExternalStorage", "Error writing " + file, e);
        }
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    public void readFileFromExternalStorage() {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, fileName.getText()+ ".txt");
        try {
            FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            quote.setText(line);
            Log.w("ExternalStorage", String.format("Read from file %s successful", line));
        } catch (Exception e) {
            Log.w("ExternalStorage", String.format("Read from file %s failed", e.getMessage()));
        }
    }
}
