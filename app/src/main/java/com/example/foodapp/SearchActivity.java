package com.example.foodapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.adapters.SearchAdapter;
import com.example.foodapp.models.ProductModels;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerViewSearch;
    List<ProductModels> productModels;
    SearchAdapter searchAdapter;
    ImageView btnBack;
    EditText searchPrt;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        initBtnBack();
        getSearchProduct(db);
    }

    private void getSearchProduct(DatabaseHandler db) {
        recyclerViewSearch = findViewById(R.id.search_list);
        recyclerViewSearch.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        recyclerViewSearch.setHasFixedSize(true);

        productModels = db.getSuggestedProducts();
        searchAdapter = new SearchAdapter(productModels);
        recyclerViewSearch.setAdapter(searchAdapter);

        searchPrt = findViewById(R.id.searchPrt);
        searchPrt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txtSearch = s.toString();
                if(txtSearch.isEmpty()){
                    productModels = db.getSuggestedProducts();
                    searchAdapter = new SearchAdapter(productModels);
                    recyclerViewSearch.setAdapter(searchAdapter);
                }else{
                    productModels = db.searchProduct(txtSearch);
                    searchAdapter = new SearchAdapter(productModels);
                    recyclerViewSearch.setAdapter(searchAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initBtnBack() {
        btnBack = findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.back:
                        onBackPressed();
                        break;
                }
            }
        });
    }
}
