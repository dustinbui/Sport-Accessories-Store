package com.example.anhbui.sportaccessoriesstore.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhbui.sportaccessoriesstore.Activity.CartActivity;
import com.example.anhbui.sportaccessoriesstore.Activity.MainActivity;
import com.example.anhbui.sportaccessoriesstore.Model.Cart;
import com.example.anhbui.sportaccessoriesstore.R;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends BaseAdapter {

    Context context;
    List<Cart> carts;

    public CartAdapter(Context context, List<Cart> carts) {
        this.context = context;
        this.carts = carts;
    }

    @Override
    public int getCount() {
        return carts.size();
    }

    @Override
    public Object getItem(int i) {
        return carts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return carts.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final CartViewHolder viewHolder;
        if (view == null) {
            viewHolder = new CartViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cart_item, null);
            viewHolder.costTextView = view.findViewById(R.id.text_view_cost_cart);
            viewHolder.imageView = view.findViewById(R.id.image_view_cart);
            viewHolder.nameTextView = view.findViewById(R.id.text_view_name_cart);
            viewHolder.minusButton = view.findViewById(R.id.button_minus);
            viewHolder.plusButton = view.findViewById(R.id.button_plus);
            viewHolder.valueButton = view.findViewById(R.id.button_value);
            view.setTag(viewHolder);
        } else {
            viewHolder = (CartViewHolder) view.getTag();
        }
        Cart cart = carts.get(i);
        viewHolder.nameTextView.setText(cart.getName());
        final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.costTextView.setText(decimalFormat.format(cart.getCost()) + " vnđ");
        viewHolder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(cart.getImage(), 0, cart.getImage().length));
        viewHolder.valueButton.setText(cart.getCountProduct() + "");
        int count = Integer.parseInt(viewHolder.valueButton.getText().toString());
        if (count == 10) {
            viewHolder.plusButton.setVisibility(View.INVISIBLE);
            viewHolder.minusButton.setVisibility(View.VISIBLE);
        } else if (count == 1) {
            viewHolder.plusButton.setVisibility(View.VISIBLE);
            viewHolder.minusButton.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.plusButton.setVisibility(View.VISIBLE);
            viewHolder.minusButton.setVisibility(View.VISIBLE);
        }
        viewHolder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newCount = Integer.parseInt(viewHolder.valueButton.getText().toString()) + 1;
                int currentCount = MainActivity.carts.get(i).getCountProduct();
                int currentCost = MainActivity.carts.get(i).getCost();
                MainActivity.carts.get(i).setCountProduct(newCount);
                int newCost = (currentCost / currentCount) * newCount;
                MainActivity.carts.get(i).setCost(newCost);
                viewHolder.costTextView.setText(decimalFormat.format(newCost) + " vnđ");
                CartActivity.evenUltil();
                if (newCount > 9) {
                    viewHolder.plusButton.setVisibility(View.INVISIBLE);
                    viewHolder.minusButton.setVisibility(View.VISIBLE);
                    viewHolder.valueButton.setText(newCount + "");
                } else {
                    viewHolder.plusButton.setVisibility(View.VISIBLE);
                    viewHolder.minusButton.setVisibility(View.VISIBLE);
                    viewHolder.valueButton.setText(newCount + "");
                }
            }
        });
        viewHolder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newCount = Integer.parseInt(viewHolder.valueButton.getText().toString()) - 1;
                int currentCount = MainActivity.carts.get(i).getCountProduct();
                int currentCost = MainActivity.carts.get(i).getCost();
                MainActivity.carts.get(i).setCountProduct(newCount);
                int newCost = (currentCost / currentCount) * newCount;
                MainActivity.carts.get(i).setCost(newCost);
                viewHolder.costTextView.setText(decimalFormat.format(newCost) + " vnđ");
                CartActivity.evenUltil();
                if (newCount < 2) {
                    viewHolder.plusButton.setVisibility(View.VISIBLE);
                    viewHolder.minusButton.setVisibility(View.INVISIBLE);
                    viewHolder.valueButton.setText(newCount + "");
                } else {
                    viewHolder.plusButton.setVisibility(View.VISIBLE);
                    viewHolder.minusButton.setVisibility(View.VISIBLE);
                    viewHolder.valueButton.setText(newCount + "");
                }
            }
        });
        return view;
    }

    public class CartViewHolder {
        ImageView imageView;
        TextView nameTextView, costTextView;
        Button minusButton, plusButton, valueButton;
    }
}
