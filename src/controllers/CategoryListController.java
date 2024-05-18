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
import javafx.scene.control.ListView;
import javafxmlapplication.JavaFXMLApplication;
import model.Acount;

import model.AcountDAOException;

import model.Category;
import model.User;
import java.util.List;

/**
 * FXML Controller class
 *
 * @author zheng
 */
public class CategoryListController implements Initializable {

    @FXML
    private Button backLobby;
    @FXML
    private ListView<String> listaCategory;
    @FXML
    private Button addbtn;
    @FXML
    private Button modifyBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button viewBtn;

    Category ActiveCategory;
    private List<Category> categories;

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

        modifyBtn.setDisable(true);
        deleteBtn.setDisable(true);
        viewBtn.setDisable(true);

        // ListView select get value index
        listaCategory.setOnMouseClicked(e -> {
            int index = listaCategory.getSelectionModel().getSelectedIndex();
            System.out.println("Index: " + index);
            if (index >= 0) {
                ActiveCategory = categories.get(index);

                System.out.println("Active Category: " + ActiveCategory.getName());

                modifyBtn.setDisable(false);
                deleteBtn.setDisable(false);
                viewBtn.setDisable(false);
                // Add .active class
                modifyBtn.getStyleClass().add("active");
                deleteBtn.getStyleClass().add("active");
                viewBtn.getStyleClass().add("active");
            } else {
                disableActive();
            }

            if (e.getClickCount() == 2) {
                if (index >= 0) {
                    ActiveCategory = categories.get(index);
                    try {
                        CategoryInfoController c = JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.CATEGORIAINFO).getController();
                        c.setCategory(ActiveCategory);
                        c.setReturn(JavaFXMLApplication.CATEGORIALISTA);
                        ActiveCategory = null;
                        disableActive();
                    } catch ( IOException ex) {
                        Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        // Add action to the button
        addbtn.setOnAction(e -> {
            try {
                JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.GASTOSLISTA);
                ActiveCategory = null;
                disableActive();
            } catch ( IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        // Add delete action
        deleteBtn.setOnAction(e -> {
            try {
                // Alert confirmation
                Acount acc = Acount.getInstance();
                JavaFXMLApplication.showConfirm("Eliminar categoria", "¿Estás seguro de que quieres eliminar esta categoria?", "Esta acción no se puede deshacer\nCategoria: "+ActiveCategory.getName(), "Eliminar", "Cancelar", () -> {
                    try {
                        acc.removeCategory(ActiveCategory);
                        ActiveCategory = null;
                        update();
                        ActiveCategory = null;
                        disableActive();
                    } catch (AcountDAOException ex) {
                        Logger.getLogger(CategoryListController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } catch (AcountDAOException ex) {
                Logger.getLogger(CategoryListController.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex) {
                Logger.getLogger(CategoryListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Add view action
        viewBtn.setOnAction(e -> {
            try {
                CategoryInfoController c = JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.CATEGORIAINFO).getController();
                c.setCategory(ActiveCategory);
                c.setReturn(JavaFXMLApplication.CATEGORIALISTA);
                ActiveCategory = null;
                disableActive();
            } catch ( IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Add modify action
        modifyBtn.setOnAction(e -> {
            try {
                CategoryInfoController c = JavaFXMLApplication.cambiarVentana(JavaFXMLApplication.CATEGORIAINFO).getController();
                c.setModify(true);
                c.setCategory(ActiveCategory);
                c.setReturn(JavaFXMLApplication.CATEGORIALISTA);
                ActiveCategory = null;
                disableActive();
            } catch ( IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        update();
    }

    public void disableActive() {
        modifyBtn.setDisable(true);
        deleteBtn.setDisable(true);
        viewBtn.setDisable(true);

        modifyBtn.getStyleClass().remove("active");
        deleteBtn.getStyleClass().remove("active");
        viewBtn.getStyleClass().remove("active");
    }

    public void update() {
        // Remove all
        listaCategory.getItems().removeAll(listaCategory.getItems());
        // Get all categories
        Acount acc = null;
        try {
            acc = Acount.getInstance();
            categories = acc.getUserCategories();
            for (Category category : categories) {
                listaCategory.getItems().add(category.getName());
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoryListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
