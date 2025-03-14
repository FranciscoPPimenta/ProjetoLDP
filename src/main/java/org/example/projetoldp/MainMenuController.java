package org.example.projetoldp;

import java.io.IOException;
import java.util.*;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;


import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends Main implements Initializable {

    @FXML
    private ListView<String> highscore;
    private Stage stage;
    private Scene scene;

    /**
     * Este método é da interface Initializable e serve para quando inicializar o FXML já mudar os valores ou até neste caso para saber se sou o player 1 ou 2
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for(Cliente qwe: Jogadores){
            String listViewHighScore = qwe.getNome() + " : " + qwe.getCounterWins();
            this.highscore.getItems().add(listViewHighScore);
        }

        ordenarHighScore();

       /* Thread receberMessagem = new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
                        String msg = dis.readUTF();
                        if(msg.startsWith("")){}
                        System.out.println(msg);

                    } catch (IOException e) {
                    }

            }
        });

        receberMessagem.start();*/



    }

    /**
     * Este método serve para ir para ir jogar, na parte em que procura jogador
     * @param event O ActionEvent é registado ao clicar no botao
     * @throws IOException Caso exista algum erro ao carregar o FXML.
     */
    @FXML protected void goPlay(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("esperaJogo.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        stagee=stage;

    }

    /**
     * Este método serve para ir para a página de informação do jogo (Como Jogar)
     * @param event O ActionEvent é registado ao clicar no botao
     * @throws IOException Caso exista algum erro ao carregar o FXML.
     */
    @FXML protected void infoGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("howTo.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Este método serve para fechar o programa
     * @param event O ActionEvent é registado ao clicar no botao
     * @throws IOException Caso exista algum erro ao carregar o FXML.
     */
    @FXML
    protected void fechar(ActionEvent event) throws IOException {

        cliente.enviarMensagem("logout");
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();

    }

    /**
     * Este método serve para organizar a tabela de HighScore
     */
    public void ordenarHighScore() {

        Collections.sort(Jogadores, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente j1, Cliente j2) {
                return Integer.compare(j2.getCounterWins(), j1.getCounterWins());
            }
        });

    }


}


