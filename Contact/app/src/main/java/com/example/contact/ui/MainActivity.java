package com.example.contact.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.contact.R;
import com.example.contact.helper.ViewModelFactory;
import com.example.contact.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ContactAdapter adapter;

    @NonNull
    private static ContactMainViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(ContactMainViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ContactMainViewModel contactMainViewModel = obtainViewModel(MainActivity.this);
        contactMainViewModel.getAllContacts().observe(this, contacts -> {
            if (contacts != null) {
                adapter.setListContacts(contacts);
            }
        });
        adapter = new ContactAdapter();
        binding.recycleContact.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleContact.setHasFixedSize(true);
        binding.recycleContact.setAdapter(adapter);
        binding.tvOption.setOnClickListener(view -> {
            if (view.getId() == R.id.tv_option) {
                Intent intent = new Intent(MainActivity.this, InsertUpdateActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}