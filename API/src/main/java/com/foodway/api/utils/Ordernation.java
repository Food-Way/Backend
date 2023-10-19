package com.foodway.api.utils;

import com.foodway.api.model.EEntity;
import com.foodway.api.model.Establishment;

public class Ordernation {

    public static <T> ListaObj<T> filterBySome(ListaObj<T> list, String filter, EEntity eEntity) {
        switch (filter.toUpperCase() + eEntity) {
            case "RATEESTABLISHMENT" :
                if (list.getTamanho() > 0) {
                    for (int i = 0; i < list.getTamanho() - 1; i++) {
                        for (int j = 1; j < list.getTamanho() - i; j++) {
                            Establishment first = (Establishment) list.getElemento(j - 1);
                            Establishment next = (Establishment) list.getElemento(j);

                            if(next.getRate() > first.getRate()){
                                list.adicionaNoIndice(j - 1, (T) next);
                                list.adicionaNoIndice(j, (T) first);
                            }
                        }
                    }
                    return list;
                }
                break;

            default:
                return list;
        }
        return null;
    }

//    public static void ordernation(ListaObj list) {
//
//    }


}

