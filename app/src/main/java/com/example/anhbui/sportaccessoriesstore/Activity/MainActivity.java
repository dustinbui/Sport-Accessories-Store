package com.example.anhbui.sportaccessoriesstore.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.anhbui.sportaccessoriesstore.Adapter.NewProductAdapter;
import com.example.anhbui.sportaccessoriesstore.Adapter.ProductAdapter;
import com.example.anhbui.sportaccessoriesstore.Adapter.ProductTypeAdapter;
import com.example.anhbui.sportaccessoriesstore.Model.Cart;
import com.example.anhbui.sportaccessoriesstore.Model.Product;
import com.example.anhbui.sportaccessoriesstore.Model.ProductType;
import com.example.anhbui.sportaccessoriesstore.R;
import com.example.anhbui.sportaccessoriesstore.Ultil.Database;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Cart> carts;

    Toolbar mToolbar;
    ListView mProductTypeListView;
    ListView mCommunicationListView;
    DrawerLayout mDrawerLayout;
    ViewFlipper mViewFlipper;
    ArrayList<ProductType> productTypes;
    ArrayList<ProductType> communications;
    ProductTypeAdapter productTypeAdapter;
    ProductTypeAdapter communicationsAdapter;
    RecyclerView mNewProductsRecyclerView;
    NewProductAdapter newProductAdapter;
    ArrayList<Product> newProducts;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        actionBar();
        actionViewFlipper();
        getDataNavitgationView();
        getDataNewProduct();
        mProductTypeListViewClickListener();
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

    private void mProductTypeListViewClickListener() {
        mProductTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                intent.putExtra("idType", productTypes.get(i).getId());
                startActivity(intent);
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        mCommunicationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    startActivity(new Intent(MainActivity.this, InformationActivity.class));
                }
            }
        });
    }

    private void getDataNewProduct() {
        SQLiteDatabase database = Database.initDatabase(this, Database.DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Product ORDER BY id DESC LIMIT 6", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int cost = cursor.getInt(2);
            String description = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            int idType = cursor.getInt(5);
            newProducts.add(new Product(id, name, cost, description, image, idType));
        }
        newProductAdapter.notifyDataSetChanged();
    }

    private void getDataNavitgationView() {
        SQLiteDatabase database = Database.initDatabase(this, Database.DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM ProductType", null);
        productTypes.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            productTypes.add(new ProductType(id, name, image));
        }
        productTypeAdapter.notifyDataSetChanged();

        communications.add(new ProductType(5, "Thông tin", getByteArrayFromDrawble(R.drawable.info)));
        communications.add(new ProductType(6, "Liên hệ", getByteArrayFromDrawble(R.drawable.phone)));
        communicationsAdapter.notifyDataSetChanged();
    }

    private void actionViewFlipper() {
        ArrayList<Integer> resourceImage = new ArrayList<>();
        resourceImage.add(R.drawable.qc1);
        resourceImage.add(R.drawable.qc4);
        resourceImage.add(R.drawable.qc3);
        for (int i = 0; i < resourceImage.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(resourceImage.get(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mViewFlipper.addView(imageView);
        }
        mViewFlipper.setFlipInterval(5000);
        mViewFlipper.setAutoStart(true);
        Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        Animation slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out);
        mViewFlipper.setInAnimation(slideIn);
        mViewFlipper.setOutAnimation(slideOut);
    }

    private void actionBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void addControl() {
        mToolbar = findViewById(R.id.toolbar_main_screen);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mViewFlipper = findViewById(R.id.view_flipper);
        mProductTypeListView = findViewById(R.id.list_view_type_product);
        productTypes = new ArrayList<>();
        productTypeAdapter = new ProductTypeAdapter(this, productTypes);
        mProductTypeListView.setAdapter(productTypeAdapter);
        mCommunicationListView = findViewById(R.id.list_view_communication);
        communications = new ArrayList<>();
        communicationsAdapter = new ProductTypeAdapter(this, communications);
        mCommunicationListView.setAdapter(communicationsAdapter);
        mNewProductsRecyclerView = findViewById(R.id.recycler_view_new_product);
        newProducts = new ArrayList<>();
        newProductAdapter = new NewProductAdapter(this, newProducts);
        mNewProductsRecyclerView.setHasFixedSize(true);
        mNewProductsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mNewProductsRecyclerView.setAdapter(newProductAdapter);
        if (carts == null) {
            carts = new ArrayList<>();
        }
    }

    private byte[] getByteArrayFromDrawble(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitMapData = stream.toByteArray();
        return bitMapData;
    }
}
