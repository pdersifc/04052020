/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.model.api;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author HEWRIE
 */
public class CreateObatDetailRequest extends BaseRequest implements Serializable {

    private List<SingleDetailPemberianObat> detailPemberianObats;

    public CreateObatDetailRequest(){}

    public List<SingleDetailPemberianObat> getDetailPemberianObats() {
        return detailPemberianObats;
    }

    public void setDetailPemberianObats(List<SingleDetailPemberianObat> detailPemberianObats) {
        this.detailPemberianObats = detailPemberianObats;
    }
}
