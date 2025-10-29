package br.com.fiap.aplicacao;

import br.com.fiap.app.ChatBot;
import br.com.fiap.connection.Conexao;
import br.com.fiap.model.PacienteTeleconsulta;
import br.com.fiap.model.SuporteTeleconsulta;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class MainGeral {
    public static void main(String[] args) {
        try (Connection con = new Conexao().getConnection();
             Scanner sc = new Scanner(System.in)) {

            ChatBot bot = new ChatBot(con);
            SuporteTeleconsulta suporte = new SuporteTeleconsulta("Suporte");

            System.out.print("✏️  Digite seu nome: ");
            String nomePaciente = sc.nextLine();
            PacienteTeleconsulta paciente = new PacienteTeleconsulta(nomePaciente);

            boolean rodando = true;
            while (rodando) {
                System.out.println("\n✨=== 📌 Menu Principal ===✨");
                System.out.println("🤖 1 - Perguntar ao ChatBot");
                System.out.println("📝 2 - Cadastrar Pergunta, Resposta e Categoria");
                System.out.println("✏️ 3 - Atualizar Resposta");
                System.out.println("🗑️ 4 - Deletar Resposta");
                System.out.println("📜 5 - Listar todas as Mensagens");
                System.out.println("🚪 0 - Sair");
                System.out.print("\n👉 Escolha uma opção: ");

                int opcao = Integer.parseInt(sc.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.println("\n==============================");
                        System.out.println("💡 Digite '.' para sair da pergunta e voltar ao menu");
                        System.out.println("==============================\n");
                        System.out.print(paciente.getNome() + ": ");
                        String pergunta = sc.nextLine();
                        if (!pergunta.equals(".")) {
                            bot.responderMensagem(pergunta);
                        }
                        break;

                    case 2:
                        System.out.print("✏️  Digite a pergunta: ");
                        String novaPergunta = sc.nextLine();
                        System.out.print("💬 Digite a resposta: ");
                        String novaResposta = sc.nextLine();
                        System.out.print("🏷️  Digite a categoria (ou deixe vazio): ");
                        String categoria = sc.nextLine();

                        if (categoria.isEmpty()) categoria = "Sem categoria";

                        suporte.cadastrarResposta(bot, novaPergunta, novaResposta, categoria);
                        break;

                    case 3:
                        System.out.println("⚠️ Liste as mensagens (opção 5) para ver o ID antes de atualizar!");
                        System.out.print("Digite o ID da mensagem que quer atualizar: ");
                        int idAtualizar = Integer.parseInt(sc.nextLine());
                        System.out.print("Digite a nova resposta: ");
                        String respostaAtualizada = sc.nextLine();
                        suporte.atualizarResposta(bot, idAtualizar, respostaAtualizada);
                        break;

                    case 4:
                        System.out.println("⚠️ Liste as mensagens (opção 5) para ver o ID antes de deletar!");
                        System.out.print("Digite o ID da mensagem que quer deletar: ");
                        int idDeletar = Integer.parseInt(sc.nextLine());
                        suporte.deletarResposta(bot, idDeletar);
                        break;

                    case 5:
                        List<?> mensagens = bot.getMensagens();
                        System.out.println("\n==============================");
                        System.out.println("📜 Mensagens Cadastradas");
                        System.out.println("==============================");

                        for (var m : mensagens) {
                            System.out.println(m);
                        }
                        break;

                    case 0:
                        rodando = false;
                        break;
                }
            }

            System.out.println("\n=============================");
            System.out.println("👋 Tchau, o Nori está de saída!");
            System.out.println("🤖 ChatBot encerrado. Até a próxima!");
            System.out.println("==============================\n");

        } catch (Exception e) {
            System.out.println("😕 Ops! Algo deu errado...");
            e.printStackTrace();
        }
    }
}
