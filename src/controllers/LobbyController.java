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
import java.util.List;
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
import javafx.scene.layout.Pane;
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

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static javafx.scene.paint.Color.*;
import model.Category;

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
    private Pane btnC1, btnC2, btnC3;
    @FXML
    private Label noDataLabel;
    @FXML
    private Pane paneCat1, paneCat2, paneCat3, paneCat4, paneCat5, paneCat6, paneCat7, paneCat8, paneCat9;
    @FXML
    private Label cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9;
    @FXML
    private FontAwesomeIconView flecha1, flecha2, flecha3, flecha4, flecha5, flecha6, flecha7, flecha8, flecha9;
    @FXML
    private Button verHistorialCategorias;
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

        exportarPDF.setOnMouseClicked((e) -> {
            try {
                String fechaHoy = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
                String absPathOfImage = getClass().getResource("../resource/logo3.png").toExternalForm();
                System.out.println(absPathOfImage);
                String html = "<!DOCTYPE html><html lang='en'><head><meta charset='utf-8' /><meta name='viewport' content='width=device-width, initial-scale=1' /><title>PIGGYBANK</title><!-- Favicon --><link rel='icon' href='./images/favicon.png' type='image/x-icon' /><!-- Invoice styling --><style>body {font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;text-align: center;color: #777;}body h1 {font-weight: 300;margin-bottom: 0px;padding-bottom: 0px;color: #000;}body h3 {font-weight: 300;margin-top: 10px;margin-bottom: 20px;font-style: italic;color: #555;}body a {color: #06f;}.invoice-box {max-width: 800px;margin: auto;padding: 30px;border: 1px solid #eee;box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);font-size: 16px;line-height: 24px;font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;color: #555;}.invoice-box table {width: 100%;line-height: inherit;text-align: left;border-collapse: collapse;}.invoice-box table td {padding: 5px;vertical-align: top;}.invoice-box table tr td:nth-child(3), .a {text-align: right;}.invoice-box table tr.top table td {padding-bottom: 20px;}.invoice-box table tr.top table td.title {font-size: 45px;line-height: 45px;color: #333;}.invoice-box table tr.information table td {padding-bottom: 40px;}.invoice-box table tr.heading td {background: #eee;border-bottom: 1px solid #ddd;font-weight: bold;}.invoice-box table tr.details td {padding-bottom: 20px;}.invoice-box table tr.item td {border-bottom: 1px solid #eee;}.invoice-box table tr.item.last td {border-bottom: none;}.invoice-box table tr.total td:nth-child(2) {border-top: 2px solid #eee;font-weight: bold;}@media only screen and (max-width: 600px) {.invoice-box table tr.top table td {width: 100%;display: block;text-align: center;}.invoice-box table tr.information table td {width: 100%;display: block;text-align: center;}}</style></head><body><div class='invoice-box'><table><tr class='top'><td colspan='3'><table><tr><td class='title'><img style='height:100px;' src='"+absPathOfImage+"' alt='Company logo' style='width: 100%; max-width: 300px' /></td><td>PDF de fecha<br />"+fechaHoy+"<br />PIGGYBANK APP</td></tr></table></td></tr><tr class='information'><td colspan='3'><table><tr><td>"+this.user.getName()+"<br />"+this.user.getSurname()+"<br /></td><td>"+this.user.getEmail()+"</td></tr></table></td></tr><tr class='heading'><td>Gasto</td><td>Costo</td> <td float='right'>Uds</td></tr>";
                // <tr class='item'>
                //     <td>Website design</td>

                //     <td>$300.00</td>

                //     <td style='text-align: end;'>1</td>
                // </tr>
                List<Charge> l = this.acc.getUserCharges();
                float i = 0;
                for (Charge c : l) {
                    i += c.getCost()*c.getUnits();
                    html += "<tr class='item'><td>" + c.getName() + "</td><td>" + c.getCost() + " €</td><td float='right'>"+c.getUnits()+"</td></tr>";
                }
                // Two decimal
                html += "<tr class='total'><td></td> <td></td><td style='text-align: end'><b>Total: "+ String.format("%.2f", i) + " €</b></td></tr></table></div></body></html>";
                JavaFXMLApplication.exportHTMLToPDF(html);

                // Alert success
                JavaFXMLApplication.showAlert("PDF exportado", "El PDF se ha exportado correctamente", "");
            }
            catch (AcountDAOException ex) {
                Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
    
    public void setUser(User u) throws IOException{
        this.user = u;
        
        userName.setText(u.getName());
    }

    @FXML
    private void entrarPerfil(MouseEvent event) throws IOException {
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
        Label[] categoryListNames = {cat1,cat2,cat3,cat4,cat5,cat6,cat7,cat8,cat9};
        Pane[] pane = {btnC1, btnC2, btnC3};
        Pane[] paneCat = {paneCat1,paneCat2,paneCat3,paneCat4,paneCat5,paneCat6,paneCat7,paneCat8,paneCat9};
        FontAwesomeIconView[] flechas = {flecha1,flecha2,flecha3,flecha4,flecha5,flecha6,flecha7,flecha8,flecha9};
        btnC1.setVisible(false);
        btnC2.setVisible(false);
        btnC3.setVisible(false);
        noDataLabel.setVisible(false);
        totalGastosInt = 0;
        int i = 3;
        int j;
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        
        try {
            List<Charge> l = this.acc.getUserCharges();
            List<Category> l2 = this.acc.getUserCategories();
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
                totalGastosInt += c.getCost()*c.getUnits();
                String cat = c.getCategory().getName();
                if (map.containsKey(cat)) {
                    map.put(cat, map.get(cat) + c.getCost());
                }
                else {
                    map.put(cat, c.getCost());
                }
            }
            for(j = 0; j < 9; j++){
                paneCat[j].setVisible(false);
            }
            j = 9;
            for(Category x : l2){

                if(j > 0){
                    // Reset all the views
                    categoryListNames[9 - j].setStyle("-fx-text-fill: BLACK; -fx-font-size: 21px");
                    flechas[9 - j].setFill(BLACK);
                    paneCat[9 - j].setStyle("-fx-background-color: #fefefe; -fx-cursor: hand; -fx-border-color: grey; -fx-border-width: 0.5px;");
                    categoryListNames[9 - j].setText(x.getName());
                    paneCat[9 - j].setVisible(true);
                    paneCat[9 - j].setOnMouseClicked((e) -> {
                    CategoryInfoController catCtrl;
                        try {
                            catCtrl = JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.CATEGORIAINFO).getController();
                            catCtrl.setReturn(JavaFXMLApplication.PRINCIPAL);
                            catCtrl.setCategory(x);
                        } catch (IOException ex) {
                            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    
                    });
                }
                j--;
            }
            //Show add category button
            System.out.println(j);
            categoryListNames[9 - j].setText("Añadir categorías");
            categoryListNames[9 - j].setStyle("-fx-text-fill: WHITE; -fx-font-size: 21px");
            flechas[9 - j].setFill(WHITE);
            paneCat[9 - j].setStyle("-fx-background-color: #e85a9d; -fx-cursor: hand");
            paneCat[9 - j].setVisible(true);
            paneCat[9 - j].setOnMouseClicked((e) -> {
                try {
                    JavaFXMLApplication.mostrarVentana(JavaFXMLApplication.ADDCATEGORIA);
                    update();
                } catch (IOException ex) {
                    Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            //pie chart
            
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
    private void anadirGastos(MouseEvent event) throws AcountDAOException {
        try {
            acc = Acount.getInstance();
            if (acc.getUserCategories().isEmpty() == false) {
                AddChargeController c = JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.ADDCHARGE).getController();
                c.update();
                c.setReturn(JavaFXMLApplication.PRINCIPAL);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("No hay ninguna categoría creada");
                alert.setContentText("Crea una categoría si desea añadir un gasto");
                alert.showAndWait();
                JavaFXMLApplication.mostrarVentana(JavaFXMLApplication.ADDCATEGORIA);
            }

        }
        catch (IOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @FXML
    private void historialCategrorias(ActionEvent event) {
        try {
            CategoryListController c = JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.CATEGORIALISTA).getController();
            c.setReturn(JavaFXMLApplication.PRINCIPAL);
            c.update();
        }
        catch (IOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}