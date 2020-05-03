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
        stringConnection.append(datosConexion.getAddress());
        stringConnection.append(":");
        stringConnection.append(datosConexion.getPort());
        
        MongoClient mongoClient = new MongoClient(new MongoClientURI(stringConnection.toString()));
        
        return mongoClient.getDB(datosConexion.getDbname());
    }
    
}
