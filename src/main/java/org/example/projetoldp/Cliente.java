package org.example.projetoldp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//criacao do cliente + entrada no servidor
public class Cliente {
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;

    private String nome;
    private int counterWins;

    public ArrayList<String> mensagensR = new ArrayList<String>();

    private List<OuvinteMensagem> ouvintes = new ArrayList<>();

    //criacao do cliente
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
    // envia a mensagem de entrada/jogada/etc ao servidor
    public void enviarMensagem(String mensagem) {
        try {
            dos.writeUTF(mensagem);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //adiciona ouvinte
    public void adicionarOuvinte(OuvinteMensagem ouvinte) {
        ouvintes.add(ouvinte);

    }
    //envia mensagem a todas os ouvintes
    private void notificarOuvintes(String mensagem) {
        for (OuvinteMensagem ouvinte : ouvintes) {
            ouvinte.mensagemRecebida(mensagem);

        }
    }
    //interface de ouvintes
    public interface OuvinteMensagem {
        void mensagemRecebida(String mensagem);
    }


    public String getNome() {
        return nome;
    }

    public void ganhou() {
        this.counterWins ++;
    }

    public int getCounterWins() {
        return counterWins;
    }


}
