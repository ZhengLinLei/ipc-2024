/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
public class AddCategoryController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextArea descTextField;
    @FXML
    private Button addBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Text textoError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void addCategory(ActionEvent event) throws AcountDAOException, IOException {
        if (nameTextField.getText().trim().isEmpty() || descTextField.getText().trim().isEmpty()) {
            textoError.setVisible(true);
        }
        else {
            Acount acc = Acount.getInstance();
            acc.registerCategory(nameTextField.getText(), descTextField.getText());

            nameTextField.clear();
            descTextField.clear();
            textoError.setVisible(false);
            
            close(event);
        }
    }

    @FXML
    private void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        textoError.setVisible(false);
        stage.close();
    }
    
    
}
