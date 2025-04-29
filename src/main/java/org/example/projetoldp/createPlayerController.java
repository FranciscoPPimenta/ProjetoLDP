package org.example.projetoldp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;

public class createPlayerController extends Main {

    @FXML
    private TextField inputUsername;
    @FXML
    private TextField inputAdrr;
    @FXML
    private TextField inputPorta;

    @FXML
    private Text errorName;
    private Stage stage;
    private Scene scene;


    /**
     *  Este metódo serve para entrar no servidor e mesmo tempo registrar o utilizador
     * @param e O ActionEvent é registado ao clicar no botao
     * @throws IOException Caso exista algum erro ao carregar o FXML.
     */
    @FXML
    protected void criarJogador(ActionEvent e) throws IOException {

        try {
            ip= InetAddress.getByName(inputAdrr.getText());
            ServerPort = Integer.parseInt(inputPorta.getText());
            String nome = this.inputUsername.getText();

            cliente= new Cliente(nome,ip,ServerPort);

        }catch (Exception ex) {

            System.out.println(ex.getMessage());

            this.errorName.setText("Erro ao conectar ao servidor!! Tente novamente");
            this.inputUsername.setText("");
            this.inputAdrr.setText("");
            this.inputPorta.setText("");

        }

            Jogadores.add(cliente);

            cliente.enviarMensagem("nome:"+ inputUsername.getText());
            System.out.println(Jogadores.size());



        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


}
