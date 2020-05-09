package com.ad.mongoDB;

import com.ad.ad06.Main;
import com.ad.exception.ADException;
import com.ad.utiles.UtilesCifrado;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 *
 * UsuarioUtiles
 */
public class UsuarioUtiles {
    
    private static final String NOME_USUARIO = "username";
    private static final String NOME_COMPLETO = "nomeCompleto";
    private static final String CONTRASINAL = "contrasena";
    
    private static final DBCollection dBCollection = Main.database.getCollection("usuarios");
    
    /**
     * Insertar Usuario
     * @param nomeCompleto
     * @param username
     * @param contrasena
     * @throws ADException 
     */
    public static void insertarUsuarioEnDB (final String nomeCompleto, final String username, final String contrasena) throws ADException {
    
        //Comprobar si existe usuario
        if (buscarUsuarioPorUsername(username)) {
            throw new ADException("El usuario xa existe.");
        }
        
        //Crear Objeto
        DBObject usuario = new BasicDBObject()
                .append(NOME_COMPLETO, nomeCompleto)
                .append(NOME_USUARIO, username)
                .append(CONTRASINAL, UtilesCifrado.cifrarEnMd5(contrasena));
        
        //Insertar Usuario
        dBCollection.insert(usuario);
        
    }
    
    /**
     * BUscar usuario
     * @param username
     * @return 
     * @throws ADException
     */
    public static Boolean buscarUsuarioPorUsername (final String username) throws ADException {
    
        Boolean usuarioExiste = Boolean.FALSE;
        
        //Crear cosulta
        DBObject query = new BasicDBObject(NOME_USUARIO, username);
        
        try {
        
            DBCursor cursor = dBCollection.find(query);
        
            while (cursor.hasNext()){
                usuarioExiste = Boolean.TRUE;
            }
            cursor.close();
            
        } catch (Exception e) {
            throw new ADException("Error al buscar usuario.");
        }
        
        return usuarioExiste;
    }
    
}
