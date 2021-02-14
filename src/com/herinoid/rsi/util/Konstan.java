/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.util;

/**
 *
 * @author Hewrei
 */
public class Konstan {
    public static final String PASIEN_RALAN="RALAN";
    public static final String PASIEN_KARYAWAN="KAYRAWAN";
    public static final String PASIEN_KELAS_VVIP="KELAS_VVIP";
    public static final String PASIEN_KELAS_VIP="KELAS_VIP";
    public static final String PASIEN_KELAS1="KELAS SATU";
    public static final String PASIEN_KELAS2="KELAS_DUA";
    public static final String PASIEN_KELAS3="KELAS_TIGA";
    public static final String PASIEN_BELILUAR="BELILUAR";
    public static final String PASIEN_RANAP="RANAP";
    
    public static final String PASIEN_BPJS_KESEHATAN="BPJS KESEHATAN";
    public static final String PASIEN_UMUM="UMUM";
    
   public static final String KLINIK_JANTUNG = "JANTUNG";
    public static final String KLINIK_DALAM = "DALAM";
    public static final String KLINIk_GIGI = "GIGI";
    public static final String KLINIK_SARAF = "SARAF";
    public static final String KLINIK_BEDAH = "BEDAH";
    public static  final String KLINIK_PARU = "PARU";
    public static  final String KLINIK_ORTHOPEDI = "ORTHOPEDI";
    public static  final String KLINIK_OBSGYN = "OBGIN";
    public static  final String KLINIK_ANAK = "ANAK";
    public static  final String KLINIK_UROLOGI = "UROLOGI";
    public static  final String KLINIK_MATA = "MATA";
    public static  final String KLINIK_AKUPUNTUR = "AKUPUNTUR";
    public static  final String KLINIK_FISIOTERAPI = "FISIO";
    public static  final String KLINIK_GIZI = "GIZI";
    public static  final String KLINIK_JIWA = "JIWA";
    public static  final String KLINIK_UMUM = "UMUM";
    public static  final String KLINIK_DOTS = "DOTS";
    public static  final String KLINIK_THT = "THT";
    public static  final String INSTALASI_GAWAT_DARURAT = "IGD";
    public static  final String LAYANAN_HEMODIALISA = "HEMODIALISA";
    public static  final String RAWAT_INAP = "RANAP";
    
    public static String getPoli(String poli){
        if(poli.equalsIgnoreCase("U001")){
            return KLINIK_UMUM;
        }else if(poli.equalsIgnoreCase("U002")){
            return KLINIK_ANAK;
        }else if(poli.equalsIgnoreCase("U003")){
            return KLINIK_AKUPUNTUR;
        }else if(poli.equalsIgnoreCase("U004")){
            return KLINIK_BEDAH;
        }else if(poli.equalsIgnoreCase("U005")){
            return KLINIk_GIGI;
        }else if(poli.equalsIgnoreCase("U006")){
            return KLINIk_GIGI;
        }else if(poli.equalsIgnoreCase("U007")){
            return KLINIK_JANTUNG;
        }else if(poli.equalsIgnoreCase("U008")){
            return KLINIK_JIWA;
        }else if(poli.equalsIgnoreCase("U009")){
            return KLINIK_MATA;
        }else if(poli.equalsIgnoreCase("U010")){
            return KLINIK_OBSGYN;
        }else if(poli.equalsIgnoreCase("U011")){
            return KLINIK_ORTHOPEDI;
        }else if(poli.equalsIgnoreCase("U012")){
            return KLINIK_PARU;
        }else if(poli.equalsIgnoreCase("U013")){
            return KLINIK_DALAM;
        }else if(poli.equalsIgnoreCase("U014")){
            return KLINIK_THT;
        }else if(poli.equalsIgnoreCase("U015")){
            return KLINIK_SARAF;
        }else if(poli.equalsIgnoreCase("U016")){
            return KLINIK_UROLOGI;
        }else if(poli.equalsIgnoreCase("U017")){
            return KLINIK_ANAK;
        }else if(poli.equalsIgnoreCase("U018")){
            return KLINIK_GIZI;
        }else if(poli.equalsIgnoreCase("HEM")){
            return LAYANAN_HEMODIALISA;
        }else if(poli.equalsIgnoreCase("U020")){
            return KLINIK_ANAK;
        }else if(poli.equalsIgnoreCase("IGDK")){
            return INSTALASI_GAWAT_DARURAT;
        }
        
        return null; 
    }
}
