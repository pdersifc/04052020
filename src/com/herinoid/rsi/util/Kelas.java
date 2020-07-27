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
public class Kelas {
    public static final String KELAS_SATU ="Kelas 1";
    public static final String KELAS_DUA ="Kelas 2";
    public static final String KELAS_TIGA ="Kelas 3";
    public static final String KELAS_VIP ="Kelas VIP";
    public static final String KELAS_VVIP ="Kelas VVIP";
    
    public static boolean isNaik(String kelasNow,String kelasPembanding){
        boolean naik = false;
        if(kelasNow.equals(KELAS_TIGA)){
            if(kelasPembanding.equals(KELAS_DUA)){
                naik = true;
            }else if(kelasPembanding.equals(KELAS_SATU)){
                naik = true;
            }else if(kelasPembanding.equals(KELAS_VIP)){
                naik = true;
            }else if(kelasPembanding.equals(KELAS_VVIP)){
                naik = true;
            }
        }else if(kelasNow.equals(KELAS_DUA)){
            if(kelasPembanding.equals(KELAS_SATU)){
                naik = true;
            }else if(kelasPembanding.equals(KELAS_VIP)){
                naik = true;
            }else if(kelasPembanding.equals(KELAS_VVIP)){
                naik = true;
            }
        }else if(kelasNow.equals(KELAS_SATU)){
            if(kelasPembanding.equals(KELAS_VIP)){
                naik = true;
            }else if(kelasPembanding.equals(KELAS_VVIP)){
                naik = true;
            }
        }else if(kelasNow.equals(KELAS_VIP)){
            if(kelasPembanding.equals(KELAS_VVIP)){
                naik = true;
            }
        }
        
        return naik;
    }
    
    public static String getKelasAtasnya(String kelas){
        String kelasAtasnya = "";
        if(kelas.equals(KELAS_TIGA)){
            kelasAtasnya = KELAS_DUA;
        }else if(kelas.equals(KELAS_DUA)){
            kelasAtasnya = KELAS_SATU;
        }else if(kelas.equals(KELAS_SATU)){
            kelasAtasnya = KELAS_VIP;
        }else if(kelas.equals(KELAS_VIP)){
            kelasAtasnya = KELAS_VVIP;
        }else if(kelas.equals(KELAS_VVIP)){
            kelasAtasnya = KELAS_VVIP;
        }
        
        return kelasAtasnya;
    }
    
    public static String getKelasBawahnya(String kelas){
        String kelasBawahnya = "";
        if(kelas.equals(KELAS_TIGA)){
            kelasBawahnya = KELAS_TIGA;
        }else if(kelas.equals(KELAS_DUA)){
            kelasBawahnya = KELAS_TIGA;
        }else if(kelas.equals(KELAS_SATU)){
            kelasBawahnya = KELAS_DUA;
        }else if(kelas.equals(KELAS_VIP)){
            kelasBawahnya = KELAS_SATU;
        }else if(kelas.equals(KELAS_VVIP)){
            kelasBawahnya = KELAS_VIP;
        }        
        return kelasBawahnya;
    }
    
}
