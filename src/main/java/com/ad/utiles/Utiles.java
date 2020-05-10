package com.ad.utiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * Utiles
 */
public class Utiles {
    
    private static final String ESPACIO = " ";
    private static final String CARACTER_HASHTAG = "#";
    
    /**
     * Buscar Hashtags en Mensaje
     * @param texto
     * @return 
     */
    public static List<String> buscarHashtagEnMensaxe (final String texto) {
    
        List<String> listaHashtag = new ArrayList<>();
        
        //Separar palabras
        List<String> listaTexto = 
                Arrays.asList(texto.split(ESPACIO));
        
        for (String cadena: listaTexto) {
            //Comprobar si la cadena es un hashtag
            if (cadena.startsWith(CARACTER_HASHTAG)) {
                //Añadir hashtag
                listaHashtag.add(formatearHashtag(cadena));
            }
        }
        
        return listaHashtag;
    }
    
    /**
     * Formatear cadena Hashtag
     * @param cadenaHashtag
     * @return 
     */
    public static String formatearHashtag (final String cadenaHashtag) {
        return cadenaHashtag.replace(CARACTER_HASHTAG, StringUtils.EMPTY);
    }
    
}
