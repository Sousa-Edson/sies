/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansEmpresa;

import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class DaoEmpresa {

    // chama conexao
    ConexaoBD conex = new ConexaoBD();

    // Chama beans
    BeansEmpresa beans = new BeansEmpresa();

    // Evento salvar
    public void Salvar(BeansEmpresa beans) {
        conex.conexao();
        try {

            java.sql.PreparedStatement pst = conex.con.prepareStatement(" INSERT INTO public.tbl_empresa(\n"
                    + "             empresa_id, empresa_nome, empresa_cnpj, \n"
                    + "            empresa_inscricao, empresa_descricao, empresa_telefone, \n"
                    + "            empresa_endereco, empresa_no, empresa_cep, empresa_complemento, \n"
                    + "            empresa_bairro, empresa_cidade, empresa_observacao, \n"
                    + "            empresa_status, empresa_usuario_id)\n"
                    + "    VALUES (?, ?, ?, ?, \n"
                    + "            ?, ?, ?, \n"
                    + "            ?, ?, ?, ?, \n"
                    + "            ?, ?, ?, \n"
                    + "            ?); ");

            pst.setInt(1, beans.getEmpresa_id());
            pst.setString(2, beans.getEmpresa_nome());
            pst.setString(3, beans.getEmpresa_cnpj());
            pst.setString(4, beans.getEmpresa_inscricao());
            pst.setString(5, beans.getEmpresa_descricao());
            pst.setString(6, beans.getEmpresa_telefone());
            pst.setString(7, beans.getEmpresa_endereco());
            pst.setString(8, beans.getEmpresa_no());
            pst.setString(9, beans.getEmpresa_cep());
            pst.setString(10, beans.getEmpresa_complemento());
            pst.setString(11, beans.getEmpresa_bairro());
            pst.setString(12, beans.getEmpresa_cidade());
            pst.setString(13, beans.getEmpresa_observacao());
            pst.setInt(14, beans.getEmpresa_status());
            pst.setInt(15, beans.getEmpresa_usuario_id());

            pst.execute();
//            JOptionPane.showMessageDialog(null, "Unidade cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void Editar(BeansEmpresa beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_empresa\n"
                    + "   SET   empresa_id=?, empresa_nome=?, empresa_cnpj=?, \n"
                    + "       empresa_inscricao=?, empresa_descricao=?, empresa_telefone=?, \n"
                    + "       empresa_endereco=?, empresa_no=?, empresa_cep=?, empresa_complemento=?, \n"
                    + "       empresa_bairro=?, empresa_cidade=?, empresa_observacao=?, \n"
                    + "       empresa_status=?\n"
                    + " WHERE empresa_id=?;");
               pst.setInt(1, beans.getEmpresa_id());
            pst.setString(2, beans.getEmpresa_nome());
            pst.setString(3, beans.getEmpresa_cnpj());
            pst.setString(4, beans.getEmpresa_inscricao());
            pst.setString(5, beans.getEmpresa_descricao());
            pst.setString(6, beans.getEmpresa_telefone());
            pst.setString(7, beans.getEmpresa_endereco());
            pst.setString(8, beans.getEmpresa_no());
            pst.setString(9, beans.getEmpresa_cep());
            pst.setString(10, beans.getEmpresa_complemento());
            pst.setString(11, beans.getEmpresa_bairro());
            pst.setString(12, beans.getEmpresa_cidade());
            pst.setString(13, beans.getEmpresa_observacao());
            pst.setInt(14, beans.getEmpresa_status());
            pst.setInt(15, beans.getEmpresa_usuario_id());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Alterado");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public void UpdateStatus(BeansEmpresa beans) {

        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE tbl_empresa"
                    + "   SET empresa_status=?"
                    + " WHERE empresa_id_unico=?;");

            pst.setInt(1, beans.getEmpresa_status());

            pst.setInt(2, beans.getEmpresa_id());

            pst.execute();
//            JOptionPane.showMessageDialog(null, "Alterado");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public void ExcluirItem(BeansEmpresa beans) {
        conex.conexao();
        try {

            java.sql.PreparedStatement pst = conex.con.prepareStatement(" INSERT INTO public.tbl_empresa(\n"
                    + "             empresa_id, empresa_nome, empresa_cnpj, \n"
                    + "            empresa_inscricao, empresa_descricao, empresa_telefone, \n"
                    + "            empresa_endereco, empresa_no, empresa_cep, empresa_complemento, \n"
                    + "            empresa_bairro, empresa_cidade, empresa_observacao, \n"
                    + "            empresa_status, empresa_usuario_id)\n"
                    + "     SELECT  empresa_id, empresa_nome, empresa_cnpj, \n"
                    + "            empresa_inscricao, empresa_descricao, empresa_telefone, \n"
                    + "            empresa_endereco, empresa_no, empresa_cep, empresa_complemento, \n"
                    + "            empresa_bairro, empresa_cidade, empresa_observacao, \n"
                    + "             ?,  ?\n"
                    + "  FROM public.tbl_empresa where empresa_id_unico=?\n"
                    + ";");
            pst.setInt(1, beans.getEmpresa_status());
            pst.setInt(2, beans.getEmpresa_usuario_id());
            pst.setInt(3, beans.getEmpresa_id());
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro  \n excuir\n" + ex);
        }
        conex.desconecta();
    }

}
