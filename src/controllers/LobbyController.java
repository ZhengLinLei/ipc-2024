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

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafxmlapplication.JavaFXMLApplication;
import model.User;
import model.Acount;
import model.AcountDAOException;
import model.AcountDAO;

/**
 * FXML Controller class
 *
 * @author Alejandro
 */
public class LobbyController implements Initializable {
    
    
    
    private User user;
    @FXML
    private Label userName;
    @FXML
    private Label totalGastos;
    @FXML
    private Button exportarPDF;
    @FXML
    private Button exportarPDF1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Acount acc = null;
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
    }    
    
    
    public void setUser(User u) throws IOException{
        this.user = u;
        
        userName.setText(u.getName());
    }

    @FXML
    private void entrarPerfil(MouseEvent event) {
        try {
            JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.PRINCIPALPERFIL);
        }
        catch (IOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void update() {
        
    }

    @FXML
    private void entrarHistorial(MouseEvent event) {
        System.out.println("Historial");
        try {
            JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.GASTOSLISTA);
        }
        catch (IOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void anadirGastos(MouseEvent event) {
    }
}