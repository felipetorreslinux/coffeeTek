package com.coffeetek.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coffeetek.R;
import com.coffeetek.models.ProductsModel;
import com.coffeetek.sqlite.SQLiCart;
import com.coffeetek.utils.Alert;
import com.coffeetek.utils.Images;
import com.coffeetek.utils.Quantity;
import com.coffeetek.utils.ShareUtils;
import com.daimajia.swipe.SwipeLayout;

import java.util.List;

public class ViewCart extends AppCompatActivity implements View.OnClickListener {

    Alert alert;
    Context context;
    Toolbar toolbar;

    TextView textInfoCart;
    RecyclerView recycler;
    TextView textBtnPayCart;
    AdapterCart adapterCart;
    SQLiCart sqLiCart;

    ListCart listCart;
    ShareUtils shareUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        this.context = this;
        this.alert = new Alert(this);
        this.sqLiCart = new SQLiCart(this);
        this.shareUtils = new ShareUtils(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back));

        textInfoCart = findViewById(R.id.textInfoCart);
        textBtnPayCart = findViewById(R.id.textBtnPayCart);
        textBtnPayCart.setText(getString(R.string.label_text_pay_cart));
        textBtnPayCart.setOnClickListener(this);

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setNestedScrollingEnabled(false);

        listCart = new ListCart();
        listCart.execute();

        if(!shareUtils.isViewCart() && sqLiCart.list().size() > 0){
            alert.viewCart(getString(R.string.message_view_cart));
            shareUtils.setViewCart(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textBtnPayCart:
                payCart();
                break;
        }
    }

    private class ListCart extends AsyncTask<String, Void, List<ProductsModel>>{

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected List<ProductsModel> doInBackground(String... strings) {
            return sqLiCart.list();
        }

        @Override
        protected void onPostExecute(List<ProductsModel> list) {
            textInfoCart.setVisibility(list != null ? list.size() > 0 ? View.GONE : View.VISIBLE : View.VISIBLE);
            textBtnPayCart.setVisibility(list != null ? list.size() > 0 ? View.VISIBLE : View.GONE : View.GONE);
            if(list.size() > 0){
                adapterCart = new AdapterCart(context, list);
                recycler.setAdapter(adapterCart);
            }
        }
    }

    public class AdapterCart extends RecyclerView.Adapter<AdapterCart.CartHolder> {

        Context context;
        List<ProductsModel> list;
        SQLiCart sqLiCart;

        public AdapterCart(Context context, List<ProductsModel> list) {
            this.context = context;
            this.list = list;
            this.sqLiCart = new SQLiCart(context);
        }

        @NonNull
        @Override
        public AdapterCart.CartHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_cart, viewGroup, false);
            return new AdapterCart.CartHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final CartHolder holder, final int i) {
            final ProductsModel itens = list.get(i);

            holder.imageProd.setImageBitmap(Images.imageUrl(itens.getImage()));
            holder.textNameProd.setText(itens.getTitle());
            holder.textMLProd.setText(Quantity.qtdMl(itens.getSize()));
            holder.textSugarProd.setText(sugarProd(itens));


            holder.btnRemoveQtd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itens.getQuantity() == 1){holder.swipeLayout.open();}
                    itens.setQuantity(itens.getQuantity() <= 1 ? 1 : itens.getQuantity() - 1);
                    holder.textQtdProd.setText(Integer.toString(itens.getQuantity()));
                    sqLiCart.updateQtdItemCart(itens);
                    notifyDataSetChanged();
                }
            });

            holder.textQtdProd.setText(Integer.toString(itens.getQuantity()));

            holder.btnAddQtd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itens.setQuantity(itens.getQuantity() + 1);
                    holder.textQtdProd.setText(Integer.toString(itens.getQuantity()));
                    sqLiCart.updateQtdItemCart(itens);
                    notifyDataSetChanged();
                }
            });

            holder.layoutBtnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.swipeLayout.close();
                    alertRemoveItem(itens, i);
                }
            });
        }

        @Override
        public int getItemCount() {
            textInfoCart.setVisibility(list != null ? list.size() > 0 ? View.GONE : View.VISIBLE : View.VISIBLE);
            textBtnPayCart.setVisibility(list != null ? list.size() > 0 ? View.VISIBLE : View.GONE : View.GONE);
            return list != null ? list.size() : 0;
        }

        public class CartHolder extends RecyclerView.ViewHolder{

            SwipeLayout swipeLayout;
            LinearLayout layoutBtnRemove;

            ImageView imageProd;
            TextView textNameProd;
            TextView textMLProd;
            TextView textSugarProd;

            ImageView btnRemoveQtd;
            TextView textQtdProd;
            ImageView btnAddQtd;

            public CartHolder(@NonNull View itemView) {
                super(itemView);

                swipeLayout = itemView.findViewById(R.id.swipeLayout);
                layoutBtnRemove = itemView.findViewById(R.id.layoutBtnRemove);

                imageProd = itemView.findViewById(R.id.imageProd);
                textNameProd = itemView.findViewById(R.id.textNameProd);
                textMLProd = itemView.findViewById(R.id.textMLProd);
                textSugarProd = itemView.findViewById(R.id.textSugarProd);

                btnRemoveQtd = itemView.findViewById(R.id.btnRemoveQtd);
                textQtdProd = itemView.findViewById(R.id.textQtdProd);
                btnAddQtd = itemView.findViewById(R.id.btnAddQtd);

            }
        }

        public String sugarProd(ProductsModel productsModel){
            switch (productsModel.getSugar()){
                case 0:
                    return getString(R.string.sugar0);
                case 1:
                    return getString(R.string.sugar1);
                case 2:
                    return getString(R.string.sugar2);
                case 3:
                    return getString(R.string.sugar3);
                default:
                    return getString(R.string.sugar1);
            }
        }

        public AlertDialog alertRemoveItem(final ProductsModel productsModel, final int position){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getString(R.string.title_alert_remove));
            builder.setMessage(context.getString(R.string.message_alert_remove, productsModel.getTitle()));
            builder.setCancelable(false);
            builder.setPositiveButton(context.getString(R.string.positive_alert), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    sqLiCart.removeItemCart(productsModel);
                    list.remove(productsModel);
                    notifyItemRemoved(position);
                    alert.openBottom(getString(R.string.text_message_remove_sucess, productsModel.getTitle()));
                }
            });
            builder.setNegativeButton(context.getString(R.string.negative_alert), null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return alertDialog;
        }
    }

    public AlertDialog payCart(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_pay_cart));
        builder.setMessage(getString(R.string.message_pay_cart));
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.positive_alert), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                sqLiCart.removeAllItensCart();
                alert.openBottom(getString(R.string.message_finnaly_cart));
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.negative_alert), null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

}

