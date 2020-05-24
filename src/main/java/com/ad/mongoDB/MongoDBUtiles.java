package com.ad.mongoDB;

import com.ad.json.pojo.DatosConexion;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 *
 * MongoDBUtiles
 */
public class MongoDBUtiles {
    
    /**
     * Crear conexion MongoDB
     * @param datosConexion
     * @return 
     */
    public static DB crearConexionMongo (final DatosConexion datosConexion) {
    
        //Crear String de conexion DB
        StringBuilder stringConnection = new StringBuilder();
        stringConnection.append("mongodb://");
        stringConnection.append(datosConexion.getUsername());
        stringConnection.append(":");
        stringConnection.append(datosConexion.getPassword());
        stringConnection.append("@");
        stringConnection.append(datosConexion.getAddress());
        stringConnection.append(":");
        stringConnection.append(datosConexion.getPort());
        stringConnection.append("/");
        stringConnection.append(datosConexion.getDbname());
        stringConnection.append("?retryWrites=false");
        
        MongoClient mongoClient = new MongoClient(new MongoClientURI(stringConnection.toString()));
        
        return mongoClient.getDB(datosConexion.getDbname());
    }
    
}
