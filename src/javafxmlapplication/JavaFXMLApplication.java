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
import com.itextpdf.html2pdf.HtmlConverter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javafx.stage.FileChooser;

import model.Acount;
import model.AcountDAOException;


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
    
    
    // Subventanas
    public static final String PRINCIPALPERFIL = "Perfil Usuario";
    
    // Submodulos
    public static final String GASTOSLISTA = "Historial de gastos";
    public static final String GASTOSINFO = "Información de gasto";
    public static final String CATEGORIALISTA = "Categorías";
    public static final String CATEGORIAINFO = "Información de categoría";


    //Fin Nombre Ventanas
    @Override
    public void start(Stage s) throws Exception {   
        stage = s;
        stage.setMinWidth(800);
        stage.setMinHeight(800);
        stage.setResizable(false);

        // Onclose
        stage.setOnCloseRequest(e -> {
            Acount acc = null;
            try {
                acc = Acount.getInstance();
                acc.logOutUser();
            } catch (AcountDAOException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Adiós");
            System.exit(0);
        });

        stage.getIcons().add(new Image("resource/logo.png"));
        
        redimensionar(800, 800);
        // stage.setMaximized(true);
        
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

        mapLoaders.put(PRINCIPALPERFIL, new FXMLLoader(getClass().getResource(".." +File.separator + "views" + File.separator + "component" + File.separator + "Profile.fxml")));
        
        mapLoaders.put(GASTOSLISTA, new FXMLLoader(getClass().getResource(".." +File.separator + "views" + File.separator + "component" + File.separator + "ChargeList.fxml")));
        mapLoaders.put(GASTOSINFO, new FXMLLoader(getClass().getResource(".." +File.separator + "views" + File.separator + "component" + File.separator + "ChargeInfo.fxml")));
        mapLoaders.put(CATEGORIALISTA, new FXMLLoader(getClass().getResource(".." +File.separator + "views" + File.separator + "component" + File.separator + "CategoryList.fxml")));
        mapLoaders.put(CATEGORIAINFO, new FXMLLoader(getClass().getResource(".." +File.separator + "views" + File.separator + "component" + File.separator + "CategoryInfo.fxml")));

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
        stage.setResizable(false);

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
    
    public static FXMLLoader cambiarVentana(String nombre, Boolean x) throws IOException{
        stage.setResizable(x);
        
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

    public static void showAlert(String title, String header, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showAlert(String title, String header, String content, javafx.scene.control.Alert.AlertType type) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showAlert(String title, String header, String content, javafx.scene.control.Alert.AlertType type, javafx.scene.control.ButtonType... buttons) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(buttons);
        alert.showAndWait();
    }

    // Confirmarion with callback
    // JavaFXMLApplication.showConfirm("Eliminar cargo", "¿Estás seguro de que quieres eliminar este cargo?", "Esta acción no se puede deshacer", "Eliminar", "Cancelar", () -> {
    
    public static void showConfirm(String title, String header, String content, String ok, String cancel, Runnable callback) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(javafx.scene.control.ButtonType.OK, javafx.scene.control.ButtonType.CANCEL);
        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                callback.run();
            }
        });
    }
    
    public static void exportHTMLToPDF(String html) {
        if (html == null || html.isEmpty()) {
            System.out.println("El contenido HTML está vacío. No se puede convertir a PDF.");
            return;
        }

        System.out.println("Eligiendo archivo...");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        Stage st2 = new Stage();
        File file = fileChooser.showSaveDialog(st2);

        if (file != null) {
            System.out.println("¡Archivo seleccionado!");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                HtmlConverter.convertToPdf(html, fos);
                
                System.out.println("PDF creado con éxito.");
            } catch (FileNotFoundException e) {
                System.out.println("Error al crear el archivo PDF: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Error al crear el archivo PDF: " + e.getMessage());
            }
        }
    }
    
}
