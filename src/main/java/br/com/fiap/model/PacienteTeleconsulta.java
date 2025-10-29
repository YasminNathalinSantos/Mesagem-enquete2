package br.com.fiap.model;

import br.com.fiap.app.ChatBot;

public class PacienteTeleconsulta extends UsuarioSistema {

    public PacienteTeleconsulta(String nome) {
            super(nome);
        }

    @Override
    public void enviarMenssagem(ChatBot bot, String menssagem) throws Exception {
        System.out.println(getNome() + ": " + menssagem);
        bot.responderMensagem(menssagem);
        }
    }


