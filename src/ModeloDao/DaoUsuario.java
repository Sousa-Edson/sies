/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansUsuario;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class DaoUsuario {

    // chama conexao
    ConexaoBD conex = new ConexaoBD();

    // Chama beans
    BeansUsuario beans = new BeansUsuario();

    // Evento salvar
    public void Salvar(BeansUsuario beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" INSERT INTO  tbl_usuario( permissao_usuario, nome_usuario, \n"
                    + "            login_usuario, senha_usuario,status_usuario )"
                    + "    VALUES ( ?, ?, ?, ?, ?);");

            pst.setInt(1, beans.getPermissao());
            pst.setString(2, beans.getNome());
            pst.setString(3, beans.getLogin());
            pst.setString(4, beans.getSenha());
            pst.setInt(5, beans.getStatus());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void Editar(BeansUsuario beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("UPDATE public.tbl_usuario\n"
                    + "   SET  permissao_usuario=?, nome_usuario=?, login_usuario=?, \n"
                    + "       senha_usuario=?,   status_usuario=?\n"
                    + " WHERE id_usuario=?;");
            pst.setInt(1, beans.getPermissao());
            pst.setString(2, beans.getNome());
            pst.setString(3, beans.getLogin());
            pst.setString(4, beans.getSenha());
            pst.setInt(5, beans.getStatus());
            pst.setInt(6, beans.getIdUsuario());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Alterado");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public void UpdateStatus(BeansUsuario beans) {

        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE tbl_usuario"
                    + "   SET status_usuario=?"
                    + " WHERE id_usuario=?;");

            pst.setInt(1, beans.getStatus());

            pst.setInt(2, beans.getIdUsuario());

            pst.execute();
//            JOptionPane.showMessageDialog(null, "Alterado");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public void ExcluirItem(BeansUsuario beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" DELETE FROM public.tbl_usuario\n"
                    + "WHERE id_usuario=?;");
            pst.setInt(1, beans.getIdUsuario());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Item " + beans.getIdUsuario()+ " excluido com sucesso .");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro \n" + ex);
        }
        conex.desconecta();
    }

}
