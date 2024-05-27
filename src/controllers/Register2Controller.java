package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.AcceptPendingException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
// Img
import javafx.scene.image.Image;
import model.Acount;
import model.AcountDAOException;
import model.Category;

// List
import java.util.List;

import javafxmlapplication.JavaFXMLApplication;

/**
 * FXML Controller class
 * 
 * @author: Zheng Lin Lei
 */
public class Register2Controller implements Initializable {

    @FXML
    private Pane registerWindow;
    @FXML
    private Label welcomeText;
    @FXML
    private Button signUpBtn;
    @FXML
    private Hyperlink loginRedirect;
    @FXML
    private Button selectIzq;
    @FXML
    private ImageView selectImg;
    @FXML
    private Button selectDrch;
    @FXML
    private Hyperlink importImage;

    Image img;
    Image[] imageList;
    Integer imgIndex = 0;

    String name, surname, email, user, pass;
    LocalDate date;
    
    /**
    * Initializes the controller class.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get all image from resource/avatar
        getImages();

        // Set the first image
        img = imageList[imgIndex];
        selectImg.setImage(img);
        
        
        selectIzq.setOnMouseClicked((MouseEvent event) -> {
            imgIndex = (imgIndex - 1) % 10;
            if (imgIndex < 0) {
                imgIndex = 9;
            }
            img = imageList[imgIndex];
            selectImg.setImage(img);
        });

        selectDrch.setOnMouseClicked((MouseEvent event) -> {
            imgIndex = (imgIndex + 1) % 10;
            img = imageList[imgIndex];
            selectImg.setImage(img);
        });


        loginRedirect.setOnMouseClicked((MouseEvent event) -> {
            try {
                clickIniciarSesion(event);
            } catch (IOException ex) {
                Logger.getLogger(Register2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        signUpBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                clickRegistrarse(event);
            } catch (IOException ex) {
                Logger.getLogger(Register2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        registerWindow.setOnKeyPressed((KeyEvent event) -> {
            try {
                windowKeyEnter(event);
            } catch (IOException ex) {
                Logger.getLogger(Register2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        // Hover
        signUpBtn.setOnMouseEntered((MouseEvent event) -> {
            hoverLoginBtn(event);
        });
        signUpBtn.setOnMouseExited((MouseEvent event) -> {
            exitLoginBtn(event);
        });

        // Import image
        importImage.setOnMouseClicked((MouseEvent event) -> {
            // Open file manager to select image from computer
            // Set the image to the selected image
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar imagen");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.jpeg"));

            File selectedFile = fileChooser.showOpenDialog(importImage.getScene().getWindow());
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                selectImg.setImage(image);
                img = image;
            }
        });

    }

    // Register data
    public void registerUser(String  n, String s, String e, String u, String p, LocalDate date) {
        email = e;
        user = u;
        pass = p;
        name = n;
        surname = s;
        this.date = date;

        welcomeText.setText(name + ", escoge tu avatar");
    }
    
    private void hoverLoginBtn(MouseEvent event) {
        signUpBtn.setStyle("-fx-padding: 10px; -fx-cursor: hand; -fx-background-color: #e74b94;");
    }
    private void exitLoginBtn(MouseEvent event) {
        signUpBtn.setStyle("-fx-padding: 10px; -fx-cursor: hand;");
    }

    // Get all image from resource/avatar
    private void getImages() {
        //Get all images from resource/avatar
        imageList = new Image[10];
        for (int i = 1; i <= 10; i++) {
            // System.out.println("resource/avatar/" + i + ".png");
            imageList[i-1] = new Image("resource/avatar/" + i + ".png");
        }
    }



    private void clickRegistrarse(MouseEvent event) throws IOException {
        try {
            Acount acc = Acount.getInstance();

            acc.registerUser(name, surname, email, user, pass, img, date);
            // Show alert
            JavaFXMLApplication.showAlert("Usuario registrado", "Usuario registrado correctamente", "Ahora puedes iniciar sesión");
            //
            LoginController c = JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.INICIOSESION).getController();
            c.setNewUser(user);
        } catch (AcountDAOException ex) {
            Logger.getLogger(Register2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void windowKeyEnter(KeyEvent event) throws IOException {
        clickRegistrarse(null);
    }
    private void clickIniciarSesion(MouseEvent event) throws IOException {
        JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.INICIOSESION);
    }
}
