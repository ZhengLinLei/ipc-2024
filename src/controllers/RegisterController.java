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
import java.time.LocalDate;
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

import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author Zheng Lin Lei
 */
public class RegisterController implements Initializable {

    @FXML
    private TextField signUpEmailIn;
    @FXML
    private TextField signUpUserIn;
    @FXML
    private PasswordField signUpPassIn;
    @FXML
    private PasswordField signUpRepassIn;
    @FXML
    private Button signUpBtn;
    @FXML
    private Hyperlink loginRedirect;
    @FXML
    private Pane registerWindow;
    @FXML
    private TextField signUpNameIn;
    @FXML
    private TextField signUpSurnameIn;
    @FXML
    private Label errorLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Link
        loginRedirect.setOnMouseClicked((MouseEvent event) -> {
            try {
                clickLogin(event);
            } catch (IOException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        // Hover
        signUpBtn.setOnMouseEntered((MouseEvent event) -> {
            hoverLoginBtn(event);
        });
        signUpBtn.setOnMouseExited((MouseEvent event) -> {
            exitLoginBtn(event);
        });
        
        // Botón
        signUpBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                signUpBtnEvent(event);
            } catch (IOException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        // Enter
        registerWindow.setOnKeyPressed((KeyEvent event) -> {
            try {
                windowKeyEnter(event);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    private void signUpBtnEvent(MouseEvent event) throws IOException {
        String n = signUpNameIn.getText();
        String s = signUpSurnameIn.getText();
        String u = signUpUserIn.getText();
        String e = signUpEmailIn.getText();
        String p = signUpPassIn.getText();
        String rp = signUpRepassIn.getText();

        // Remove styles
        errorLabel.getStyleClass().remove("error");
        signUpNameIn.getStyleClass().remove("error");
        signUpSurnameIn.getStyleClass().remove("error");
        signUpUserIn.getStyleClass().remove("error");
        signUpEmailIn.getStyleClass().remove("error");
        signUpPassIn.getStyleClass().remove("error");
        signUpRepassIn.getStyleClass().remove("error");

        // Comprobar que los campos no estén vacíos
        if (n.isEmpty() || s.isEmpty() || u.isEmpty() || e.isEmpty() || p.isEmpty() || rp.isEmpty()) {
            System.out.println("Vacios");
            if (n.isEmpty()) signUpNameIn.getStyleClass().add("error");
            if (s.isEmpty()) signUpSurnameIn.getStyleClass().add("error");
            if (u.isEmpty()) signUpUserIn.getStyleClass().add("error");
            if (e.isEmpty()) signUpEmailIn.getStyleClass().add("error");
            if (p.isEmpty()) signUpPassIn.getStyleClass().add("error");
            if (rp.isEmpty()) signUpRepassIn.getStyleClass().add("error");

            errorLabel.setText("Rellene todos los campos");
            errorLabel.getStyleClass().add("error");
            return;
        }

        // Comprobar que email es correcto
        if (!e.contains("@") || !e.contains(".")) {
            errorLabel.setText("Email no válido");
            errorLabel.getStyleClass().add("error");
            signUpEmailIn.getStyleClass().add("error");
            return;
        }

        // Comprobar que las contraseñas coinciden
        if (!p.equals(rp)) {
            errorLabel.setText("Las contraseñas no coinciden");
            errorLabel.getStyleClass().add("error");
            signUpPassIn.getStyleClass().add("error");
            signUpRepassIn.getStyleClass().add("error");
            return;
        }

        // Tienen que tener más de 6 caracteres
        if (p.length() < 6) {
            errorLabel.setText("La contraseña debe tener más de 6 caracteres");
            errorLabel.getStyleClass().add("error");
            signUpPassIn.getStyleClass().add("error");
            signUpRepassIn.getStyleClass().add("error");
            return;
        }

        // Comprobar que no existe otro usuario con el mismo nombre
        Acount acc = null;
        try {
            acc = Acount.getInstance();
            
            // If exist
            if (acc.existsLogin(u)) {
                errorLabel.setText("El usuario ya existe");
                errorLabel.getStyleClass().add("error");
                signUpUserIn.getStyleClass().add("error");
                return;
            }

            // Get LocalDate
            LocalDate date = LocalDate.now();
            // Save and change window to Register2
            clear();
            Register2Controller c = JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.REGISTROAVATAR).getController();
            c.registerUser(e, u, e, n, p, date);

            //acc.registerUser(e, u, e, n, p, new Image("avatars/default.png"), date);
            
        } catch (AcountDAOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void hoverLoginBtn(MouseEvent event) {
        signUpBtn.getStyleClass().add("hover");
    }
    private void exitLoginBtn(MouseEvent event) {
        signUpBtn.getStyleClass().remove("hover");
    }
    private void windowKeyEnter(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER){
            signUpBtnEvent(null);
        }
    }

    private void clear() {
        signUpNameIn.getStyleClass().remove("error");
        signUpSurnameIn.getStyleClass().remove("error");
        signUpUserIn.getStyleClass().remove("error");
        signUpEmailIn.getStyleClass().remove("error");
        signUpPassIn.getStyleClass().remove("error");
        signUpRepassIn.getStyleClass().remove("error");
        errorLabel.getStyleClass().remove("error");
        // Remove all input values
        signUpNameIn.setText("");
        signUpSurnameIn.setText("");
        signUpUserIn.setText("");
        signUpEmailIn.setText("");
        signUpPassIn.setText("");
        signUpRepassIn.setText("");
    }
    private void clickLogin(MouseEvent event) throws IOException {
        clear();
        JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.INICIOSESION);
    }
}