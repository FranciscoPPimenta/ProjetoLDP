package org.example.projetoldp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;

    private String nome;
    private int counterWins;

    public ArrayList<String> mensagensR = new ArrayList<String>();

    private List<OuvinteMensagem> ouvintes = new ArrayList<>();

    /**
     * Construtor da classe Cliente. Começa a conexão com o servidor e inicia a thread para ouvir mensagens.
     *
     * @param nome  O nome do cliente.
     * @param ip    O endereço IP do servidor.
     * @param porta A porta do servidor.
     * @throws IOException Caso exista algum erro ao conectar ao servidor
     */
    public Cliente(String nome,InetAddress ip, int porta) throws IOException {

        this.nome=nome;
        this.counterWins=0;
        s = new Socket(ip, porta);
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());

        System.out.println("Cliente inicializado com sucesso.");

        Thread lerMensagem = new Thread(() -> {
            try {
                while (true) {
                    String mensagem = dis.readUTF();
                    notificarOuvintes(mensagem);
                    System.out.println("ouvi: " + mensagem);
                    mensagensR.add(mensagem);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lerMensagem.start();
    }
    /**
     * Envia uma mensagem para o servidor.
     *
     * @param mensagem A mensagem a ser enviada.
     */
    public void enviarMensagem(String mensagem) {
        try {
            dos.writeUTF(mensagem);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Adiciona um ouvinte de mensagem à lista de ouvintes.
     *
     * @param ouvinte O ouvinte de mensagem a ser adicionado.
     */
    public void adicionarOuvinte(OuvinteMensagem ouvinte) {
        ouvintes.add(ouvinte);

    }
    /**
     * Notifica todos os ouvintes sobre uma nova mensagem recebida.
     *
     * @param mensagem A mensagem recebida.
     */
    private void notificarOuvintes(String mensagem) {
        for (OuvinteMensagem ouvinte : ouvintes) {
            ouvinte.mensagemRecebida(mensagem);

        }
    }
    /**
     * Interface para ouvintes de mensagem. Implementada para receber "notificações" de novas mensagens.
     */
    public interface OuvinteMensagem {
        void mensagemRecebida(String mensagem);
    }

    /**
     * Getter do Nome
     * @return retorna o nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Aumenta as vitórias
     */
    public void ganhou() {
        this.counterWins ++;
    }

    /**
     * Getter das Vitórias
     * @return o número de vitórias
     */
    public int getCounterWins() {
        return counterWins;
    }


}
