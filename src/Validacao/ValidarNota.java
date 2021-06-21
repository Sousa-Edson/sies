/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validacao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansNota;
import ModeloDao.DaoNota;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class ValidarNota {

    //chama conexao
    ConexaoBD conex = new ConexaoBD();
 String ValidaA = "item_id_nota", ValidaB = "item_nota_id";
    public void Validar(BeansNota beans) {
        conex.conexao();
        
        try {
            // salva no banco  desde que seja nulo t_item_id e  t_item_st=1
            java.sql.PreparedStatement pst = conex.con.prepareStatement("INSERT INTO public.tbl_item(\n"
                    + "             item_produto_id, item_complemento, item_qtd, " + ValidaB + ")\n"
                    + "    SELECT  t_produto_id, t_complemento, t_qtd, " + ValidaA + " \n"
                    + "  FROM public.tbl_item_temp where t_item_id isnull and t_item_st=1 and " + ValidaA + " =? ");
            pst.setInt(1, beans.getId_nota());
            pst.execute();
            //   JOptionPane.showMessageDialog(null, "Salvou no banco -- " + beans.getId_nota());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro salvar . \n\n" + ex);
            System.err.println("SQLException salva " + ex);
        }
        try {
            // atualiza no banco  desde que seja nao nulo t_item_id e  t_item_st=2
            java.sql.PreparedStatement pst = conex.con.prepareStatement("UPDATE public.tbl_item\n"
                    + "   SET   item_produto_id=t_produto_id, item_complemento=t_complemento, item_qtd=t_qtd, \n"
                   + "       " + ValidaB + "=" + ValidaA + "\n"
                    + "         FROM public.tbl_item_temp \n"
                    + "  where t_item_id = item_id  and t_item_st=2 and " + ValidaA + " =? ");
            pst.setInt(1, beans.getId_nota());
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro atualiza . \n\n" + ex);
            System.err.println("SQLException atualiza " + ex);
        }
        // deleta no banco  desde que seja nao nulo t_item_id e  t_item_st=3
        int selecao = beans.getId_nota();
        conex.executaSql2(" SELECT id, t_produto_id, t_complemento, t_qtd, item_id_nota, t_item_st, t_item_id  FROM public.tbl_item_temp "
                + "where  " + ValidaA + "=" + selecao + " and t_item_st=3  order by id  desc");
        try {
            conex.rs.first();
            do {
                String delete = conex.rs.getString("t_item_id");
                java.sql.PreparedStatement pst = conex.con.prepareStatement("DELETE FROM public.tbl_item WHERE item_id=" + delete + " ");
                pst.execute();
                //JOptionPane.showMessageDialog(null, "Deletou no banco -- id nota = " + selecao+"id item = "+delete);
            } while (conex.rs.next());
        } catch (SQLException ex) {
            System.err.println("SQLException delete " + ex);
        }
        conex.desconecta();
    }

    public void LimpaValidar(BeansNota beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" DELETE FROM public.tbl_item_temp WHERE " + ValidaA + "=?");
            pst.setInt(1, beans.getId_nota());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Limpa validar com sucesso. "+ beans.getId_nota());
        } catch (SQLException ex) {
            // JOptionPane.showMessageDialog(null, "SQLException --Limpa validar -- " + ex);
        }
        conex.desconecta();
    }

    public void VerificaValidacao(String a) {
        BeansNota beans = new BeansNota();
        
        System.out.println("chamou - VerificaValidacao em validar nota");
        conex.conexao();
        conex.executaSql2(" SELECT item_id ,item_nota_id FROM public.tbl_item  where " + ValidaB + "=" + a + "");
        try {
            conex.rs.first();
            beans.setId_nota(Integer.parseInt(conex.rs.getString("item_nota_id")));
            beans.setNota_status(5);
            ValidaStatusNota(beans);
            System.out.println("VerificaValidacao encontrou "+a+" item "+conex.rs.getString("item_id") );
        } catch (SQLException ex) {
            System.out.println("VerificaValidacao nao encontrou" + ex);
            beans.setId_nota(Integer.parseInt(a));
            beans.setNota_status(9);
            ValidaStatusNota(beans);
            //JOptionPane.showMessageDialog(rootPane, "VerificaValidacao - ex = " + ex);
        }
        conex.desconecta();
    }

    public void ValidaStatusNota(BeansNota beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_nota   SET  nota_status=?  WHERE id_nota=?;");
            pst.setInt(1, beans.getNota_status());
            pst.setInt(2, beans.getId_nota());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Obs atualiza com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQLException Obs atualiza -- " + ex);
        }
        conex.desconecta();
    }
}
