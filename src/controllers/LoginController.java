/**
        MIT License

        Copyright (c) 2024 ZhengLinLei, Elena Clofent Muñoz and Alejandro Zafra Muñoz

        Permission is hereby granted, free of charge, to any person obtaining a copy
        of this software and associated documentation files (the "Software"), to deal
        in the Software without restriction, including without limitation the rights
        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
        copies of the Software, and to permit persons to whom the Software is
        furnished to do so, subject to the following conditions:

        The above copyright notice and this permission notice shall be included in all
        copies or substantial portions of the Software.

        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
        SOFTWARE.

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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafxmlapplication.JavaFXMLApplication;

// Localdate
import java.time.LocalDate;

import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author Zheng Lin Lei
 */
public class LoginController implements Initializable {

    @FXML
    private TextField signInEmailIn;
    @FXML
    private PasswordField signInPassIn;
    @FXML
    private Button signInBtn;
    @FXML
    private Hyperlink registerRedirect;
    @FXML
    private Label errorLabel;
    @FXML
    private Pane loginWindow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Link
        registerRedirect.setOnMouseClicked((MouseEvent event) -> {
            try {
                clickRegistrarse(event);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Botón
        signInBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                signInBtnEvent(event);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Hover
        signInBtn.setOnMouseEntered((MouseEvent event) -> {
            hoverLoginBtn(event);
        });
        signInBtn.setOnMouseExited((MouseEvent event) -> {
            exitLoginBtn(event);
        });

        // Enter
        loginWindow.setOnKeyPressed((KeyEvent event) -> {
            try {
                windowKeyEnter(event);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    

    private void signInBtnEvent(MouseEvent event) throws IOException {
        String u = signInEmailIn.getText();
        String p = signInPassIn.getText();
        
        // Quitar clase error
        signInEmailIn.getStyleClass().remove("error");
        signInPassIn.getStyleClass().remove("error");
        errorLabel.getStyleClass().remove("error");

        // Debug values
        Logger.getLogger(LoginController.class.getName()).log(Level.INFO, "Email: " + u);
        Logger.getLogger(LoginController.class.getName()).log(Level.INFO, "Pass: " + p);

        Acount acc = null;
        boolean valido = false;

        if(u != null && p != null && !u.equals("") && !p.equals("")) {
            // Hay valor
            try {
                acc = Acount.getInstance();
                
                valido = acc.logInUserByCredentials(u, p);
                
            } catch (AcountDAOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (u == null || u.equals("")) {
                signInEmailIn.getStyleClass().add("error");
            }
            if (p == null || p.equals("")) {
                signInPassIn.getStyleClass().add("error");
            }
            
            return;
        }
        
        if (valido) {
            clear();
            // Entrar a dashboard
            LobbyController c = JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.PRINCIPAL, true).getController();
            // ProfileController c = JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.PRINCIPAL).getController();
            try {
                // c.setUser(acc.getLoggedUser());
            } catch (NullPointerException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            errorLabel.getStyleClass().add("error");
        }
    }
    void setNewUser(String username) {
        signInEmailIn.setText(username);
    }
    private void hoverLoginBtn(MouseEvent event) {
        signInBtn.getStyleClass().add("hover");
    }
    private void exitLoginBtn(MouseEvent event) {
        signInBtn.getStyleClass().remove("hover");
    }
    
    private void windowKeyEnter(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER){
            signInBtnEvent(null);
        }
    }
    private void clear() {
        signInEmailIn.getStyleClass().remove("error");
        signInPassIn.getStyleClass().remove("error");
        errorLabel.getStyleClass().remove("error");
        // Remove all input values
        signInEmailIn.setText("");
        signInPassIn.setText("");
    }
    private void clickRegistrarse(MouseEvent event) throws IOException {
        clear();
        JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.REGISTRO);
    }
    
    
}
