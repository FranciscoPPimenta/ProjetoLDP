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
import java.util.*;

import static java.lang.Integer.parseInt;

public class jogoController extends Main implements Initializable{

    private Stage stage;
    private Scene scene;

    private boolean ganhei;
    ArrayList<Button> Vuttons = new ArrayList<Button>();

    @FXML
    private Button Button1;
    @FXML
    private Button Button2;
    @FXML
    private Button Button3;
    @FXML
    private Button Button4;
    @FXML
    private Button Button5;
    @FXML
    private Button Button6;
    @FXML
    private Button Button7;
    @FXML
    private Button Button8;
    @FXML
    private Button Button9;
    @FXML
    private Button Button10;
    @FXML
    private Button Button11;
    @FXML
    private Button Button12;
    @FXML
    private Button Button13;
    @FXML
    private Button fimT;

    @FXML
    private Button botaoFinal;

    @FXML
    private Text textoGrande;
    private int count = 0;
    private boolean podeJogar = true;
    @FXML
    private Text infoPecaUm;

    private String Jogada;

    private Button lastButton1;
    private Button lastButton2;

    private String PrimeiroClicado;
    private String SegundoClicado;

    private boolean podeEnviar;
    private int counterClick = 0;

    private List<Integer>[] adjacencia = new ArrayList[13];

    @FXML
    public void jogo(ActionEvent e){

        if(podeJogar){

            if(SouPlayerUm == true){

                Button Button = (Button)e.getSource();
                Button.setOnMouseClicked((mouseEvent) -> {

                    if(count < 3){

                        if(Button != Button7){

                            if(!Button.getText().equals("O") && !Button.getText().equals("X")){

                                setPlayerSymbol(Button);
                                count++;
                                infoPecaUm.setText("Peças do Jogador: "+ (3-count));


                                podeJogar = false;

                                fimT.setDisable(false);

                                for(Button c : Vuttons){

                                    if(Button == c){
                                        lastButton1= c;
                                        Jogada= "P1:1:"+c.getId();
                                        System.out.println(c.getId());
                                        podeEnviar = true;
                                    }


                                }

                            }


                        }

                    }else{

                        if(counterClick == 0){

                            if(!Button.getText().equals("O") && !Button.getText().equals("")){

                                PrimeiroClicado= Button.getId();
                                counterClick++;
                            }



                        }else{

                            if(!Button.getText().equals("O")) {

                                SegundoClicado = Button.getId();


                                if (movimentoValido(PrimeiroClicado, SegundoClicado)) {

                                    counterClick = 0;

                                    for (Button c : Vuttons) {

                                        if (PrimeiroClicado.equals(c.getId())) {

                                            lastButton1=c;
                                            c.setText("");

                                        }

                                        if (SegundoClicado.equals(c.getId())) {

                                            lastButton2=c;
                                            setPlayerSymbol(c);
                                            Jogada = "P1:2:" + PrimeiroClicado + ":" + SegundoClicado;
                                            System.out.println(Jogada);
                                            podeJogar = false;
                                            podeEnviar = true;

                                        }

                                    }

                                } else {
                                    PrimeiroClicado = "";
                                    SegundoClicado = "";
                                }
                            }

                        }

                    }

                });

            }else{

                Button Button = (Button)e.getSource();
                Button.setOnMouseClicked((mouseEvent) -> {

                    if(count < 3){

                        if(Button != Button7){

                            if(!Button.getText().equals("O") && !Button.getText().equals("X")) {

                                setPlayerSymbol(Button);
                                count++;
                                infoPecaUm.setText("Peças do Jogador: " + (3 - count));

                                podeJogar = false;
                                fimT.setDisable(false);
                                podeEnviar = true;

                                for (Button c : Vuttons) {

                                    if (Button == c) {
                                        lastButton1=c;
                                        Jogada = "P2:1:" + c.getId();
                                        System.out.println(c.getId());

                                    }


                                }
                            }
                        }
                    }else{

                        if(counterClick == 0){

                            if(!Button.getText().equals("X") && !Button.getText().equals("")) {

                                PrimeiroClicado = Button.getId();
                                counterClick++;
                            }

                        }else{

                            if(!Button.getText().equals("X")) {

                                SegundoClicado= Button.getId();


                                if(movimentoValido(PrimeiroClicado, SegundoClicado)){

                                    counterClick = 0;
                                    podeEnviar = true;

                                    for(Button c : Vuttons){

                                        if(PrimeiroClicado.equals(c.getId())){
                                            lastButton1=c;
                                            c.setText("");

                                        }

                                        if(SegundoClicado.equals(c.getId())){

                                            lastButton2=c;
                                            setPlayerSymbol(c);
                                            Jogada= "P2:2:"+PrimeiroClicado+":"+ SegundoClicado;
                                            System.out.println(Jogada);
                                            podeJogar = false;
                                            podeEnviar = true;

                                        }

                                    }

                                }

                            }

                        }


                    }

                });

            }

        }
    }

