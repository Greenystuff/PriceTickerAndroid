package com.greedythings.priceticker.Utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.greedythings.priceticker.R;
import com.greedythings.priceticker.Models.ProductData;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    private List<ProductData> products;

    public ProductsAdapter(List<ProductData> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_added, parent, false);
        return new ProductsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        holder.bind(products.get(position));
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductsViewHolder extends RecyclerView.ViewHolder {

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(ProductData products) {
            if(products.getPrix().equals("N/A")) {
                ((TextView) itemView.findViewById(R.id.txtId)).setTextColor(Color.RED);
                ((TextView) itemView.findViewById(R.id.txtLibelle)).setTextColor(Color.RED);
                ((TextView) itemView.findViewById(R.id.txtPrix)).setTextColor(Color.RED);
            }else {
                ((TextView) itemView.findViewById(R.id.txtId)).setTextColor(Color.BLACK);
                ((TextView) itemView.findViewById(R.id.txtLibelle)).setTextColor(Color.BLACK);
                ((TextView) itemView.findViewById(R.id.txtPrix)).setTextColor(Color.BLACK);
            }
            ((TextView) itemView.findViewById(R.id.txtId)).setText(products.getId());
            ((TextView) itemView.findViewById(R.id.txtLibelle)).setText(products.getLibelle());
            ((TextView) itemView.findViewById(R.id.txtPrix)).setText(products.getPrix());
        }
    }
}
