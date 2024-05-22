package ru.mirea.makarovra.lesson6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.makarovra.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPref = getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        binding.editText.setText(sharedPref.getString("GROUP", "unknown"));
        binding.editText2.setText(sharedPref.getString("NUMBER", "0"));
        binding.editText3.setText(sharedPref.getString("FILM", "unknown"));
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("GROUP", String.valueOf(binding.editText.getText()));
                editor.putString("NUMBER", String.valueOf(binding.editText2.getText()));
                editor.putString("FILM",String.valueOf(binding.editText3.getText()));
                editor.apply();
            }
        });
    }
}