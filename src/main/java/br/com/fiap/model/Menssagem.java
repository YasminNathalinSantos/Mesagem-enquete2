package br.com.fiap.model;

public class Menssagem {
    private int id;
    private String pergunta;
    private String resposta;
    private String categoria;


    public Menssagem(int id,String pergunta, String resposta, String categoria) {
        this.pergunta = pergunta;
        this.id = id;
        this.resposta = resposta;
        this.categoria = categoria;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "🆔 " + id +
                " | ❓ Pergunta: \"" + pergunta + "\"" +
                " | 💬 Resposta: \"" + resposta + "\"" +
                " | 🏷️ Categoria: " + (categoria != null ? categoria : "Sem categoria");
    }

}