    /**
     * Este método é chamado no botao de fim de turno, serve para validar e enviar para o servidor a jogada
     * @param event O ActionEvent é registado ao clicar no botao
     * @throws IOException Caso exista algum erro ao carregar o FXML.
     */
    public void fimTurno(ActionEvent event) throws IOException {


        if(podeEnviar){

            if(!Objects.equals(Jogada, "")){
                if(!Objects.equals(Jogada,cliente.mensagensR.getLast())){

                    cliente.enviarMensagem(Jogada);
                    podeEnviar = false;

                    Platform.runLater(() -> {

                        textoGrande.setText("A espera da Jogada do oponente");
                        vericarWin();

                    });

                }
            }

        }



        /*
        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {


                try {

                    dos.writeUTF(Jogada);
                    dos.flush();
                    System.out.println(dis);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

        sendMessage.start();
*/

    }

    /**
     * Este método serve para processar a mensagem enviada pelo servidor
     * @param mensagem é a mensagem recebida pelo servidor
     */
    private void processarMensagem(String mensagem) {



        Platform.runLater(() -> {

            System.out.println("Estou na funca de processar.");

            if(SouPlayerUm){

                if (mensagem.startsWith("P2")) {

                    vericarWin();
                    esperaJogo=false;
                    textoGrande.setText("E a tua vez de Jogar");

                    agiliza(mensagem);

                }

            }else{

                if (mensagem.startsWith("P1")) {
                    vericarWin();
                    esperaJogo=false;
                    textoGrande.setText("E a tua vez de Jogar");

                    agiliza(mensagem);

                }

            }

        });
    }

    /**
     * Pega a partir do processar mensagem, para não ficar tão confuso lá e funciona melhor também
     * @param mensagem é a mensagem recebida pelo servidor
     */
    private void agiliza(String mensagem){

        Platform.runLater(() -> {

        String[] partes = mensagem.split(":");
        int parteJogo = parseInt(partes[1]);
        if (parteJogo == 1) {

            System.out.println(partes[1] + " 2 -->" + partes[2]);

            String botaoClicado=partes[2];

            for (Button b:Vuttons){

                if(Objects.equals(b.getId(), botaoClicado)){

                    System.out.println(b.getId() + botaoClicado);
                    setAdvSymbol(b);

                    podeJogar = true;
                    fimT.setDisable(false);
                    vericarWin();
                }
            }
            podeJogar = true;
            vericarWin();
        }
        else if (parteJogo==2){

            String botaoClicadoUm=partes[2];
            String botaoClicadoDois=partes[3];

            for (Button b:Vuttons){

                if(Objects.equals(b.getId(), botaoClicadoUm)){

                    System.out.println(b.getId() + botaoClicadoUm);
                    b.setText("");

                }

                if(Objects.equals(b.getId(), botaoClicadoDois)){

                    System.out.println(b.getId() + botaoClicadoDois);
                    setAdvSymbol(b);

                    podeJogar = true;
                    fimT.setDisable(false);

                }
            }
            podeJogar = true;
            vericarWin();
        }

        });
    }

