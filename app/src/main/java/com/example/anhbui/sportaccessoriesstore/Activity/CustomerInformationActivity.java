package com.example.anhbui.sportaccessoriesstore.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anhbui.sportaccessoriesstore.Model.Cart;
import com.example.anhbui.sportaccessoriesstore.R;
import com.example.anhbui.sportaccessoriesstore.Ultil.Database;

public class CustomerInformationActivity extends AppCompatActivity {

    EditText mNameCustomerEditText, mPhoneEditText, mEmailEditText;
    Button mConfirmButton, mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);
        addControl();
        eventButton();
//        SQLiteDatabase database = Database.initDatabase(this, Database.DATABASE_NAME);
//        Cursor cursor = database.rawQuery("SELECT * FROM Orderr", null);
//        for (int i = 0; i < cursor.getCount(); i++) {
//            cursor.moveToPosition(i);
//            Toast.makeText(this, cursor.getString(1), Toast.LENGTH_SHORT).show();
//        }
    }

    private void eventButton() {
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mNameCustomerEditText.getText().toString().trim();
                String phone = mPhoneEditText.getText().toString().trim();
                String email = mEmailEditText.getText().toString().trim();
                if ((name.length() * phone.length() * email.length()) == 0) {
                    Toast.makeText(CustomerInformationActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase database = Database.initDatabase(CustomerInformationActivity.this, Database.DATABASE_NAME);
                    ContentValues contentValuesOrder = new ContentValues();
                    contentValuesOrder.put("name", name);
                    contentValuesOrder.put("phone", phone);
                    contentValuesOrder.put("email", email);
                    database.insert("Orderr", null, contentValuesOrder);

                    Cursor cursor = database.rawQuery("SELECT * FROM Orderr WHERE name = ? AND phone = ? AND email = ?",
                            new String[]{name, phone, email});
                    cursor.moveToFirst();
                    int idorder = cursor.getInt(0);
                    database.execSQL("CREATE TABLE IF NOT EXISTS DetailOrder (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , idorder INTEGER NOT NULL , idproduct INTEGER NOT NULL , name VARCHAR NOT NULL , cost INTEGER NOT NULL , count INTEGER NOT NULL )");
                    for (int i = 0; i < MainActivity.carts.size(); i++) {
                        Cart cart = MainActivity.carts.get(i);
                        ContentValues contentValuesDetailOrder = new ContentValues();
                        contentValuesDetailOrder.put("idorder", idorder);
                        contentValuesDetailOrder.put("idproduct", cart.getId());
                        contentValuesDetailOrder.put("name", cart.getName());
                        contentValuesDetailOrder.put("cost", cart.getCost());
                        contentValuesDetailOrder.put("count", cart.getCountProduct());
                        database.insert("DetailOrder", null, contentValuesDetailOrder);
                    }
                }
                Toast.makeText(CustomerInformationActivity.this, "Bạn đã đặt hàng thành công, mời bạn tiếp tục mua hàng", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CustomerInformationActivity.this, MainActivity.class));
                MainActivity.carts.clear();
                finish();
            }
        });
    }

    private void addControl() {
        mNameCustomerEditText = findViewById(R.id.edit_text_name_customer);
        mPhoneEditText = findViewById(R.id.edit_text_phone_customer);
        mEmailEditText = findViewById(R.id.edit_text_email_customer);
        mConfirmButton = findViewById(R.id.button_confirm);
        mCancelButton = findViewById(R.id.button_cancel);
    }
}
