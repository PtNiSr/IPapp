package com.ni___ckel.ipapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private FloatingActionButton floatingActionButtonAdd;
    private MainViewModel viewModel;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        notesAdapter = new NotesAdapter();

        notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(IPaddress ipaddress) {

                int id = ipaddress.getId();
                String id_string = String.valueOf(id);
                Intent intent = Saved_info.newIntent(MainActivity.this);
                intent.putExtra("id_string", id_string);
                startActivity(intent);
            }
        });

        recyclerViewNotes.setAdapter(notesAdapter);

        viewModel.getIPaddress().observe(this, new Observer<List<IPaddress>>() {
            @Override
            public void onChanged(List<IPaddress> iPaddresses) {
                notesAdapter.setIpADDresses(iPaddresses);
            }
        });

        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ipADD.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT
                ) {
                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target
                    ) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                         int direction
                    ) {
                        int position = viewHolder.getAdapterPosition();
                        IPaddress ipaddress = notesAdapter.getIpADDresses().get(position);
                        viewModel.remove(ipaddress);

                    }
                });

        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);

    }

    private void initViews() {
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        floatingActionButtonAdd = findViewById(R.id.floatingActionButtonAdd);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}