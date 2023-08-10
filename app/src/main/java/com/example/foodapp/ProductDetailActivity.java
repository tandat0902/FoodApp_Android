package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.adapters.ProductAdapter;
import com.example.foodapp.models.CartModels;
import com.example.foodapp.models.ProductModels;
import com.example.foodapp.utils.Utils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DecimalFormat;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    RecyclerView recyclerViewProduct;
    List<ProductModels> productModels;
    ProductAdapter productAdapter;
    TextView edtSearch;
    ImageView btnBack, btnCart;
    Button btnAddCart, btnAdd, btnMinus;
    EditText edtQuantity;
    ProductModels product;

    DatabaseHandler db = new DatabaseHandler(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        initBtnBack();
        initBtnCart();
        initBtnAddCart();
        initEdtSearch();

        Intent intentProduct = getIntent();
        if (intentProduct != null && intentProduct.hasExtra("product")) {
            initData(intentProduct);
        }

        initBtnAdd();
        initBtnMinus();
    }

    private void initEdtSearch() {
        edtSearch = (TextView) findViewById(R.id.search);
        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });
    }

    private void initBtnCart() {
        btnCart = (ImageView) findViewById(R.id.cart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });
    }

    private void initData(Intent intent) {

        product = (ProductModels) intent.getParcelableExtra("product");
        RoundedImageView productImage = findViewById(R.id.img_product);
        TextView productName = findViewById(R.id.name_product);
        TextView productPrice = findViewById(R.id.price_product);

        productImage.setImageResource(product.getImg());
        productName.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        productPrice.setText("Giá: " + decimalFormat.format(Double.parseDouble(product.getPrice())) + "đ");

        getRelatedProduct(db, product.getIdType(), product.getName());
        getSuggestProduct(db);

    }

    private void initBtnAdd() {
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtQuantity = (EditText) findViewById(R.id.quantity);
                int temp = Integer.parseInt(String.valueOf(edtQuantity.getText()));
                if(temp < 10){
                    temp += 1;
                    edtQuantity.setText(temp + "");
                }
            }
        });
    }

    private void initBtnMinus() {
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtQuantity = (EditText) findViewById(R.id.quantity);
                int temp = Integer.parseInt(String.valueOf(edtQuantity.getText()));
                if(temp > 1){
                    temp -= 1;
                    edtQuantity.setText(temp + "");
                }
            }
        });
    }

    private void initBtnAddCart() {
        btnAddCart = (Button) findViewById(R.id.addCart);
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInCart();
            }
        });
    }

    private void addInCart(){
        if(Utils.cartList.size() > 0){
            boolean flag = false;
            edtQuantity = (EditText) findViewById(R.id.quantity);
            int sl = Integer.parseInt(String.valueOf(edtQuantity.getText()));
            int quantity = sl;
            for(int i = 0; i < Utils.cartList.size(); i++){
                if(Utils.cartList.get(i).getName() == product.getName()){
                    Utils.cartList.get(i).setQuantity(quantity + Utils.cartList.get(i).getQuantity());
                    long price = Long.parseLong(product.getPrice()) * Utils.cartList.get(i).getQuantity();
                    Utils.cartList.get(i).setPrice(price);
                    flag = true;
                }
            }
            if(!flag){
                long price = Long.parseLong(product.getPrice()) * quantity;
                CartModels cart = new CartModels();
                cart.setPrice(price);
                cart.setQuantity(quantity);
                cart.setId(product.getId());
                cart.setName(product.getName());
                cart.setImg(product.getImg());
                Utils.cartList.add(cart);
            }
        } else{
            edtQuantity = (EditText) findViewById(R.id.quantity);
            int sl = Integer.parseInt(String.valueOf(edtQuantity.getText()));
            int quantity = sl;
            long price = Long.parseLong(product.getPrice()) * quantity;
            CartModels cart = new CartModels();
            cart.setPrice(price);
            cart.setQuantity(quantity);
            cart.setId(product.getId());
            cart.setName(product.getName());
            cart.setImg(product.getImg());
            Utils.cartList.add(cart);
        }
    }

    private void initBtnBack(){
        btnBack = (ImageView) findViewById(R.id.back);
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

    private void getRelatedProduct(DatabaseHandler db, int idType, String nameProduct) {
        recyclerViewProduct = findViewById(R.id.rec_related);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
        recyclerViewProduct.setHasFixedSize(true);

        productModels = db.getAllRelatedProducts(idType, nameProduct);

        productAdapter = new ProductAdapter(productModels);
        recyclerViewProduct.setAdapter(productAdapter);
    }

    private void getSuggestProduct(DatabaseHandler db) {
        recyclerViewProduct = findViewById(R.id.rec_suggested);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
        recyclerViewProduct.setHasFixedSize(true);

        productModels = db.getSuggestedProducts();

        productAdapter = new ProductAdapter(productModels);
        recyclerViewProduct.setAdapter(productAdapter);
    }

}
