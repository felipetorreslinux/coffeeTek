package com.coffeetek.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coffeetek.R;
import com.coffeetek.models.ProductsModel;
import com.coffeetek.utils.Images;
import com.coffeetek.views.ViewDetailProduct;

import java.util.List;

public class AdapterListProducts extends RecyclerView.Adapter<AdapterListProducts.ProductsHolder> {

    Context context;
    List<ProductsModel> lista;

    public AdapterListProducts(Context context, List<ProductsModel> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ProductsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_products, viewGroup, false);
        return new ProductsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsHolder holder, int i) {
        final ProductsModel itens = lista.get(i);

        holder.imageProd.setImageBitmap(Images.imageUrl(itens.getImage()));
        holder.textTitleProd.setText(itens.getTitle());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewDetailProduct.class);
                intent.putExtra("product", itens);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    public class ProductsHolder extends RecyclerView.ViewHolder{

        LinearLayout item;
        ImageView imageProd;
        TextView textTitleProd;

        public ProductsHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
            imageProd = itemView.findViewById(R.id.imageProd);
            textTitleProd = itemView.findViewById(R.id.textTitleProd);
        }
    }
}