    /**
     * Este método serve para verficar se alguém ganhou
     */
    public void vericarWin(){

        for (int a = 0; a < 16; a++) {
            String linha = switch (a) {
                case 0 -> Button1.getText() + Button5.getText() + Button7.getText();
                case 1 -> Button2.getText() + Button4.getText() + Button8.getText();
                case 2 -> Button6.getText() + Button10.getText() + Button12.getText();
                case 3 -> Button5.getText() + Button7.getText() + Button9.getText();
                case 4 -> Button7.getText() + Button9.getText() + Button13.getText();
                case 5 -> Button1.getText() + Button6.getText() + Button11.getText();
                case 6 -> Button11.getText() + Button12.getText() + Button13.getText();
                case 7 -> Button1.getText() + Button2.getText() + Button3.getText();
                case 8 -> Button3.getText() + Button8.getText() + Button13.getText();
                case 9 -> Button6.getText() + Button7.getText() + Button8.getText();
                case 10 -> Button2.getText() + Button7.getText() + Button12.getText();
                case 11 -> Button6.getText() + Button5.getText() + Button2.getText();
                case 12 -> Button12.getText() + Button9.getText() + Button8.getText();
                case 13 -> Button11.getText() + Button10.getText() + Button7.getText();
                case 14 -> Button10.getText() + Button7.getText() + Button4.getText();
                case 15 -> Button7.getText() + Button4.getText() + Button3.getText();
                default -> null;
            };

            System.out.println("Linha " + a + ": " + linha); // Diagnóstico

            if(SouPlayerUm){

                if ("XXX".equals(linha)) {

                    this.podeJogar = false;
                    this.count = 0;
                    this.fimT.setDisable(true);

                    cliente.enviarMensagem("G1");
                    Platform.runLater(() -> {
                        textoGrande.setText("Ganhaste!! :)");
                        this.ganhei = true;
                        this.botaoFinal.setDisable(false);
                        this.botaoFinal.setVisible(true);
                    });

                } else if (linha.equals("OOO")) {

                    this.podeJogar = false;
                    this.count = 0;
                    this.fimT.setDisable(true);

                    Platform.runLater(() -> {
                        textoGrande.setText("Perdeste!! :(");
                        this.ganhei=false;
                        this.botaoFinal.setDisable(false);
                        this.botaoFinal.setVisible(true);
                    });
                    cliente.enviarMensagem("G2");
                }

            }else{
                if ("XXX".equals(linha)) {

                    this.podeJogar = false;
                    this.count = 0;
                    this.fimT.setDisable(true);

                    cliente.enviarMensagem("G1");
                    Platform.runLater(() -> {
                        textoGrande.setText("Perdeste!! :(");
                        this.ganhei=false;
                        this.botaoFinal.setDisable(false);
                        this.botaoFinal.setVisible(true);

                    });

                } else if (linha.equals("OOO")) {

                    this.podeJogar = false;
                    this.count = 0;
                    this.fimT.setDisable(true);

                    Platform.runLater(() -> {
                        textoGrande.setText("Ganhaste!! :)");
                        this.ganhei = true;
                        this.botaoFinal.setDisable(false);
                        this.botaoFinal.setVisible(true);

                    });
                    cliente.enviarMensagem("G2");
                }
            }

        }
    }

    /**
     * Este método serve para no botão, dependendo de que jogador é por o seu símbolo. Serve também para melhorar a legitividade do outro método e simplicar
     * @param Button é o botão que foi clicado
     */
    public void setPlayerSymbol(Button Button) {
        if (SouPlayerUm) {
            Button.setText("X");
        } else {
            Button.setText("O");
        }

    }

    /**
     * Este método é usado para por o símbolo do adversário, depois de receber a mensagem
     * @param Button é o botao que foi clicado pelo adversário
     */
    public void setAdvSymbol(Button Button) {

        if (!SouPlayerUm) {
            Button.setText("X");
        } else {
            Button.setText("O");
        }

    }

