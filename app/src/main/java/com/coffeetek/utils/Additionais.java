package com.coffeetek.utils;

import com.coffeetek.models.ProductsModel;

import java.util.ArrayList;
import java.util.List;

public class Additionais {

    public Additionais() {}

    public List<String> itens (ProductsModel productsModel){
        List<String> list = new ArrayList<>();
        list.clear();
        String[] add = productsModel.getAdditional().replaceAll("\"", "").replace("[", "").replace("]", "").split(",");
        for(String s : add){
            list.add(s);
        }
        return list;
    }
}
