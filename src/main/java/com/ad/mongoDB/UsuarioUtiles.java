package com.ad.mongoDB;

import com.ad.ad06.Main;
import com.ad.exception.ADException;
import com.ad.utiles.UtilesCifrado;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * UsuarioUtiles
 */
public class UsuarioUtiles {
    
    private static final String NOME_USUARIO = "nome";
    private static final String NOME_COMPLETO = "username";
    private static final String CONTRASINAL = "password";
    private static final String FOLLOWS = "follows";
    
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
        
        List<String> follows = new ArrayList<>();
        //Crear Objeto
        DBObject usuario = new BasicDBObject()
                .append(NOME_COMPLETO, nomeCompleto)
                .append(NOME_USUARIO, username)
                .append(CONTRASINAL, UtilesCifrado.cifrarEnMd5(contrasena))
                .append(FOLLOWS, follows);
        
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
    
    public static void comprobarUsuarioContrasena (final String username, final String contrasena) throws ADException {
        
        Boolean usuarioCorrecto = Boolean.FALSE;
        
        //Crear cosulta
        DBObject query = new BasicDBObject(NOME_USUARIO, username);
        
        try {
        
            DBCursor cursor = dBCollection.find(query);
        
            while (cursor.hasNext()){
                DBObject documento = cursor.next();
                if (documento.get(CONTRASINAL).equals(UtilesCifrado.cifrarEnMd5(contrasena))) {
                    usuarioCorrecto = Boolean.TRUE;
                }
                
            }
            cursor.close();
            
        } catch (Exception e) {
            throw new ADException("Error al buscar usuario.");
        }
        
        //Lanzar error
        if (!usuarioCorrecto) {
            throw new ADException("Usuario y contraseña invalidos.");
        }
    
    }
    
    /**
     * Buscar usuarios con expresion reguar
     * @param nombreUsuario
     * @return
     * @throws ADException 
     */
    public static List<String> buscarUsuarios (final String nombreUsuario) throws ADException {
    
        List<String> listaUsuarios = new ArrayList<>();
        
        //Crear cosulta
        Document regexQuery = new Document();
        regexQuery.append("$regex", ".*" + Pattern.quote(nombreUsuario) + ".*");
        BasicDBObject criteria = new BasicDBObject(NOME_USUARIO, regexQuery);
        
        try {
        
            DBCursor cursor = dBCollection.find(criteria);
        
            while (cursor.hasNext()){
                DBObject documento = cursor.next();
                listaUsuarios.add((String)documento.get(NOME_USUARIO));
            }
            cursor.close();
            
        } catch (Exception e) {
            throw new ADException("Error al buscar usuario.");
        }
        
        return listaUsuarios;
    }
    
    /**
     * Actualizar Usuario
     * @param nomeUsuario
     * @throws ADException 
     */
    public static void añadirUsuarioSeguir (final String nomeUsuario) throws ADException {
    
        Bson filterUp = Filters.eq(NOME_USUARIO, Main.nomeUsuario);
        DBObject queryUp = new BasicDBObject(filterUp.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry()));
        
        List<String> follows = new ArrayList<>();
        //Crear cosulta
        DBObject query = new BasicDBObject(NOME_USUARIO, Main.nomeUsuario);
        
        DBCursor cursor = dBCollection.find(query);

        List<String> listaUsuarios = new ArrayList<>();
        while (cursor.hasNext()){
            DBObject documento = cursor.next();
            listaUsuarios.add((String)documento.get(FOLLOWS));

        }
        listaUsuarios.add(nomeUsuario);
        cursor.close();
        
        Bson updateAux = Updates.set(FOLLOWS,listaUsuarios);
        DBObject update = new BasicDBObject(updateAux.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry()));
        dBCollection.update(queryUp,update);
        
    }
    
}