    /**
     *  Este método é usado no botao fim de jogo,em adiciona caso ganhe uma win e manda para o menu principal
     * @param event O ActionEvent é registado ao clicar no botao
     * @throws IOException Caso exista algum erro ao carregar o FXML.
     */
    public void fimJogo(ActionEvent event) throws IOException {

        if(ganhei) {

            this.cliente.ganhou();

        }
        this.count=0;
        this.counterClick=0;
        this.podeJogar = true;

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

    /**
     * Este método é da interface Initializable e serve para quando inicializar o FXML já mudar os valores ou até neste caso para saber se sou o player 1 ou 2
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.botaoFinal.setDisable(true);
        this.botaoFinal.setVisible(false);

        podeEnviar=false;

        for (int i = 0; i < 13; i++) {
            adjacencia[i] = new ArrayList<>();
        }

        criarAdjacias();

        for (int i = 0; i < 13; i++) {
            System.out.print("Espaço " + (i + 1) + " está adjacente a: ");
            for (int adj : adjacencia[i]) {
                System.out.print((adj ) + " ");
            }
            System.out.println();
        }


        cliente.adicionarOuvinte(mensagem -> {
            Platform.runLater(() -> processarMensagem(mensagem));
        });

            if(SouPlayerUm){
                podeJogar=true;
            }else{
                textoGrande.setText("A espera da jogada do Oponente");
                podeJogar=false;
            }
            fimT.setDisable(true);
            Vuttons.add(Button1);
            Vuttons.add(Button2);
            Vuttons.add(Button3);
            Vuttons.add(Button4);
            Vuttons.add(Button5);
            Vuttons.add(Button6);
            Vuttons.add(Button7);
            Vuttons.add(Button8);
            Vuttons.add(Button9);
            Vuttons.add(Button10);
            Vuttons.add(Button11);
            Vuttons.add(Button12);
            Vuttons.add(Button13);


    }


    /**
     * Este método é usado para contruir a "tabela" de adjacencias para saber se a jogada é valida ao mover no outro método.
     */
    public void criarAdjacias(){

        adjacencia[0].add(2); adjacencia[0].add(5); adjacencia[0].add(6); // Button1
        adjacencia[1].add(1); adjacencia[1].add(3); adjacencia[1].add(4); adjacencia[1].add(5); // Button2
        adjacencia[2].add(2); adjacencia[2].add(4); adjacencia[2].add(8); // Button3
        adjacencia[3].add(2); adjacencia[3].add(3); adjacencia[3].add(7); adjacencia[3].add(8); // Button4
        adjacencia[4].add(1); adjacencia[4].add(2); adjacencia[4].add(6); adjacencia[4].add(7); // Button5
        adjacencia[5].add(1); adjacencia[5].add(5); adjacencia[5].add(7); adjacencia[5].add(10); adjacencia[5].add(11); // Button6
        adjacencia[6].add(2); adjacencia[6].add(4); adjacencia[6].add(5); adjacencia[6].add(6); adjacencia[6].add(8); adjacencia[6].add(9); adjacencia[6].add(10); adjacencia[6].add(12);// Button7
        adjacencia[7].add(3); adjacencia[7].add(4); adjacencia[7].add(7); adjacencia[7].add(9); adjacencia[7].add(13); // Button8
        adjacencia[8].add(7); adjacencia[8].add(8); adjacencia[8].add(12); adjacencia[8].add(13); // Button9
        adjacencia[9].add(6); adjacencia[9].add(7); adjacencia[9].add(11); adjacencia[9].add(12); // Button10
        adjacencia[10].add(6); adjacencia[10].add(10); adjacencia[10].add(12); // Button11
        adjacencia[11].add(7); adjacencia[11].add(9); adjacencia[11].add(10); adjacencia[11].add(11); adjacencia[11].add(13); // Button12
        adjacencia[12].add(8); adjacencia[12].add(9); adjacencia[12].add(12); // Button13

    }

    /**
     * Este método é o tal que verifica se a jogada é válida. Vendo se de onde foi clicado pode ir para o segundo click
     * @param PrimBot é a string que é o id do primeiro botao clicado
     * @param SegBot é a string que é o id do segundo botao clicado
     * @return retorna a dizer se a jogada é válida ou não
     */
    public boolean movimentoValido(String PrimBot,String SegBot){

        boolean saVA =false;

        String PrimBotNumber = PrimBot.replace("Button", "");
        String SegBotNumber = SegBot.replace("Button", "");
        int PN = parseInt(PrimBotNumber);
        int SN = parseInt(SegBotNumber);

        if(adjacencia[(PN-1)].contains(SN)){
            saVA=true;
            System.out.println("Movimento válido");
            System.out.println("Primeiro botão: " + PN + "  Segundo Botao" + SN);
        }

        return saVA;
    }
}
