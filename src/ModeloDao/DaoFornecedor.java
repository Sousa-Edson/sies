/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansFornecedor;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class DaoFornecedor {

    // chama conexao
    ConexaoBD conex = new ConexaoBD();

    // Chama beans
    BeansFornecedor beans = new BeansFornecedor();

    // Evento salvar
    public void Salvar(BeansFornecedor beans) {
        conex.conexao();
        try {

            java.sql.PreparedStatement pst = conex.con.prepareStatement(" INSERT INTO public.tbl_fornecedor(\n"
                    + "               fornecedor_nome, fornecedor_cnpj, \n"
                    + "            fornecedor_inscricao, fornecedor_descricao, fornecedor_telefone, \n"
                    + "            fornecedor_endereco, fornecedor_no, fornecedor_cep, fornecedor_complemento, \n"
                    + "            fornecedor_bairro, fornecedor_cidade, fornecedor_observacao, \n"
                    + "            fornecedor_status, fornecedor_usuario_id)\n"
                    + "    VALUES ( ?, ?, ?, \n"
                    + "            ?, ?, ?, \n"
                    + "            ?, ?, ?, ?, \n"
                    + "            ?, ?, ?, \n"
                    + "            ?); ");

            pst.setString(1, beans.getFornecedor_nome());
            pst.setString(2, beans.getFornecedor_cnpj());
            pst.setString(3, beans.getFornecedor_inscricao());
            pst.setString(4, beans.getFornecedor_descricao());
            pst.setString(5, beans.getFornecedor_telefone());
            pst.setString(6, beans.getFornecedor_endereco());
            pst.setString(7, beans.getFornecedor_no());
            pst.setString(8, beans.getFornecedor_cep());
            pst.setString(9, beans.getFornecedor_complemento());
            pst.setString(10, beans.getFornecedor_bairro());
            pst.setString(11, beans.getFornecedor_cidade());
            pst.setString(12, beans.getFornecedor_observacao());
            pst.setInt(13, beans.getFornecedor_status());
            pst.setInt(14, beans.getFornecedor_usuario_id());

            pst.execute();
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void Editar(BeansFornecedor beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_fornecedor\n"
                    + "   SET   fornecedor_nome=?, fornecedor_cnpj=?, fornecedor_inscricao=?, \n"
                    + "       fornecedor_descricao=?, fornecedor_telefone=?, fornecedor_endereco=?, \n"
                    + "       fornecedor_no=?, fornecedor_cep=?, fornecedor_complemento=?, \n"
                    + "       fornecedor_bairro=?, fornecedor_cidade=?, fornecedor_observacao=?, \n"
                    + "        fornecedor_status=?, fornecedor_usuario_id=?\n"
                    + " WHERE fornecedor_id=?;");
            pst.setString(1, beans.getFornecedor_nome());
            pst.setString(2, beans.getFornecedor_cnpj());
            pst.setString(3, beans.getFornecedor_inscricao());
            pst.setString(4, beans.getFornecedor_descricao());
            pst.setString(5, beans.getFornecedor_telefone());
            pst.setString(6, beans.getFornecedor_endereco());
            pst.setString(7, beans.getFornecedor_no());
            pst.setString(8, beans.getFornecedor_cep());
            pst.setString(9, beans.getFornecedor_complemento());
            pst.setString(10, beans.getFornecedor_bairro());
            pst.setString(11, beans.getFornecedor_cidade());
            pst.setString(12, beans.getFornecedor_observacao());
            pst.setInt(13, beans.getFornecedor_status());
            pst.setInt(14, beans.getFornecedor_usuario_id());
            pst.setInt(15, beans.getFornecedor_id());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Alterado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public void UpdateStatus(BeansFornecedor beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE tbl_fornecedor   SET fornecedor_status=? WHERE fornecedor_id=?;");
            pst.setInt(1, beans.getFornecedor_status());
            pst.setInt(2, beans.getFornecedor_id());
            pst.execute();
//            JOptionPane.showMessageDialog(null, "Alterado");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public void ExcluirItem(BeansFornecedor beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("  DELETE FROM public.tbl_fornecedor WHERE fornecedor_id=?;");
            pst.setInt(1, beans.getFornecedor_id());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Item " + beans.getFornecedor_id() + " excluido com sucesso .");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro  \n excuir\n" + ex);
        }
        conex.desconecta();
    }

    public ArrayList<BeansFornecedor> PreencheFornecedor() {
        ArrayList fornecedores = new ArrayList();
        conex.conexao();
        conex.executaSql2(" SELECT *  FROM tbl_fornecedor where   fornecedor_status=1    order by fornecedor_id asc");
        try {
            conex.rs.first();
            do {
                BeansFornecedor fornecedor = new BeansFornecedor();
                fornecedor.setFornecedor_nome(conex.rs.getString("fornecedor_nome"));
                fornecedor.setFornecedor_id(Integer.parseInt(conex.rs.getString("fornecedor_id")));
                fornecedores.add(fornecedor);
            } while (conex.rs.next());
        } catch (SQLException ex) {
        }
        conex.desconecta();
        return fornecedores;
    }
    public ArrayList<BeansFornecedor> PreencheFornecedor2(int a) {
        ArrayList fornecedores = new ArrayList();
        conex.conexao();
        conex.executaSql2(" SELECT *  FROM tbl_fornecedor where   fornecedor_id="+a+"    order by fornecedor_id asc");
        try {
            conex.rs.first();
            do {
                BeansFornecedor fornecedor = new BeansFornecedor();
                fornecedor.setFornecedor_nome(conex.rs.getString("fornecedor_nome"));
                fornecedor.setFornecedor_id(Integer.parseInt(conex.rs.getString("fornecedor_id")));
                fornecedores.add(fornecedor);
            } while (conex.rs.next());
        } catch (SQLException ex) {
        }
        conex.desconecta();
        return fornecedores;
    }
    
    

}
