/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.File;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication;
import model.Acount;
import model.AcountDAOException;
import model.Category;


/**
 * FXML Controller class
 *
 * @author panqu
 */
public class AddChargeController implements Initializable {

    @FXML
    private Button backLobby;
    @FXML
    private TextField chargeName;
    @FXML
    private TextField chargeCost;
    @FXML
    private TextField chargeAmount;
    @FXML
    private TextArea chargeDescription;
    @FXML
    private DatePicker chargeDate;
    @FXML
    private ChoiceBox<String> chargeCategoryName;
    @FXML
    private Button addBtn;
    @FXML
    private ImageView chargeImage;
    @FXML
    private Button addImageBtn;
    
    @FXML
    private Text textoError;
    
    private String imagePath = "resource/img-ticket.jpg";
    
    private boolean imgAdded = false;
    
    private List<Category> categories;
    private static Map<String, Category> mapCategory;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       predeterminedImage();
       try {
           Acount acc = Acount.getInstance();
           categories = acc.getUserCategories();
           mapCategory = new HashMap<>();
           for (Category c : categories) {
               mapCategory.put(c.getName(), c);
               chargeCategoryName.getItems().add(c.getName());
           }
        } catch (IOException | AcountDAOException e) {
           System.out.println(e);
        } 
    }    
    
    private void predeterminedImage() {
        Image image = new Image(imagePath);
        chargeImage.setImage(image);
        imgAdded = false;
    }
    
    private boolean valuesEmpty() {
        return chargeName.getText().trim().isEmpty() &&
               chargeCost.getText().trim().isEmpty() &&
               chargeAmount.getText().trim().isEmpty() &&
               chargeDescription.getText().trim().isEmpty() &&
               chargeDate.getValue() == null &&
               chargeCategoryName.getValue() == null &&
               !imgAdded;
    }
    
    private boolean somethingEmpty() {
        return chargeName.getText().trim().isEmpty() ||
               chargeCost.getText().trim().isEmpty() ||
               chargeAmount.getText().trim().isEmpty() ||
               chargeDescription.getText().trim().isEmpty() ||
               chargeDate.getValue() == null ||
               chargeCategoryName.getValue() == null ||
               !imgAdded;
    }
    
    private void clearAll() {
        chargeName.clear();
        chargeCost.clear();
        chargeAmount.clear();
        chargeDescription.clear();
        chargeDate.setValue(null);
        chargeCategoryName.setValue(null);
        predeterminedImage();
    }

    @FXML
    private boolean goBack(ActionEvent event) {
        if (!valuesEmpty()) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Advertencia");
            alert.setHeaderText("¿Estás seguro de que quieres volver?");
            alert.setContentText("Se perderán los datos no guardados");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.CANCEL){
               return false;
            }
        }
        try {
            clearAll();
            textoError.setVisible(false);
            JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.PRINCIPAL);
        }
        catch (IOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @FXML
    private void addCharge(ActionEvent event) throws AcountDAOException, IOException {
        if (somethingEmpty()) {
            textoError.setVisible(true);
        } else {
            Acount acc = Acount.getInstance();
            Category cat = mapCategory.get(chargeCategoryName.getValue());
            acc.registerCharge(chargeName.getText().trim(), 
                           chargeDescription.getText().trim(), 
                           parseDouble(chargeCost.getText().trim()), 
                           parseInt(chargeAmount.getText().trim()), 
                           chargeImage.getImage(), 
                           chargeDate.getValue(), 
                           (Category) cat);

            // Alert
            JavaFXMLApplication.showAlert("Carga exitosa", "La carga se ha realizado con éxito", "Se ha registrado la carga correctamente", AlertType.INFORMATION);
            LobbyController c = null;
            c = JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.PRINCIPAL, true).getController();
            c.update();
            clearAll();
            textoError.setVisible(false);
        }
        
    }

    public void setReturn(String ret) {
        update();
        clearAll();
        textoError.setVisible(false);
        backLobby.setOnAction(e -> {
            try {
                if (ret.equals(JavaFXMLApplication.GASTOSLISTA)) {
                    ChargeListController c = null;
                    c = JavaFXMLApplication.cambiarVentana(ret).getController();
                    c.update();
                } else {
                    LobbyController c = null;
                    c = JavaFXMLApplication.cambiarVentana(ret, true).getController();
                    c.update();
                }
            } catch ( IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void addImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        
        Image image = new Image(file.toURI().toString());
        chargeImage.setImage(image);
        
        imgAdded = true;
    }

    @FXML
    private void addCategory(ActionEvent event) {
        try {
            JavaFXMLApplication.mostrarVentana(JavaFXMLApplication.ADDCATEGORIA);
            update();
        }
        catch (IOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update() {
        try {
            // Remove all
            chargeCategoryName.getItems().removeAll(chargeCategoryName.getItems());
            Acount acc = Acount.getInstance();
            categories = acc.getUserCategories();
            mapCategory = new HashMap<>();
            for (Category c : categories) {
                mapCategory.put(c.getName(), c);
                chargeCategoryName.getItems().add(c.getName());
            }
        } catch (IOException | AcountDAOException e) {
           System.out.println(e);
        } 
    }
}
