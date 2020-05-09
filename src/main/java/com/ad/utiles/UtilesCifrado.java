package com.ad.utiles;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * UtilesCifrado
 */
public class UtilesCifrado {
    
    /**
     * Cifrar contraseña en MD5
     * @param cadena
     * @return 
     */
    public static String cifrarEnMd5 (final String cadena) {
        
        //Cifrar en MD5
        return DigestUtils.md5Hex(cadena);
        
    }
    
}
