/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
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


    private Boolean edit = false;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add action to the button
        backLobby.setOnAction(e -> {
            try {
                JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.PRINCIPAL);
            } catch ( IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // ChoiceBox add all categories
        Acount acc = null;
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
        // modifyBtn.setOnMouseClicked(e -> {
        //     if (edit) {
        //         // Save the data
        //         try {
        //             charge.setName(chargeName.getText());
        //             charge.setCost(Double.parseDouble(chargeCost.getText()));
        //             charge.setUnits(Integer.parseInt(chargeAmount.getText()));
        //             charge.setDescription(chargeDescription.getText());
        //             charge.setDate(chargeDate.getValue());
        //             charge.setCategory(new Category(chargeCategoryName.getValue()));
        //             acc.updateCharge(charge);
        //         } catch (AcountDAOException ex) {
        //             Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        //         }

        //         // Remove the edit
        //         cancelEdit();
        //         // // Save the data
        //         // try {
        //         //     acc.updateUser(user);
        //         // } catch (AcountDAOException ex) {
        //         //     Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        //         // }
        //     } else {
        //         activeEdit();
        //     }
        // });

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
                if (ret == JavaFXMLApplication.GASTOSLISTA) {
                    ChargeListController c = null;
                    c = JavaFXMLApplication.cambiarVentana(ret).getController();
                    c.update();
                } else {
                    LobbyController c = null;
                    c = JavaFXMLApplication.cambiarVentana(ret).getController();
                    c.update();
                }
            } catch ( IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
}
