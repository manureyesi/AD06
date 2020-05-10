package com.ad.mongoDB;

import com.ad.ad06.Main;
import com.ad.exception.ADException;
import com.ad.mongoDB.vo.Usuario;
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
    
    private static final String NOME_USUARIO = "username";
    private static final String NOME_COMPLETO = "nome";
    private static final String CONTRASINAL = "password";
    private static final String FOLLOWS = "follows";
    
    private static final DBCollection dBCollection = Main.database.getCollection("usuario");
    
    /**
     * Insertar Usuario
     * @param nomeCompleto
     * @param username
     * @param contrasena
     * @throws ADException 
     */
    public static void insertarUsuarioEnDB (final String nomeCompleto, final String username, final String contrasena) throws ADException {
    
        //Comprobar si existe usuario
        if (comprobarSiExisteUsuaio(username)) {
            throw new ADException("El usuario xa existe.");
        }
        
        List<String> follows = new ArrayList<>();
        //Crear Objeto
        DBObject usuario = new BasicDBObject()
                .append(NOME_COMPLETO, nomeCompleto)
                .append(NOME_USUARIO, username)
                .append(CONTRASINAL, contrasena)
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
    public static Boolean comprobarSiExisteUsuaio (final String username) throws ADException {
    
        Boolean usuarioExiste = Boolean.FALSE;
        
        //Comprobar si existe Usuario
        if (buscarUsuarioPorUsername(username)!=null) {
            usuarioExiste = Boolean.TRUE;
        }
        
        return usuarioExiste;
    }
    
    /**
     * Buscar Usuario por Username
     * @param username
     * @return
     * @throws ADException 
     */
    protected static Usuario buscarUsuarioPorUsername (final String username) throws ADException {
    
        //Crear cosulta
        DBObject query = new BasicDBObject(NOME_USUARIO, username);
        
        Usuario usuario = null;
        
        try {
        
            DBCursor cursor = dBCollection.find(query);
        
            while (cursor.hasNext()){
                usuario = crearObjeto(cursor.next());
                System.err.println(usuario.toString());
            }
            cursor.close();
            
        } catch (Exception e) {
            throw new ADException("Error al buscar usuario.");
        }
        
        return usuario;
    }
    
    /**
     * Comprobar usuario y contraseña
     * @param username
     * @param contrasena
     * @throws ADException 
     */
    public static void comprobarUsuarioContrasena (final String username, final String contrasena) throws ADException {
        
        Boolean usuarioCorrecto = Boolean.FALSE;
        
        Usuario user = 
                buscarUsuarioPorUsername(username);
        
        //Comprobar si existe usuario
        if (user == null) {
            throw new ADException("Usuario y contraseña invalidos.");
        }
        
        //Comprobar contraseña
        if (!user.getPassword().equals(contrasena)) {
            throw new ADException("Usuario y contraseña invalidos.");
        }
        
    }
    
    /**
     * Buscar usuarios con expresion reguar
     * @param nombreUsuario
     * @return
     * @throws ADException 
     */
    public static List<Usuario> buscarUsuarios (final String nombreUsuario) throws ADException {
    
        List<Usuario> listaUsuarios = new ArrayList<>();
        
        //Crear cosulta
        Document regexQuery = new Document();
        regexQuery.append("$regex", ".*" + Pattern.quote(nombreUsuario) + ".*");
        BasicDBObject criteria = new BasicDBObject(NOME_USUARIO, regexQuery);
        
        try {
        
            DBCursor cursor = dBCollection.find(criteria);
        
            while (cursor.hasNext()){
                listaUsuarios.add(crearObjeto(cursor.next()));
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
        
        List<String> follows = buscarUsuarioPorUsername(Main.nomeUsuario).getListaFollowers();
        //añadir lista follows
        follows.add(nomeUsuario);
        
        //Crear cosulta
        DBObject query = new BasicDBObject(NOME_USUARIO, Main.nomeUsuario);
        
        Bson updateAux = Updates.set(FOLLOWS, follows);
        DBObject update = new BasicDBObject(updateAux.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry()));
        dBCollection.update(queryUp,update);
        
    }
    
    /**
     * Crear objeto
     * @param documento
     * @return 
     */
    private static Usuario crearObjeto (DBObject documento) {
        
        return new Usuario(
            (String)documento.get(NOME_COMPLETO),
            (String)documento.get(NOME_USUARIO),
            (String)documento.get(CONTRASINAL),
            (List)documento.get(FOLLOWS)); 
        
    }
    
}
