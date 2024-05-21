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
import javafx.scene.chart.PieChart;
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
import model.Charge;
import model.Category;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Alejandro
 */
public class LobbyController implements Initializable {
    
    
    
    private User user;
    private Acount acc;
    private float totalGastosInt;
    @FXML
    private Label userName;
    @FXML
    private Label totalGastos;
    @FXML
    private Button exportarPDF;
    @FXML
    private Button exportarPDF1;
    @FXML
    private PieChart idPieChart;
    @FXML
    private Label n1, n2, n3;
    @FXML
    private Label c1, c2, c3;
    @FXML
    private Label a1, a2, a3;
    @FXML
    private Pane btnC1;
    @FXML
    private Pane btnC2;
    @FXML
    private Pane btnC3;
    @FXML
    private Label noDataLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        // Reset pie chart
        idPieChart.getData().clear();

        // Create array of (n1, c1, a1, btnC1), (n2, c2, a2, btnC2), (n3, c3, a3, btnC3)
        Label[] names = {n1, n2, n3};
        Label[] category = {c1, c2, c3};
        Label[] amounts = {a1, a2, a3};
        Pane[] pane = {btnC1, btnC2, btnC3};
        btnC1.setVisible(false);
        btnC2.setVisible(false);
        btnC3.setVisible(false);
        noDataLabel.setVisible(false);
        totalGastosInt = 0;
        int i = 3;
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        
        try {
            List<Charge> l = this.acc.getUserCharges();
            // Map with the category and the total amount of money
            java.util.Map<String, Double> map = new java.util.HashMap<>();
            for (Charge c : l) {
                // Show top 3 charges
                if (i > 0) {
                    names[3 - i].setText(c.getName());
                    category[3 - i].setText(c.getCategory().getName());
                    amounts[3 - i].setText(String.format("%.2f", c.getCost()) + " €");
                    pane[3 - i].setVisible(true);
                    pane[3 - i].setOnMouseClicked((e) -> {
                        try {
                            ChargeInfoController ch = JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.GASTOSINFO).getController();
                            ch.setReturn(JavaFXMLApplication.PRINCIPAL);
                            ch.setCharge(c);
                            System.out.println("Charge: " + c.getName());
                        } catch (IOException ex) {
                            Logger.getLogger(ChargeListController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    i--;

                }
                totalGastosInt += c.getCost();
                String cat = c.getCategory().getName();
                if (map.containsKey(cat)) {
                    map.put(cat, map.get(cat) + c.getCost());
                }
                else {
                    map.put(cat, c.getCost());
                }
            }

            for (java.util.Map.Entry<String, Double> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }

            if (l.size() == 0) {
                pieChartData.add(new PieChart.Data("No hay gastos", 0));
                
                // No data
                noDataLabel.setVisible(true);
            }
        }
        catch (AcountDAOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Value with two decimals
        totalGastos.setText(String.format("%.2f", totalGastosInt) + " €");

        /*pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " amount: ", data.pieValueProperty()
                        )
                )
        );*/

        idPieChart.getData().addAll(pieChartData);


        
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