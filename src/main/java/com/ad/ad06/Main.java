package com.ad.ad06;

import com.ad.exception.ADException;
import com.ad.json.JsonUtiles;
import com.ad.json.pojo.DatosConexion;
import com.ad.mongoDB.MongoDBUtiles;
import com.ad.vistas.Inicio;
import com.ad.vistas.Rexistro;
import com.mongodb.DB;
import java.io.File;

/**
 *
 * Main
 */
public class Main {

    //Creaciíon varibles Frames
    public static Inicio frameInicio = new Inicio();
    public static Rexistro frameRexistro = new Rexistro();
    
    //Conexion DB
    public static DB database;
    
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
            database = MongoDBUtiles.crearConexionMongo(datosDriver);
            
            frameInicio.setVisible(true);
            
        } catch (ADException e) {
            System.err.println(e.getDescripcionError());
        }
        
    }
    
}
