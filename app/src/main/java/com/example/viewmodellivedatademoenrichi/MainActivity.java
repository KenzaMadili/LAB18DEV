package com.example.viewmodellivedatademoenrichi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private CounterViewModel viewModel;

    private TextView tvCount;
    private Button btnIncrement, btnDecrement, btnReset, btnThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCount = findViewById(R.id.tvCount);
        btnIncrement = findViewById(R.id.btnIncrement);
        btnDecrement = findViewById(R.id.btnDecrement);
        btnReset = findViewById(R.id.btnReset);
        btnThread = findViewById(R.id.btnThread);

        // ViewModel
        viewModel = new ViewModelProvider(this).get(CounterViewModel.class);

        // Observer LiveData
        viewModel.getCount().observe(this, value -> {
            tvCount.setText(String.valueOf(value));
        });

        // Actions
        btnIncrement.setOnClickListener(v -> viewModel.increment());
        btnDecrement.setOnClickListener(v -> viewModel.decrement());
        btnReset.setOnClickListener(v -> viewModel.reset());

        // Thread button
        btnThread.setOnClickListener(v -> viewModel.incrementFromThread());
    }
}