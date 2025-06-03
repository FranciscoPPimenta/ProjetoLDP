package org.example.projetoldp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class esperaJogoController extends Main implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private Button botaoJogar;
    @FXML
    private Button botaoSair;
    @FXML
    private Text textoInfo;



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        cliente.enviarMensagem("qs");

        System.out.println("Estou a espera de jogar? " + esperaJogo);
        System.out.println("Sou player Um? " + SouPlayerUm);


        cliente.adicionarOuvinte(mensagem -> {

            Platform.runLater(() -> processarMensagem(mensagem));

        });


    }

    //processar imagem do servidor
    private void processarMensagem(String mensagem) {


        Platform.runLater(() -> {

            System.out.println("Estou na funca de processar.");
            if (mensagem.startsWith("qs")) {

                System.out.println(mensagem + " processamento de mensagem");
                String[] partes = mensagem.split(":");
                int vez = parseInt(partes[1]);
                if (vez %2 == 0) {
                    System.out.println("Entrou no if, apareceu o botão jogar " + partes[1]);
                    SouPlayerUm = true;
                    textoInfo.setText("Partida Encontrada, és o primeiro a Jogar. És o (X)");
                    botaoJogar.setVisible(true);
                    botaoSair.setVisible(false);

                } else if (vez %2 == 1) {
                    System.out.println(SouPlayerUm);
                    textoInfo.setText("Partida Encontrada, é a vez do oponente. És o (O)");
                    SouPlayerUm = false;
                    System.out.println(SouPlayerUm);

                    botaoJogar.setVisible(true);
                    botaoSair.setVisible(false);
                }
            }


        });
    }

    /**
     *  Este método serve para ir para o jogo quando clicado no botao, sendo que só aparece quando se sabe que utilizador é
     * @param event O ActionEvent é registado ao clicar no botao
     * @throws IOException Caso exista algum erro ao carregar o FXML.
     */
    @FXML
    protected void jogar(ActionEvent event) throws IOException {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("jogo.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}