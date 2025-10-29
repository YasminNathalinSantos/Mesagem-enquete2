package br.com.fiap.model;

import br.com.fiap.app.ChatBot;

public class SuporteTeleconsulta extends UsuarioSistema {

    public SuporteTeleconsulta(String nome) {
            super(nome);
        }

    @Override
    public void enviarMenssagem(ChatBot bot, String menssagem) throws Exception {
        System.out.println(getNome() + ": " + menssagem);
        bot.responderMensagem(menssagem);
    }

    public void cadastrarResposta(ChatBot bot, String pergunta, String resposta, String categoria) throws Exception {
        bot.cadastrarResposta(pergunta, resposta, categoria);
    }

    public void atualizarResposta(ChatBot bot, int id, String novaResposta) throws Exception {
        bot.atualizarResposta(id, novaResposta);
    }

    public void deletarResposta(ChatBot bot, int id) throws Exception {
        bot.deletarResposta(id);
    }


}

