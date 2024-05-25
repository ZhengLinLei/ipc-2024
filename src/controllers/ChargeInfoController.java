/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafxmlapplication.JavaFXMLApplication;

import model.Acount;
import model.Category;
import model.AcountDAOException;

//List
import java.util.List;

import model.Charge;

/**
 * FXML Controller class
 *
 * @author zheng
 */
public class ChargeInfoController implements Initializable {

    @FXML
    private Button backLobby;
    
    
    private Charge charge;
    @FXML
    private ImageView chargeImage;
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
    private Button modifyBtn;
    @FXML
    private Button deleteBtn;

    private Category catTmp;
    private Acount acc = null;


    private Boolean edit = false;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add action to the button
        backLobby.setOnAction(e -> {
            try {
                JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.PRINCIPAL, true);
            } catch ( IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // ChoiceBox add all categories
        try {
            acc = Acount.getInstance();
            List<Category> categories = acc.getUserCategories();

            for (Category category : categories) {
                chargeCategoryName.getItems().add(category.getName());
            }
        } catch (AcountDAOException ex) {
            Logger.getLogger(ChargeInfoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChargeInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Disable fields
        chargeName.setEditable(false);
        chargeCost.setEditable(false);
        chargeAmount.setEditable(false);
        chargeDescription.setEditable(false);
        chargeDate.setEditable(false);
        chargeCategoryName.setDisable(true);



        // comfirm on click
        modifyBtn.setOnMouseClicked(e -> {
            if (edit) {
                // Save the data
                try {
                    // Get all categories
                    List<Category> l = acc.getUserCategories();
                    for(Category c : l) {
                        if (c.getName().equals(chargeCategoryName.getValue())) {
                            catTmp = c;
                        }
                    }

                    // Remove this
                    acc.removeCharge(charge);

                    // Register the new one
                    acc.registerCharge(
                            chargeName.getText(), 
                            chargeDescription.getText(),
                            Double.parseDouble(chargeCost.getText()), 
                            Integer.parseInt(chargeAmount.getText()), 
                            chargeImage.getImage(),
                            chargeDate.getValue(), catTmp);

                } catch (AcountDAOException ex) {
                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Remove the edit
                cancelEdit();
                // // Save the data
                // try {
                //     acc.updateUser(user);
                // } catch (AcountDAOException ex) {
                //     Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                // }
            } else {
                activeEdit();
            }
        });

        // Delete on click
        deleteBtn.setOnMouseClicked(e -> {
            // Show confirmation prompt
            JavaFXMLApplication.showConfirm("Eliminar cargo", "¿Estás seguro de que quieres eliminar este cargo?", "Esta acción no se puede deshacer", "Eliminar", "Cancelar", () -> {
                try {
                    Acount acc2 = Acount.getInstance();
                    acc2.removeCharge(charge);
                    // JavaFXMLApplication.showAlert("Cargo eliminado", "Cargo eliminado correctamente", "El cargo ha sido eliminado correctamente");
                    // Click on back
                    backLobby.fire();
                } catch (AcountDAOException ex) {
                    Logger.getLogger(ChargeInfoController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ChargeInfoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });
    }

    private void activeEdit() {
        edit = true;
        modifyBtn.setText("Guardar");
        chargeName.setEditable(true);
        chargeCost.setEditable(true);
        chargeAmount.setEditable(true);
        chargeDescription.setEditable(true);
        chargeDate.setEditable(true);
        chargeCategoryName.setDisable(false);
    }

    private void cancelEdit() {
        edit = false;
        modifyBtn.setText("Modificar");
        chargeName.setEditable(false);
        chargeCost.setEditable(false);
        chargeAmount.setEditable(false);
        chargeDescription.setEditable(false);
        chargeDate.setEditable(false);
        chargeCategoryName.setDisable(true);
    }
    
    public void setCharge(Charge charge) {
        this.charge = charge;

        // Update interface
        chargeName.setText(charge.getName());
        chargeCost.setText(String.valueOf(charge.getCost()));
        chargeAmount.setText(String.valueOf(charge.getUnits()));
        chargeDescription.setText(charge.getDescription());
        chargeDate.setValue(charge.getDate());
        chargeCategoryName.setValue(charge.getCategory().getName());
        chargeImage.setImage(charge.getImageScan());
    }
    
    public void setReturn(String ret) {
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
    
}
