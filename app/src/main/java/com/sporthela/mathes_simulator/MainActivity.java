package com.sporthela.mathes_simulator;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.sporthela.mathes_simulator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.firstText.setText("testando com a classe em java");
        setContentView(binding.getRoot());

    }
}
