package com.example.anhbui.sportaccessoriesstore.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhbui.sportaccessoriesstore.Adapter.CartAdapter;
import com.example.anhbui.sportaccessoriesstore.R;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ListView mCartListView;
    TextView mNoteTextView;
    public static TextView mTotalCostTextView;
    Button mPayButton, mContinueBuyButton;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        addControl();
        actionBar();
        checkData();
        evenUltil();
        catchOnItemListView();
        eventButton();
    }

    private void eventButton() {
        mContinueBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        mPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.carts.size() == 0) {
                    Toast.makeText(CartActivity.this, "Giỏ hàng của bạn chưa có sản phẩm nào!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CartActivity.this, CustomerInformationActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void catchOnItemListView() {
        mCartListView.setLongClickable(true);
        mCartListView.setClickable(true);
        mCartListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.carts.remove(position);
                        cartAdapter.notifyDataSetChanged();
                        evenUltil();
                        if (MainActivity.carts.size() == 0) {
                            mNoteTextView.setVisibility(View.VISIBLE);
                            mCartListView.setVisibility(View.INVISIBLE);
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void evenUltil() {
        int totalCost = 0;
        for (int i = 0; i < MainActivity.carts.size(); i++) {
            totalCost += MainActivity.carts.get(i).getCost();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        mTotalCostTextView.setText("Tổng tiền: " + decimalFormat.format(totalCost) + " vnđ");
    }

    private void checkData() {
        if (MainActivity.carts.size() == 0) {
            cartAdapter.notifyDataSetChanged();
            mNoteTextView.setVisibility(View.VISIBLE);
            mCartListView.setVisibility(View.INVISIBLE);
        } else {
            cartAdapter.notifyDataSetChanged();
            mNoteTextView.setVisibility(View.INVISIBLE);
            mCartListView.setVisibility(View.VISIBLE);
        }
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
        mToolbar = findViewById(R.id.toolbar_cart);
        mCartListView = findViewById(R.id.list_view_cart);
        mNoteTextView = findViewById(R.id.text_view_note);
        mTotalCostTextView = findViewById(R.id.text_view_total_cost);
        mPayButton = findViewById(R.id.button_pay);
        mContinueBuyButton = findViewById(R.id.button_continue_buy);
        cartAdapter = new CartAdapter(this, MainActivity.carts);
        mCartListView.setAdapter(cartAdapter);
    }
}
