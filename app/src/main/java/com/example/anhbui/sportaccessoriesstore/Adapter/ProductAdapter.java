package com.example.anhbui.sportaccessoriesstore.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhbui.sportaccessoriesstore.Model.Product;
import com.example.anhbui.sportaccessoriesstore.R;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> gloveses) {
        this.context = context;
        this.products = gloveses;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return products.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ProductViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ProductViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.product_item, null);
            viewHolder.imageView = view.findViewById(R.id.image_view_product);
            viewHolder.name = view.findViewById(R.id.text_view_product_name);
            viewHolder.cost = view.findViewById(R.id.text_view_product_cost);
            viewHolder.description = view.findViewById(R.id.text_view_product_description);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ProductViewHolder) view.getTag();
        }
        Product gloves = products.get(i);
        viewHolder.name.setText(gloves.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.cost.setText("Giá: " + decimalFormat.format(gloves.getCost()) + " vnđ");
        viewHolder.description.setMaxLines(2);
        viewHolder.description.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.description.setText(gloves.getDescription());
        Bitmap bitmap = BitmapFactory.decodeByteArray(gloves.getImage(), 0, gloves.getImage().length);
        viewHolder.imageView.setImageBitmap(bitmap);
        viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }

    public class ProductViewHolder {
        ImageView imageView;
        TextView name, cost, description;
    }
}
