package com.example.anhbui.sportaccessoriesstore.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhbui.sportaccessoriesstore.Model.Product;
import com.example.anhbui.sportaccessoriesstore.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.NewProductViewHoler> {

    private Activity activity;
    private ArrayList<Product> products;

    public NewProductAdapter(Activity activity, ArrayList<Product> products) {
        this.activity = activity;
        this.products = products;
    }

    @NonNull
    @Override
    public NewProductViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_new_item, null);
        return new NewProductViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductViewHoler holder, int position) {
        Product product = products.get(position);
        holder.mNameProductTextView.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String cost = decimalFormat.format(product.getCost());
        holder.mCostProductTextView.setText("Giá: " + cost + " vnđ");
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
        holder.mImageProductImageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class NewProductViewHoler extends RecyclerView.ViewHolder {
        public ImageView mImageProductImageView;
        public TextView mNameProductTextView, mCostProductTextView;

        public NewProductViewHoler(View itemView) {
            super(itemView);
            mCostProductTextView = itemView.findViewById(R.id.text_view_product_cost);
            mNameProductTextView = itemView.findViewById(R.id.text_view_product_name);
            mImageProductImageView = itemView.findViewById(R.id.image_view_product);
        }
    }
}
