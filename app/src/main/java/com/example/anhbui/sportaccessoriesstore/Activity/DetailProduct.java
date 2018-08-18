package com.example.anhbui.sportaccessoriesstore.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.anhbui.sportaccessoriesstore.Model.Cart;
import com.example.anhbui.sportaccessoriesstore.Model.Product;
import com.example.anhbui.sportaccessoriesstore.R;

import java.text.DecimalFormat;

public class DetailProduct extends AppCompatActivity {
    ImageView mDetailProductImageView;
    TextView mNameDetailProductTextView, mCostDetailProductTextView, mDetailProductTextView;
    Toolbar mToolbar;
    Spinner mSpinner;
    Button mAddCartButton;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        addControl();
        actionBar();
        getInformation();
        catchEventSpinner();
        eventButtonAddCart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cart_menu) {
            startActivity(new Intent(getApplicationContext(), CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void eventButtonAddCart() {
        mAddCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.carts.size() == 0) {
                    int count = (int) mSpinner.getSelectedItem();
                    int newCost = count * product.getCost();
                    MainActivity.carts.add(new Cart(product.getId(), product.getName(), newCost, product.getImage(), count));
                } else {
                    boolean exists = false;
                    int count = (int) mSpinner.getSelectedItem();
                    for (int i = 0; i < MainActivity.carts.size(); i++) {
                        if (MainActivity.carts.get(i).getId() == product.getId()) {
                            exists = true;
                            int countProduct = count + MainActivity.carts.get(i).getCountProduct();
                            if (countProduct > 10) {
                                countProduct = 10;
                            }
                            MainActivity.carts.get(i).setCountProduct(countProduct);
                            MainActivity.carts.get(i).setCost(product.getCost() * countProduct);
                        }
                    }
                    if (!exists) {
                        MainActivity.carts.add(new Cart(product.getId(), product.getName(), product.getCost() * count,
                                product.getImage(), count));
                    }
                }
                Intent intent = new Intent(DetailProduct.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void catchEventSpinner() {
        Integer[] integers = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, integers);
        mSpinner.setAdapter(arrayAdapter);
    }

    private void getInformation() {
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
        mDetailProductImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mDetailProductImageView.setImageBitmap(bitmap);
        mNameDetailProductTextView.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        mCostDetailProductTextView.setText("Giá: " + decimalFormat.format(product.getCost()) + " vnđ");
        mDetailProductTextView.setText(product.getDescription());
    }

    private void actionBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void addControl() {
        mDetailProductImageView = findViewById(R.id.image_view_detail_product);
        mNameDetailProductTextView = findViewById(R.id.text_view_name_detail_product);
        mCostDetailProductTextView = findViewById(R.id.text_view_cost_detail_product);
        mDetailProductTextView = findViewById(R.id.text_view_detail_product);
        mToolbar = findViewById(R.id.tool_bar_detail_product);
        mSpinner = findViewById(R.id.spinner);
        mAddCartButton = findViewById(R.id.button_add_cart);
    }
}
