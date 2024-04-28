/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class JavaFXMLApplication extends Application {
    private static Stage stage;
    private static Scene scene;
    
    private static Map<String, FXMLLoader> mapLoaders;
    private static Map<String, Parent> mapRoots;
    
    //Nombre de las ventanas
    public static final String INICIOSESION = "Iniciar Sesión";
    public static final String PRINCIPAL = "Dashboard";
    public static final String REGISTRO = "Registrarse";
    public static final String REGISTROAVATAR = "Registrarse Avatar";

    //Fin Nombre Ventanas
        @Override
    public void start(Stage s) throws Exception {   
        stage = s;
        stage.setMinWidth(800);
        stage.setMinHeight(800);
        stage.setResizable(false);

        stage.getIcons().add(new Image("resource/logo.png"));
        
        redimensionar(800, 800);
        stage.setMaximized(true);
        
        inicializarMaps();
        cambiarVentana(INICIOSESION);
    }
    
    /**
     * Incializa el Map
     */
    private void inicializarMaps(){
        mapLoaders = new HashMap<>();
        mapLoaders.put(INICIOSESION, new FXMLLoader(getClass().getResource(".." +File.separator + "views" + File.separator + "Login.fxml")));
        mapLoaders.put(PRINCIPAL, new FXMLLoader(getClass().getResource(".." +File.separator + "views" + File.separator + "Lobby.fxml")));
        mapLoaders.put(REGISTRO, new FXMLLoader(getClass().getResource(".." +File.separator + "views" + File.separator + "Register.fxml")));
        mapLoaders.put(REGISTROAVATAR, new FXMLLoader(getClass().getResource(".." +File.separator + "views" + File.separator + "Register2.fxml")));

        mapRoots = new HashMap<>();
    }
    
    /**
     * Inicia la ventana para cambiarla
     * @param loader Archivo fxml
     * @param nombreVentana Nombre de la ventana
     */
    private static void iniciar(Parent root, String nombreVentana){
        double width = getWidth();
        double height = getHeight();
        
        // System.out.println(width + " "+ height);
            
        if (scene == null) scene = new Scene(root);
        else scene.setRoot(root);
        
        stage.setScene(scene);
        stage.setTitle(nombreVentana);
        redimensionar(width, height);
        stage.show();
    }
    
    /**
     * Cambia a la ventana que corresponde
     * @param nombre Nombre de la ventana (Usar variables de la clase)
     */
    public static FXMLLoader cambiarVentana(String nombre) throws IOException{
        //Obtener loader
        Parent root;
        if(mapRoots.containsKey(nombre)) root = mapRoots.get(nombre);
        else {
            root = mapLoaders.get(nombre).load();
            mapRoots.put(nombre, root);
        }
        if(root!=null) {
            iniciar(root, nombre);
            return mapLoaders.get(nombre);
        }
        return null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }

    
    // Funciones common
    /**
     * Cambiar la anchura y altura de la ventana
     * @param width Anchura
     * @param height Altura
     */
    public static void redimensionar(double width, double height){
        stage.setWidth(width);
        stage.setHeight(height);
    }

    
    
    /**
     * Cambiar la anchura de la ventana
     * @param width Anchura
     */
    public static void setWidth(double width){
        stage.setWidth(width);
    }
    
    /**
     * Cambia la altura de la ventana
     * @param height Altura
     */
    public static void setHeight(double height){
        stage.setHeight(height);
    }
    
    /**
     * Da la anchura de la ventana
     */
    public static double getWidth(){
        return stage.getWidth();
    }
    
    /**
     * Da la altura de la ventana
     */
    public static double getHeight(){
        return stage.getHeight();
    }

    /**
     * Da el Stage general
     * @return stage
     */
    public static Stage getStage(){
        return stage;
    }

    public static void Log(String x) {
        System.out.println(x);
    }
    
}
