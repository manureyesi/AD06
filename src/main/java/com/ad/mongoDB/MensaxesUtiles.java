/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad.mongoDB;

import com.ad.ad06.Main;
import com.ad.exception.ADException;
import com.ad.mongoDB.vo.Mensaxe;
import com.ad.mongoDB.vo.Usuario;
import com.ad.utiles.Utiles;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.model.DBCollectionFindOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;

/**
 *
 * MensaxesUtiles
 */
public class MensaxesUtiles {
    
    private static final String TEXTO = "text";
    private static final String USUARIO = "user";
    private static final String USUARIO_NOME_USUARIO = "username";
    private static final String NOME_COMPLETO = "nome";
    private static final String FECHA = "date";
    private static final String HASHTAGS = "hashtags";
    
    private static final DBCollection dBCollection = Main.database.getCollection("mensaxe");
    
    /**
     * Crear mensaxe
     * @param mensaxe
     * @throws ADException 
     */
    public static void crearMensaxe (final String mensaxe) throws ADException {
        
        //Buscar Usuario
        Usuario user =
                UsuarioUtiles.buscarUsuarioPorUsername(Main.nomeUsuario);
        
        //Crear Usuario
        DBObject usuarioDB = new BasicDBObject()
                .append(NOME_COMPLETO, user.getNome())
                .append(USUARIO_NOME_USUARIO, user.getUsername());
        
        List<String> hashtags = Utiles.buscarHashtagEnMensaxe(mensaxe);
        
        //Crear Objeto mensaxe
        DBObject mensaxeDB = new BasicDBObject()
                .append(TEXTO, mensaxe)
                .append(USUARIO, usuarioDB)
                .append(FECHA, new Date().toString())
                .append(HASHTAGS, hashtags);
        
        //Insertar Usuario
        dBCollection.insert(mensaxeDB);
    
    }
    
    /**
     * Buscar por hashtag
     * @param hashtag
     * @return
     * @throws ADException 
     */
    public static List<Mensaxe> buscarFiltroMensaxes (final String hashtag) throws ADException {
    
        List<Mensaxe> listaMensaxes = new ArrayList<>();
        
        try {
        
            Bson filter = Filters.eq(HASHTAGS, Utiles.formatearHashtag(hashtag));
            DBObject query = new BasicDBObject(filter.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry()));
            
            
            DBCursor cursor  = dBCollection.find(query);
            while (cursor.hasNext()){
                listaMensaxes.add(crearObjeto(cursor.next()));
            }
            cursor.close();
        
        } catch (Exception e) {
            throw new ADException("Error al buscar hashtag.");
        }
        
        return listaMensaxes;
    }
    
    /**
     * Listar todos os mensaxes
     * @return
     * @throws ADException 
     */
    public static List<Mensaxe> buscarMensaxes () throws ADException {
    
        List<Mensaxe> listaMensaxes = new ArrayList<>();
        
        try {
        
            DBCollectionFindOptions options = new DBCollectionFindOptions();
            Bson sortAux = Sorts.descending(FECHA);
            DBObject sort = new BasicDBObject(sortAux.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry()));
            options.sort(sort);
            
            DBCursor cursor  = dBCollection.find(new BasicDBObject(),options);
            
            Mensaxe men;
            while (cursor.hasNext()){
                men = crearObjeto(cursor.next());
                System.err.println(men.toString());
                listaMensaxes.add(men);
            }
            cursor.close();
            
        } catch (Exception e) {
            throw new ADException("Error al buscar usuario.");
        }
    
        return listaMensaxes;
    }
    
    /**
     * Buscar mensaxes de persoas seguidas
     * @return
     * @throws ADException 
     */
    public static List<Mensaxe> buscarMensaxesSeguidos () throws ADException {
    
        List<Mensaxe> listaMensaxes = new ArrayList<>();
        
        //Buscar seguidores usuario
        Usuario user =
                UsuarioUtiles.buscarUsuarioPorUsername(Main.nomeUsuario);
        
        for (Mensaxe men: buscarMensaxes()) {
        
            if (user.getListaFollowers().contains(men.getUsername())) {
                //añadir mensaxe
                listaMensaxes.add(men);
            }
            
        }
        
        return listaMensaxes;
    }
    
    /**
     * Crear objeto
     * @param documento
     * @return 
     */
    private static Mensaxe crearObjeto (DBObject documento) {
        
        return new Mensaxe(
            (String)documento.get(TEXTO),
            (String)documento.get(FECHA),
            (String)((DBObject)documento.get(USUARIO)).get(NOME_COMPLETO),
            (String)((DBObject)documento.get(USUARIO)).get(USUARIO_NOME_USUARIO),
            (List)documento.get(HASHTAGS)); 
        
    }
    
}
