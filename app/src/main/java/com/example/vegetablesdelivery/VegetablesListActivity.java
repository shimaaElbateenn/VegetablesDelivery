package com.example.vegetablesdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vegetablesdelivery.Interface.ItemClickListener;
import com.example.vegetablesdelivery.Model.Vegetable;
import com.example.vegetablesdelivery.ViewHolder.VegetableViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class VegetablesListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference vegList;
    String categoryId = "";
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetables_list);
        setupVariables();
        getIdFromIntent();
    }

    void setupVariables() {
        database = FirebaseDatabase.getInstance();
        vegList = database.getReference("Vegetables");
        recyclerView = findViewById(R.id.recycler_veg);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    void getIdFromIntent() {
        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        Log.e("ID", categoryId);
        if (!categoryId.isEmpty() && categoryId != null) {
            loadVegList(categoryId);
        }
    }

    private void loadVegList(String categoryId) {
        FirebaseRecyclerOptions<Vegetable> options = new FirebaseRecyclerOptions.Builder<Vegetable>()
                .setQuery(vegList.orderByChild("MenuId").equalTo(categoryId), Vegetable.class).build();
        adapter = new FirebaseRecyclerAdapter<Vegetable, VegetableViewHolder>(options) {
            @NonNull
            @Override
            public VegetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vegetable_item, parent, false);
                return new VegetableViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull VegetableViewHolder holder, int position, @NonNull Vegetable model) {
                holder.veg_name.setText(model.getName());
                Picasso.get().load(model.getImage()).into(holder.veg_image);
                final Vegetable clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(VegetablesListActivity.this, VegetablesDetailActivity.class);
                        intent.putExtra("FoodId", adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}