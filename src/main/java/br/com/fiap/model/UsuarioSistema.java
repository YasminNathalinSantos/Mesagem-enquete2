package br.com.fiap.model;

import br.com.fiap.app.ChatBot;

public abstract class UsuarioSistema {
    private String nome;

    public UsuarioSistema(String nome) {
        this.nome = nome;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public abstract void enviarMenssagem(ChatBot bot, String menssagem) throws Exception;


}



