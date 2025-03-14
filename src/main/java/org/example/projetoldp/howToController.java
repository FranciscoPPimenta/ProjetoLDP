package org.example.projetoldp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class howToController {

    private Stage stage;
    private Scene scene;



    /**
     * Este método é para voltar para o FXML do Menu
     * @param event O ActionEvent é registado ao clicar no botao
     * @throws IOException Caso exista algum erro ao carregar o FXML.
     */
    @FXML
    protected void voltar(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}