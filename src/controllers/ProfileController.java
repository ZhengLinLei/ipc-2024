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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafxmlapplication.JavaFXMLApplication;
import model.Acount;
import model.AcountDAOException;
import model.User;

/**
 * FXML Controller class
 *
 * @author Zheng Lin Lei
 */
public class ProfileController implements Initializable {

    @FXML
    private Button backLobby;
    @FXML
    private ImageView avatarCode;
    @FXML
    private TextField signUpNameIn;
    @FXML
    private TextField signUpSurnameIn;
    @FXML
    private TextField signUpEmailIn;
    @FXML
    private PasswordField signUpPassIn;


    private User user;
    private Boolean edit = false;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button modifyBtn;
    @FXML
    private Label userTitle;
    @FXML
    private Button logout;
    
    private Acount acc = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get user info
        try {
            acc = Acount.getInstance();
            this.user = acc.getLoggedUser();
            System.out.println(this.user.getName());
        }
        catch (IOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (AcountDAOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Deacativate input fields
        signUpNameIn.setEditable(false);
        signUpSurnameIn.setEditable(false);
        signUpEmailIn.setEditable(false);
        signUpPassIn.setEditable(false);
        userTitle.setText(user.getNickName());
        
        // Hover add .hover class
        modifyBtn.setOnMouseEntered(e -> modifyBtn.getStyleClass().add("hover"));
        
        // Add action to the button
        backLobby.setOnAction(e -> {
            try {
                JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.PRINCIPAL, true);
            } catch ( IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        // comfirm on click
        modifyBtn.setOnMouseClicked(e -> {
            if (edit) {
                // Save the data
                user.setName(signUpNameIn.getText());
                user.setSurname(signUpSurnameIn.getText());
                user.setEmail(signUpEmailIn.getText());
                user.setPassword(signUpPassIn.getText());

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

        logout.setOnMouseClicked(e -> {
            try {
                acc.logOutUser();
                JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.INICIOSESION);
            }
            catch (IOException ex) {
                Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Cancel on click
        cancelBtn.setOnMouseClicked(e -> {
            if (edit) {
                // Cancel the edit
                signUpNameIn.setText(user.getName());
                signUpSurnameIn.setText(user.getSurname());
                signUpEmailIn.setText(user.getEmail());
                signUpPassIn.setText(user.getPassword());
            }
            cancelEdit();
        });

        // Set the user's data
        signUpNameIn.setText(user.getName());
        signUpSurnameIn.setText(user.getSurname());
        signUpEmailIn.setText(user.getEmail());
        signUpPassIn.setText(user.getPassword());

        // Set the user's avatar
        avatarCode.setImage(user.getImage());
    }    
    

    public void activeEdit() {
        edit = true;

        modifyBtn.setText("Guardar");
        cancelBtn.getStyleClass().add("active");

        signUpNameIn.setEditable(edit);
        signUpSurnameIn.setEditable(edit);
        signUpEmailIn.setEditable(edit);
        signUpPassIn.setEditable(edit);
    }

    public void cancelEdit() {
        edit = false;
        modifyBtn.setText("Modificar");
        cancelBtn.getStyleClass().remove("active");

        signUpNameIn.setEditable(edit);
        signUpSurnameIn.setEditable(edit);
        signUpEmailIn.setEditable(edit);
        signUpPassIn.setEditable(edit);
    }

    public void setUser(User u) {
        user = u;
    }
}
