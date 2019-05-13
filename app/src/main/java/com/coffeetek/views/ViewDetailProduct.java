package com.coffeetek.views;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coffeetek.R;
import com.coffeetek.models.ProductsModel;
import com.coffeetek.utils.Images;
import com.coffeetek.utils.Quantity;

public class ViewDetailProduct extends AppCompatActivity implements View.OnClickListener {

    ProductsModel productsModel;

    Toolbar toolbar;
    ImageView imageProd;
    TextView textNameProd;
    TextView textMLProd;

    ImageView imageCupP;
    ImageView imageCupM;
    ImageView imageCupG;

    ImageView imageSugar0;
    ImageView imageSugar1;
    ImageView imageSugar2;
    ImageView imageSugar3;

    ImageView btnAddQtd;
    TextView textQtdProd;
    ImageView btnRemoveQtd;

    Button btnAddCart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        this.productsModel = (ProductsModel) getIntent().getExtras().getSerializable("product");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back));

        imageProd = findViewById(R.id.imageProd);
        textNameProd = findViewById(R.id.textNameProd);
        textMLProd = findViewById(R.id.textMLProd);

        imageCupP = findViewById(R.id.imageCupP);
        imageCupP.setOnClickListener(this);
        imageCupM = findViewById(R.id.imageCupM);
        imageCupM.setOnClickListener(this);
        imageCupG = findViewById(R.id.imageCupG);
        imageCupG.setOnClickListener(this);

        imageSugar0 = findViewById(R.id.imageSugar0);
        imageSugar0.setOnClickListener(this);
        imageSugar1 = findViewById(R.id.imageSugar1);
        imageSugar1.setOnClickListener(this);
        imageSugar2 = findViewById(R.id.imageSugar2);
        imageSugar2.setOnClickListener(this);
        imageSugar3 = findViewById(R.id.imageSugar3);
        imageSugar3.setOnClickListener(this);

        btnAddQtd = findViewById(R.id.btnAddQtd);
        btnAddQtd.setOnClickListener(this);
        textQtdProd = findViewById(R.id.textQtdProd);
        btnRemoveQtd = findViewById(R.id.btnRemoveQtd);
        btnRemoveQtd.setOnClickListener(this);

        btnAddCart = findViewById(R.id.btnAddCart);
        btnAddCart.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        imageProd.setImageBitmap(Images.imageUrl(productsModel.getImage()));
        textNameProd.setText(productsModel.getTitle());
        textMLProd.setText(Quantity.qtdMl(productsModel.getSize()));
        cupSize(productsModel.getSize());
        selectedSugar(productsModel.getSugar());
        textQtdProd.setText(Integer.toString(productsModel.getQuantity()));

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

    public void cupSize(int size){
        switch (size){
            case 1:
                imageCupP.setImageDrawable(getResources().getDrawable(R.drawable.cup_select));
                imageCupM.setImageDrawable(getResources().getDrawable(R.drawable.cup_unselect));
                imageCupG.setImageDrawable(getResources().getDrawable(R.drawable.cup_unselect));
                break;
            case 2:
                imageCupP.setImageDrawable(getResources().getDrawable(R.drawable.cup_unselect));
                imageCupM.setImageDrawable(getResources().getDrawable(R.drawable.cup_select));
                imageCupG.setImageDrawable(getResources().getDrawable(R.drawable.cup_unselect));
                break;
            case 3:
                imageCupP.setImageDrawable(getResources().getDrawable(R.drawable.cup_unselect));
                imageCupM.setImageDrawable(getResources().getDrawable(R.drawable.cup_unselect));
                imageCupG.setImageDrawable(getResources().getDrawable(R.drawable.cup_select));
                break;
            default:
                cupSize(1);
                break;
        }
    }

    public void selectedCup(ImageView view){
        imageCupP.setImageDrawable(getResources().getDrawable(R.drawable.cup_unselect));
        imageCupM.setImageDrawable(getResources().getDrawable(R.drawable.cup_unselect));
        imageCupG.setImageDrawable(getResources().getDrawable(R.drawable.cup_unselect));
        view.setImageDrawable(getResources().getDrawable(R.drawable.cup_select));
    }

    public void selectedSugar(int sugar){
        switch (sugar){
            case 0:
                imageSugar0.setImageDrawable(getResources().getDrawable(R.drawable.no_sugar));
                imageSugar1.setImageDrawable(getResources().getDrawable(R.drawable.sugar_unselect));
                imageSugar2.setImageDrawable(getResources().getDrawable(R.drawable.sugar_two_unselect));
                imageSugar3.setImageDrawable(getResources().getDrawable(R.drawable.sugar_three_unselect));
                break;
            case 1:
                imageSugar0.setImageDrawable(getResources().getDrawable(R.drawable.no_sugar_unselect));
                imageSugar1.setImageDrawable(getResources().getDrawable(R.drawable.sugar1));
                imageSugar2.setImageDrawable(getResources().getDrawable(R.drawable.sugar_two_unselect));
                imageSugar3.setImageDrawable(getResources().getDrawable(R.drawable.sugar_three_unselect));
                break;
            case 2:
                imageSugar0.setImageDrawable(getResources().getDrawable(R.drawable.no_sugar_unselect));
                imageSugar1.setImageDrawable(getResources().getDrawable(R.drawable.sugar_unselect));
                imageSugar2.setImageDrawable(getResources().getDrawable(R.drawable.sugar2));
                imageSugar3.setImageDrawable(getResources().getDrawable(R.drawable.sugar_three_unselect));
                break;
            case 3:
                imageSugar0.setImageDrawable(getResources().getDrawable(R.drawable.no_sugar_unselect));
                imageSugar1.setImageDrawable(getResources().getDrawable(R.drawable.sugar_unselect));
                imageSugar2.setImageDrawable(getResources().getDrawable(R.drawable.sugar_two_unselect));
                imageSugar3.setImageDrawable(getResources().getDrawable(R.drawable.sugar3));
                break;
            default:
                selectedSugar(0);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageCupP:
                selectedCup(imageCupP);
                productsModel.setSize(1);
                textMLProd.setText(Quantity.qtdMl(1));
                break;
            case R.id.imageCupM:
                selectedCup(imageCupM);
                productsModel.setSize(2);
                textMLProd.setText(Quantity.qtdMl(2));
                break;
            case R.id.imageCupG:
                selectedCup(imageCupG);
                productsModel.setSize(3);
                textMLProd.setText(Quantity.qtdMl(3));
                break;

            case R.id.imageSugar0:
                selectedSugar(0);
                productsModel.setSugar(0);
                break;
            case R.id.imageSugar1:
                selectedSugar(1);
                productsModel.setSugar(1);
                break;
            case R.id.imageSugar2:
                selectedSugar(2);
                productsModel.setSugar(2);
                break;
            case R.id.imageSugar3:
                selectedSugar(3);
                productsModel.setSugar(3);
                break;

            case R.id.btnAddQtd:
                productsModel.setQuantity(productsModel.getQuantity() + 1);
                textQtdProd.setText(Integer.toString(productsModel.getQuantity()));
                break;
            case R.id.btnRemoveQtd:
                productsModel.setQuantity(productsModel.getQuantity() <= 1 ? 1 : productsModel.getQuantity() - 1);
                textQtdProd.setText(Integer.toString(productsModel.getQuantity()));
                break;

            case R.id.btnAddCart:

                break;
        }
    }
}
