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

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafxmlapplication.JavaFXMLApplication;
import model.Acount;
import model.AcountDAO;
import model.AcountDAOException;
import model.Charge;
import model.MyChargeModel;
import model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;

// Sqlite
import org.sqlite.SQLiteDataSource;
import java.sql.Connection;

/**
 * FXML Controller class
 *
 * @author zheng
 */
public class CategoryInfoController implements Initializable {

    @FXML
    private Button backLobby;
    @FXML
    private TableView<MyChargeModel> tableView;
    @FXML
    private TableColumn<MyChargeModel, String> tName;
    @FXML
    private TableColumn<MyChargeModel, String> tPrice;
    @FXML
    private TableColumn<MyChargeModel, String> tUds;
    @FXML
    private TableColumn<MyChargeModel, String> tDate;
    @FXML
    private TextField categoryName;
    @FXML
    private TextArea categoryDescription;
    @FXML
    private Button modifyBtn;
    @FXML
    private Button deleteBtn;

    private Category category;
    private List<Charge> chargesTmp = new java.util.ArrayList<>();
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

        // Disable inputs
        categoryName.setEditable(false);
        categoryDescription.setEditable(false);

        // Add action to modify the button
        // Disable modify button opacity 0
        modifyBtn.setOnMouseClicked(e -> {
            setModify(true);
        });

        // Add action to delete the button
        deleteBtn.setOnAction(e -> {
            // Show confirmation dialog
            JavaFXMLApplication.showConfirm("Eliminar categoria", "¿Estás seguro de que quieres eliminar esta categoría?", "Eliminar la categoría eliminará todo los cargos asociados a la categoría", "Eliminar", "Cancelar", () -> {
                try {
                    Acount acc = Acount.getInstance();
                    // Remove all charges from this category
                    List<Charge> charges = acc.getUserCharges();
                    for (Charge charge : charges) {
                        if (charge.getCategory().getName().equals(category.getName())) {
                            acc.removeCharge(charge);
                        }
                    }
                    acc.removeCategory(category);
                    // Click backLobby
                    backLobby.fire();
                } catch (AcountDAOException ex) {
                    Logger.getLogger(CategoryInfoController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(CategoryInfoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });
    }

    public void setModify(Boolean modify) {
        categoryDescription.setEditable(true);

        if (modifyBtn.getText().equals("Modificar")) {
            modifyBtn.setText("Guardar");
        } else {
            modifyBtn.setText("Modificar");
            category.setName(categoryName.getText());
            category.setDescription(categoryDescription.getText());
            try {
                Acount acc = Acount.getInstance();


                // NO HAY FUNCION PARA MODIFICAR CATEGORIA!!!!!!! en Acount.class
                // Obtener todas los charge de la categoria
                List<Charge> charges = acc.getUserCharges();
                for (Charge charge : charges) {
                    if (charge.getCategory().getName().equals(category.getName())) {
                        chargesTmp.add(charge);
                    }
                }
                acc.removeCategory(category);

                acc.registerCategory(category.getName(), category.getDescription());
                // Recreate all charges
                for (Charge charge : chargesTmp) {
                    acc.registerCharge(charge.getName(), charge.getDescription(), charge.getCost(), charge.getUnits(), charge.getImageScan(), charge.getDate(), category);
                }
            } catch (AcountDAOException ex) {
                Logger.getLogger(CategoryInfoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CategoryInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setCategory(Category category) {
        this.category = category;

        // Set values
        categoryName.setText(category.getName());
        categoryDescription.setText(category.getDescription());
        
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
                    if (charge.getCategory().getName().equals(category.getName())) {
                        System.out.println(i+"/"+t_size);
                        MyChargeModel myCharge = new MyChargeModel(charge.getCategory().getName(), charge.getName(), Double.toString(charge.getCost()), Integer.toString(charge.getUnits()), charge.getDate().toString(), charge);
                        System.out.println(myCharge.getCategoryName() + " " + myCharge.getChargeName() + " " + myCharge.getChargePrice() + " " + myCharge.getChargeUds() + " " + myCharge.getChargeDate());
                        data.add(myCharge);
                    }
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
    
    public void setReturn(String ret) {
        backLobby.setOnAction(e -> {
            modifyBtn.setText("Modificar");

            try {
                if (ret.equals(JavaFXMLApplication.CATEGORIALISTA)) {
                    CategoryListController c = null;
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
