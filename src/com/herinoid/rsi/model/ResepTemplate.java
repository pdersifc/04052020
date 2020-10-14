/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.model;

import com.herinoid.rsi.dao.ResepTemplateDao;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

/**
 *
 * @author Hewrei
 */
public class ResepTemplate {
    private String code;
    private String kdDokter;
    private String namaDokter;
    private String kdJaminan;
    private String namaTemplate;
    private boolean racikan;
    private List<ObatResep> obatTemplateDetail;
    private List<ObatResep> obatTemplateRacikanDetail;
    
    public ResepTemplate(){}

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the kdDokter
     */
    public String getKdDokter() {
        return kdDokter;
    }

    /**
     * @param kdDokter the kdDokter to set
     */
    public void setKdDokter(String kdDokter) {
        this.kdDokter = kdDokter;
    }

    /**
     * @return the namaTemplate
     */
    public String getNamaTemplate() {
        return namaTemplate;
    }

    /**
     * @param namaTemplate the namaTemplate to set
     */
    public void setNamaTemplate(String namaTemplate) {
        this.namaTemplate = namaTemplate;
    }

    /**
     * @return the racikan
     */
    public boolean isRacikan() {
        return racikan;
    }

    /**
     * @param racikan the racikan to set
     */
    public void setRacikan(boolean racikan) {
        this.racikan = racikan;
    }

    /**
     * @return the obatTemplateDetail
     */
    public List<ObatResep> getObatTemplateDetail() {
        return obatTemplateDetail;
    }

    /**
     * @param obatTemplateDetail the obatTemplateDetail to set
     */
    public void setObatTemplateDetail(List<ObatResep> obatTemplateDetail) {
        this.obatTemplateDetail = obatTemplateDetail;
    }

    /**
     * @return the obatTemplateRacikanDetail
     */
    public List<ObatResep> getObatTemplateRacikanDetail() {
        return obatTemplateRacikanDetail;
    }

    /**
     * @param obatTemplateRacikanDetail the obatTemplateRacikanDetail to set
     */
    public void setObatTemplateRacikanDetail(List<ObatResep> obatTemplateRacikanDetail) {
        this.obatTemplateRacikanDetail = obatTemplateRacikanDetail;
    }

    /**
     * @return the namaDokter
     */
    public String getNamaDokter() {
        return namaDokter;
    }

    /**
     * @param namaDokter the namaDokter to set
     */
    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    /**
     * @return the kdJaminan
     */
    public String getKdJaminan() {
        return kdJaminan;
    }

    /**
     * @param kdJaminan the kdJaminan to set
     */
    public void setKdJaminan(String kdJaminan) {
        this.kdJaminan = kdJaminan;
    }
    
}
