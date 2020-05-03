package com.ad.ad06;

import com.ad.exception.ADException;
import com.ad.json.JsonUtiles;
import com.ad.json.pojo.DatosConexion;
import com.ad.mongoDB.MongoDBUtiles;
import com.mongodb.DB;
import java.io.File;

/**
 *
 * Main
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        final String datosArchivo = "Datos_conexion.json";
        
        try {
        
            //Cargar datos DB
            DatosConexion datosDriver =
                    JsonUtiles.leerArchivoJson(new File(datosArchivo));

            //Crear Conexion
            DB db = MongoDBUtiles.crearConexionMongo(datosDriver);
            
        } catch (ADException e) {
            System.err.println(e.getDescripcionError());
        }
        
    }
    
}
