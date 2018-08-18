package com.example.anhbui.sportaccessoriesstore.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhbui.sportaccessoriesstore.Model.ProductType;
import com.example.anhbui.sportaccessoriesstore.R;

import java.util.ArrayList;

public class ProductTypeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ProductType> productTypes;

    public ProductTypeAdapter(Context context, ArrayList<ProductType> productTypes) {
        this.context = context;
        this.productTypes = productTypes;
    }

    @Override
    public int getCount() {
        return productTypes.size();
    }

    @Override
    public Object getItem(int i) {
        return productTypes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return productTypes.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ProductTypeViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ProductTypeViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.product_type_item, null);
            viewHolder.mImageProductTypeImageView = view.findViewById(R.id.image_view_product_type);
            viewHolder.mNameProductTypeTextView = view.findViewById(R.id.text_view_product_type);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ProductTypeViewHolder) view.getTag();
        }
        ProductType productType = productTypes.get(i);
        viewHolder.mNameProductTypeTextView.setText(productType.getName());
        Bitmap bitmap = BitmapFactory.decodeByteArray(productType.getImage(), 0, productType.getImage().length);
        viewHolder.mImageProductTypeImageView.setImageBitmap(bitmap);
        viewHolder.mImageProductTypeImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }

    public class ProductTypeViewHolder {
        public TextView mNameProductTypeTextView;
        public ImageView mImageProductTypeImageView;
    }
}
