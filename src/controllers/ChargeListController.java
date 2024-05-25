/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.callback.Callback;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableCell;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafxmlapplication.JavaFXMLApplication;
import model.Acount;
import model.AcountDAO;
import model.AcountDAOException;
import model.Charge;
import model.Category;
import java.time.LocalDate;
import model.MyChargeModel;
import model.MyButtonModel;
import model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author zheng
 */
public class ChargeListController implements Initializable {

    @FXML
    private Button backLobby;
    @FXML
    private javafx.scene.control.TableView<MyChargeModel> tableView;
    @FXML
    private javafx.scene.control.TableColumn<MyChargeModel, String> tCat;
    @FXML
    private javafx.scene.control.TableColumn<MyChargeModel, String> tName;
    @FXML
    private javafx.scene.control.TableColumn<MyChargeModel, String> tPrice;
    @FXML
    private javafx.scene.control.TableColumn<MyChargeModel, String> tUds;
    @FXML
    private javafx.scene.control.TableColumn<MyChargeModel, String> tDate;
    
    private User user;

    final ObservableList<MyChargeModel> data = FXCollections.observableArrayList();

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
        update();
    }
    
    public void update() {

        // Get user info
        Acount acc = null;
        AcountDAO accD = null;

       

        try {
            acc = Acount.getInstance();
            
            this.user = acc.getLoggedUser();
        }
        catch (IOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (AcountDAOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Get all charges
        List<Charge> charges = null;
        try {
            System.out.println(acc.getLoggedUser().getNickName());
            charges = acc.getUserCharges();
        }
        catch (AcountDAOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex); 
        }
        catch (NullPointerException d) {
            System.out.println(d);
            charges = null;
        }

        /*
        // Add example test
        List<Category> categories = null;
        try {
            categories = acc.getUserCategories();
            acc.registerCharge("Comida", "Pizza", 12.3, 1, this.user.getImage(), LocalDate.now(), categories.get(0));
        }
        catch (AcountDAOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        */

        // Create a table with all charges
        /*
         * private int id;
         * private String name;
         * private String description;
         * private double cost;
         * private int units;
         * private Image scanImage;
         * private LocalDate date;
         * private Category category;
         */

        
        if (charges != null || !charges.isEmpty()) {
            // Remove all first
            data.removeAll(data);
            
            System.out.println("Tamaño: " + charges.size());
            int t_size = charges.size();
            // Set columns
            tCat.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
            tName.setCellValueFactory(new PropertyValueFactory<>("chargeName"));
            tPrice.setCellValueFactory(new PropertyValueFactory<>("chargePrice"));
            tUds.setCellValueFactory(new PropertyValueFactory<>("chargeUds"));
            tDate.setCellValueFactory(new PropertyValueFactory<>("chargeDate"));

            // Row on double click
            tableView.setRowFactory(tv -> {
                javafx.scene.control.TableRow<MyChargeModel> row = new javafx.scene.control.TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        MyChargeModel rowData = row.getItem();
                        
                        // Get charge
                        //System.out.println(rowData.getChargeModel().getName());
                        
                        try {
                            ChargeInfoController c = JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.GASTOSINFO).getController();
                            c.setReturn(JavaFXMLApplication.GASTOSLISTA);
                            c.setCharge(rowData.getChargeModel());
                        } catch (IOException ex) {
                            Logger.getLogger(ChargeListController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                    }
                });
                return row;
            });
            
            

            try {
                // Insert to model MyChargeModel

                // Not showing data to table
                int i = 0;
                for (Charge charge : charges) {
                    System.out.println(i+"/"+t_size);
                    MyChargeModel myCharge = new MyChargeModel(charge.getCategory().getName(), charge.getName(), Double.toString(charge.getCost()), Integer.toString(charge.getUnits()), charge.getDate().toString(), charge);
                    System.out.println(myCharge.getCategoryName() + " " + myCharge.getChargeName() + " " + myCharge.getChargePrice() + " " + myCharge.getChargeUds() + " " + myCharge.getChargeDate());
                    data.add(myCharge);
                    i++;
                }

                // Set data
                tableView.setItems(data);
            }
            catch (NullPointerException ex) {
                System.out.println("Vacio");
            }
        }
    }
}
