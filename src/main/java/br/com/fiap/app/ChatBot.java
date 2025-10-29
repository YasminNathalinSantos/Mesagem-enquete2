package br.com.fiap.app;

import br.com.fiap.model.Menssagem;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class ChatBot {
    private MenssagemBanco banco;

    public ChatBot(Connection con) {
        this.banco = new MenssagemBanco(con);
    }


    public void responderMensagem(String pergunta) throws Exception {
        List<Menssagem> resultados = banco.buscarPorPergunta(pergunta);

        if (resultados != null && !resultados.isEmpty()) {
            Menssagem menssagem = resultados.get(0); // mais parecida

            System.out.println("🤖 Nori: " + menssagem.getResposta());
            if (menssagem.getCategoria() != null) {
                System.out.println("🏷️ Categoria: " + menssagem.getCategoria());
            }
        } else {
            System.out.println("\n==============================");
            System.out.println("🤖 Nori: Ainda não sei responder isso 😅");
            System.out.println("💡 Se você souber, pode me ajudar a evoluir!");
            System.out.println("👉 Use a opção 2 do menu para cadastrar a pergunta e a resposta.");
            System.out.println("==============================\n");
        }
    }


    public void cadastrarResposta(String pergunta, String resposta,String categoria) throws Exception {
        banco.adicionarMenssagem(new Menssagem(0, pergunta, resposta,categoria));
        System.out.println("\n==============================");
        System.out.println("🤖 Nori: Resposta cadastrada com sucesso! ✅");
        System.out.println("==============================\n");

    }


    public void atualizarResposta(int id, String novaResposta) throws Exception {
        Menssagem menssagem = banco.buscarPorId(id);
        if (menssagem != null) {
            menssagem.setResposta(novaResposta);
            banco.atualizarMenssagem(menssagem);
            System.out.println("\n==============================");
            System.out.println("🤖 Nori: Resposta atualizada com sucesso! ✅");
            System.out.println("==============================\n");

        } else {
            System.out.println("\n==============================");
            System.out.println("⚠️ 🤖 Nori: Não encontrei nenhuma mensagem com ID " + id + ".");
            System.out.println("==============================\n");

        }
    }

    public void deletarResposta(int id) throws Exception {
        banco.deletarMenssagem(id);
        System.out.println("\n==============================");
        System.out.println("🗑️🤖 Nori: Resposta deletada com sucesso!");
        System.out.println("==============================\n");

    }
    public List<Menssagem> getMensagens() throws SQLException {
        return banco.listarMenssagens();
    }
}
