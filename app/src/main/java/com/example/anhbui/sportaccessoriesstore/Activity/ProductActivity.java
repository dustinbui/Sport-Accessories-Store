package com.example.anhbui.sportaccessoriesstore.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.anhbui.sportaccessoriesstore.Adapter.ProductAdapter;
import com.example.anhbui.sportaccessoriesstore.Model.Product;
import com.example.anhbui.sportaccessoriesstore.R;
import com.example.anhbui.sportaccessoriesstore.Ultil.Database;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    ListView mProductListView;
    Toolbar mToolbar;
    ArrayList<Product> products;
    ProductAdapter productAdapter;
    View footerView;
    MyHandler myHandler;

    private int p = 1;
    private int idType = 1;
    private boolean isLoading = false;
    private boolean limitData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        addControl();
        idType = getIdType();
        actionToolbar();
        getData(p);
        mProductListViewOnItemClickListener();
        mProductListViewLoadMoreData();
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

    private void mProductListViewLoadMoreData() {
        mProductListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItemCount, int totalItemCount) {
                if (absListView.getLastVisiblePosition() == totalItemCount - 1 && !isLoading && !limitData) {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void mProductListViewOnItemClickListener() {
        mProductListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ProductActivity.this, DetailProduct.class);
                intent.putExtra("product", products.get(i));
                startActivity(intent);
            }
        });
    }

    private int getIdType() {
        Intent intent = getIntent();
        idType = intent.getIntExtra("idType", -1);
        return idType;
    }

    private void getData(int page) {
        SQLiteDatabase database = Database.initDatabase(this, Database.DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Product WHERE idtype = ?", new String[]{idType + ""});
        int start = (page - 1) * 5;
        int end = start + 4;
        int max = cursor.getCount() - 1;
        if (end <= max) {
            for (int i = start; i <= end; i++) {
                cursor.moveToPosition(i);
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int cost = cursor.getInt(2);
                String description = cursor.getString(3);
                byte[] image = cursor.getBlob(4);
                products.add(new Product(id, name, cost, description, image, idType));
            }
        } else {
            for (int i = start; i <= max; i++) {
                cursor.moveToPosition(i);
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int cost = cursor.getInt(2);
                String description = cursor.getString(3);
                byte[] image = cursor.getBlob(4);
                products.add(new Product(id, name, cost, description, image, idType));
                limitData = true;
                mProductListView.removeFooterView(footerView);
            }
        }
        productAdapter.notifyDataSetChanged();
    }

    private void actionToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        switch (idType) {
            case 1:
                mToolbar.setTitle("Găng tay");
                break;
            case 2:
                mToolbar.setTitle("Vợt");
                break;
            case 3:
                mToolbar.setTitle("Giày");
                break;
            case 4:
                mToolbar.setTitle("Ghế tập tạ");
                break;
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void addControl() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.footer_view, null);
        mProductListView = findViewById(R.id.list_view_product);
        mToolbar = findViewById(R.id.tool_bar_product);
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(), products);
        mProductListView.setAdapter(productAdapter);
        myHandler = new MyHandler();
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mProductListView.addFooterView(footerView);
                    break;
                case 1:
                    mProductListView.removeFooterView(footerView);
                    getData(++p);
                    isLoading = false;
                    break;
            }
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1);
            myHandler.sendMessage(message);
        }
    }
}
