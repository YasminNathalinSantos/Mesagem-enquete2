package br.com.fiap.resource;

import br.com.fiap.app.MenssagemBanco;
import br.com.fiap.connection.Conexao;
import br.com.fiap.model.Menssagem;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.util.List;


@Path("/mensagens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MensagemResource {

    @GET
    public Response listarMensagens() {
        try (Connection con = new Conexao().getConnection()) {
            MenssagemBanco dao = new MenssagemBanco(con);
            List<Menssagem> lista = dao.listarMenssagens();
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Erro ao listar mensagens: " + e.getMessage()).build();
        }
    }


    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) {
        try (Connection con = new Conexao().getConnection()) {
            MenssagemBanco dao = new MenssagemBanco(con);
            Menssagem m = dao.buscarPorId(id);
            if (m == null) {
                return Response.status(404).entity("Mensagem não encontrada").build();
            }
            return Response.ok(m).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Erro ao buscar mensagem: " + e.getMessage()).build();
        }
    }


    @POST
    public Response cadastrarMensagem(Menssagem m) {
        try (Connection con = new Conexao().getConnection()) {
            MenssagemBanco dao = new MenssagemBanco(con);
            dao.adicionarMenssagem(m);
            return Response.status(201).entity(m).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(400).entity("Erro ao cadastrar mensagem: " + e.getMessage()).build();
        }
    }


    @PUT
    @Path("/{id}")
    public Response atualizarMensagem(@PathParam("id") int id, Menssagem m) {
        try (Connection con = new Conexao().getConnection()) {
            MenssagemBanco dao = new MenssagemBanco(con);
            Menssagem existente = dao.buscarPorId(id);
            if (existente == null) {
                return Response.status(404).entity("Mensagem não encontrada").build();
            }
            m.setId(id);
            dao.atualizarMenssagem(m);
            return Response.ok(m).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(400).entity("Erro ao atualizar mensagem: " + e.getMessage()).build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response deletarMensagem(@PathParam("id") int id) {
        try (Connection con = new Conexao().getConnection()) {
            MenssagemBanco dao = new MenssagemBanco(con);
            Menssagem existente = dao.buscarPorId(id);
            if (existente == null) {
                return Response.status(404).entity("Mensagem não encontrada").build();
            }
            dao.deletarMenssagem(id);
            return Response.status(204).build(); // No Content
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(400).entity("Erro ao deletar mensagem: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/buscar")
    public Response buscarPorPergunta(@QueryParam("pergunta") String pergunta) {
        try (Connection con = new Conexao().getConnection()) {
            MenssagemBanco dao = new MenssagemBanco(con);
            List<Menssagem> resultados = dao.buscarPorPergunta(pergunta);
            return Response.ok(resultados).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(400).entity("Erro ao buscar por pergunta: " + e.getMessage()).build();
        }
    }
}
