package com.coffeetek.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.coffeetek.R;
import com.coffeetek.adapters.AdapterListProducts;
import com.coffeetek.api.Server;
import com.coffeetek.models.ProductsModel;
import com.coffeetek.sqlite.SQLiCart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewListProducts extends AppCompatActivity implements View.OnClickListener {

    Context context;

    Toolbar toolbar;
    TextView countCart;
    TextView textInfoCart;

    RelativeLayout layoutLoad;
    TextView textInfoProducts;

    SwipeRefreshLayout swiper;
    RecyclerView recycler;
    List<ProductsModel> list_products;
    AdapterListProducts adapterListProducts;

    SQLiCart sqLiCart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);
        this.context = this;
        this.sqLiCart = new SQLiCart(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        layoutLoad = findViewById(R.id.layoutLoad);
        textInfoProducts = findViewById(R.id.textInfoProducts);

        swiper = findViewById(R.id.swiper);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.VERTICAL));
        recycler.setNestedScrollingEnabled(false);

        textInfoCart = findViewById(R.id.textInfoCart);
        textInfoCart.setOnClickListener(this);

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ListProducts();
            }
        });

        ListProducts();

    }

    @Override
    protected void onStart() {
        super.onStart();
        textInfoCart.setVisibility(sqLiCart.count() > 0 ? View.VISIBLE : View.GONE);
        textInfoCart.setText(getString(R.string.info_cart_count, sqLiCart.count(), sqLiCart.count() > 1 ? getString(R.string.label_itens) : getString(R.string.label_item)));
        if(countCart != null){
            countCart.setVisibility(sqLiCart.count() > 0 ? View.VISIBLE : View.GONE);
            countCart.setText(Integer.toString(sqLiCart.count()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_products, menu);

        View view = menu.findItem(R.id.cart_view).getActionView();
        countCart = view.findViewById(R.id.textCountCart);
        countCart.setVisibility(sqLiCart.count() > 0 ? View.VISIBLE : View.GONE);
        countCart.setText(Integer.toString(sqLiCart.count()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ViewCart.class));
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AndroidNetworking.cancel("ListProducts");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textInfoCart:
                startActivity(new Intent(context, ViewCart.class));
                break;
        }
    }

    public void ListProducts(){

        layoutLoad.setVisibility(list_products != null ? list_products.size() > 0 ? View.GONE : View.VISIBLE : View.VISIBLE);
        textInfoProducts.setVisibility(View.GONE);
        swiper.setVisibility(list_products != null ? list_products.size() > 0 ? View.VISIBLE : View.GONE : View.GONE);

        list_products = new ArrayList<ProductsModel>();
        list_products.clear();

        AndroidNetworking.get(Server.produtos())
                .setTag("ListProducts")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        swiper.setRefreshing(false);
                        try{
                            JSONArray products = response.getJSONArray("products");
                            if(products.length() > 0){
                                for(int i = 0; i < products.length(); i++){
                                    JSONObject jsonObject = products.getJSONObject(i);
                                    ProductsModel productsModel = new ProductsModel();
                                    productsModel.setTitle(jsonObject.getString("title"));
                                    productsModel.setImage(jsonObject.getString("image"));
                                    productsModel.setSize(jsonObject.getInt("size"));
                                    productsModel.setSugar(jsonObject.getInt("sugar"));
                                    productsModel.setAdditional(jsonObject.has("additional") ? jsonObject.getString("additional") : "");
                                    productsModel.setQuantity(1);
                                    list_products.add(productsModel);
                                }
                                adapterListProducts = new AdapterListProducts(context, list_products);
                                recycler.setAdapter(adapterListProducts);
                                layoutLoad.setVisibility(View.GONE);
                                textInfoProducts.setVisibility(View.GONE);
                                swiper.setVisibility(View.VISIBLE);

                            }else{
                                layoutLoad.setVisibility(View.GONE);
                                swiper.setVisibility(View.GONE);
                                textInfoProducts.setVisibility(View.VISIBLE);
                                textInfoProducts.setText(getString(R.string.not_item_products));
                            }
                        }catch (JSONException e){
                            swiper.setRefreshing(false);
                            layoutLoad.setVisibility(View.GONE);
                            swiper.setVisibility(View.GONE);
                            textInfoProducts.setVisibility(View.VISIBLE);
                            textInfoProducts.setText(getString(R.string.error_server));
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        swiper.setRefreshing(false);
                        layoutLoad.setVisibility(View.GONE);
                        swiper.setVisibility(View.GONE);
                        textInfoProducts.setVisibility(View.VISIBLE);
                        textInfoProducts.setText(getString(R.string.error_server));
                    }
                });
    }
}
