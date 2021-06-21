/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansKit;
import ModeloBeans.BeansProduto;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class DaoKit {
    // chama conexao

    ConexaoBD conex = new ConexaoBD();

    // Chama beans
    BeansKit beans = new BeansKit();

    //kit
    public ArrayList<BeansProduto> PreencheListaA(String a, String b) {
        ArrayList produtos = new ArrayList();
        String A = a;
        String B = b;
        conex.conexao();
        conex.executaSql2("SELECT produto_id, produto_nome, produto_unidade,produto_id\n"
                + "  FROM public.tbl_produto\n"
                + "inner join tbl_produto_unidade on id_unidade = produto_unidade"
                + "  WHERE  (coalesce((sigla_unidade)) ||' '||coalesce((produto_nome))"
                + "||' '||coalesce((produto_descricao))||' '||coalesce((produto_registro ))ilike '%" + B + "%') and "
                        + " produto_id   NOT IN (SELECT produto_kit  FROM  tbl_produto_kit where id_produto =" + A + ")  "
                + " order by produto_id asc");
        try {//where produto_nome ="+A+"   ///and produto_id="+A+"
            conex.rs.first();

            do {
                BeansProduto prod = new BeansProduto();

                prod.setProduto_nome(conex.rs.getString("produto_nome"));
                prod.setProduto_id(Integer.parseInt(conex.rs.getString("produto_id")));;

                produtos.add(prod);
            } while (conex.rs.next());

        } catch (SQLException ex) {
        }

        conex.desconecta();

        return produtos;

    }

    //kit
    public ArrayList<BeansProduto> PreencheListaB(String b) {
        ArrayList produtos = new ArrayList();
        String B = b;
        conex.conexao();
        conex.executaSql2("SELECT id_kits, id_produto, produto_kit,produto_nome,produto_id  FROM public. tbl_produto_kit\n"
                + "inner join tbl_produto on produto_kit= produto_id where id_produto=" + B + " and produto_id != id_produto order by id_kits desc ");
        try {//where produto_nome ="+A+" //and produto_id="+B+" 
            conex.rs.first();

            do {
                BeansProduto prod = new BeansProduto();

                prod.setProduto_nome(conex.rs.getString("produto_nome"));
                prod.setProduto_id(Integer.parseInt(conex.rs.getString("id_kits")));;

                produtos.add(prod);
            } while (conex.rs.next());

        } catch (SQLException ex) {
        }

        conex.desconecta();

        return produtos;

    }

    // Evento salvar
    public void Salvar(BeansKit beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("  INSERT INTO public. tbl_produto_kit(\n"
                    + "            id_produto, produto_kit)\n"
                    + "    VALUES (  ?, ?);");

            pst.setInt(1, beans.getId_produto());
            pst.setInt(2, beans.getProduto_kit());

            pst.execute();
//            JOptionPane.showMessageDialog(null, "Cfop cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

// Evento deletar
    public void Deletar(BeansKit beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("   DELETE FROM public. tbl_produto_kit WHERE id_kits=?;");

            pst.setInt(1, beans.getId_kits());

            pst.execute();
            //  JOptionPane.showMessageDialog(null, "Deletado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar . \n\n" + ex);
        }
        conex.desconecta();
    }
}
