/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansImagem;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class DaoImagem {
    // chama conexao

    ConexaoBD conex = new ConexaoBD();

    // Chama beans
    BeansImagem beans = new BeansImagem();

    //evento listar
    public ArrayList<BeansImagem> PreencheListaI(String i) {
        ArrayList imagens = new ArrayList();
        String I = i;
        conex.conexao();
        conex.executaSql2("SELECT id_imagem, id_imagem_produto, caminho_imagem,nome_imagem\n"
                + "  FROM public.tbl_produto_imagem where id_imagem_produto=" + I + "  order by id_imagem desc ");
        try {//where produto_nome ="+A+" //and produto_id="+B+" 
            conex.rs.first();
            do {
                BeansImagem imagem = new BeansImagem();
                imagem.setId_imagem(conex.rs.getInt("id_imagem"));
                imagem.setCaminho_imagem(conex.rs.getString("caminho_imagem"));
                imagem.setNome_imagem(conex.rs.getString("nome_imagem"));
                System.err.println("lista - " + conex.rs.getString("caminho_imagem") + " id - " + I);
                imagens.add(imagem);
            } while (conex.rs.next());
        } catch (SQLException ex) {
            System.err.println("erro imagem " + ex);
        }
        conex.desconecta();
        return imagens;
    }

    // Evento salvar
    public void Salvar(BeansImagem beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("INSERT INTO public.tbl_produto_imagem(\n"
                    + "             id_imagem_produto, caminho_imagem,nome_imagem) VALUES ( ?,?, ?);");
            
            pst.setInt(1, beans.getId_imagem_produto());
            pst.setString(2, beans.getCaminho_imagem());
            pst.setString(3, beans.getNome_imagem());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Cadastrar ...");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    // Evento alterar
    public void Alterar(BeansImagem beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("UPDATE public.tbl_produto_imagem\n"
                    + "   SET  id_imagem_produto=?, caminho_imagem=?, nome_imagem=?\n"
                    + " WHERE id_imagem=?;");
            
            pst.setInt(1, beans.getId_imagem_produto());
            pst.setString(2, beans.getCaminho_imagem());
            pst.setString(3, beans.getNome_imagem());
            pst.setInt(4, beans.getId_imagem());
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

// Evento deletar
    public void Deletar(BeansImagem beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("   DELETE FROM public.tbl_produto_imagem WHERE id_imagem=?;");
            pst.setInt(1, beans.getId_imagem());
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar . \n\n" + ex);
        }
        conex.desconecta();
    }
    
}
