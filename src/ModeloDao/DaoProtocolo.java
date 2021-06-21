/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansProtocolo;

import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class DaoProtocolo {

    // chama conexao
    ConexaoBD conex = new ConexaoBD();

    // Chama beans
    BeansProtocolo beans = new BeansProtocolo();

    // Evento salvar
    public void Processar(BeansProtocolo beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("INSERT INTO public.tbl_protocolo(\n"
                    + "               protocolo_id_fornecedor, protocolo_data, protocolo_hora, \n"
                    + "            protocolo_numero,   protocolo_observacao, protocolo_total, protocolo_status, protocolo_id_usuario)\n"
                    + "    VALUES ( ?, ?, ?, ?, \n"
                    + "            ?, ?, ?, ?);");

            pst.setInt(1, beans.getprotocolo_id_fornecedor());
            pst.setString(2, beans.getprotocolo_data());
            pst.setString(3, beans.getprotocolo_hora());
            pst.setString(4, beans.getprotocolo_numero());
            pst.setString(5, beans.getprotocolo_observacao());
            pst.setDouble(6, beans.getprotocolo_total());
            pst.setInt(7, beans.getprotocolo_status());
            pst.setInt(8, beans.getprotocolo_id_usuario());

            pst.execute();
//            JOptionPane.showMessageDialog(null, "Cfop cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void ProcessarAlteracao(BeansProtocolo beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_protocolo\n"
                    + "   SET    protocolo_id_fornecedor=?, protocolo_numero=?, \n"
                    + "        protocolo_observacao=?, protocolo_total=?, protocolo_hora=?, protocolo_data=?, \n"
                    + "       protocolo_status=?, protocolo_id_usuario=?\n"
                    + " WHERE id_protocolo=?;");

            pst.setInt(1, beans.getprotocolo_id_fornecedor());
            pst.setString(2, beans.getprotocolo_numero());
            pst.setString(3, beans.getprotocolo_observacao());
            pst.setDouble(4, beans.getprotocolo_total());
            pst.setString(5, beans.getprotocolo_hora());
            pst.setString(6, beans.getprotocolo_data());
            pst.setInt(7, beans.getprotocolo_status());
            pst.setInt(8, beans.getprotocolo_id_usuario());
            pst.setInt(9, beans.getId_protocolo());

            pst.execute();
//            JOptionPane.showMessageDialog(null, "Cfop cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void Validar(BeansProtocolo beans) {
        conex.conexao();
        try {
            // salva no banco  desde que seja nulo t_item_id e  t_item_st=1
            java.sql.PreparedStatement pst = conex.con.prepareStatement("INSERT INTO public.tbl_item(\n"
                    + "             item_produto_id, item_complemento, item_qtd, item_protocolo_id)\n"
                    + "    SELECT  t_produto_id, t_complemento, t_qtd, item_id_protocolo \n"
                    + "  FROM public.tbl_item_temp where t_item_id isnull and t_item_st=1 and item_id_protocolo =? ");
            pst.setInt(1, beans.getId_protocolo());
            pst.execute();
            //   JOptionPane.showMessageDialog(null, "Salvou no banco -- " + beans.getId_protocolo());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro salvar . \n\n" + ex);
            System.err.println("SQLException salva " + ex);
        }
        try {
            // atualiza no banco  desde que seja nao nulo t_item_id e  t_item_st=2
            java.sql.PreparedStatement pst = conex.con.prepareStatement("UPDATE public.tbl_item\n"
                    + "   SET   item_produto_id=t_produto_id, item_complemento=t_complemento, item_qtd=t_qtd, \n"
                    + "       item_protocolo_id=item_id_protocolo\n"
                    + "         FROM public.tbl_item_temp \n"
                    + "  where t_item_id = item_id  and t_item_st=2 and item_id_protocolo =? ");
            pst.setInt(1, beans.getId_protocolo());
            pst.execute();
            // JOptionPane.showMessageDialog(null, "Atualizou no banco -- " + beans.getId_protocolo());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro atualiza . \n\n" + ex);
            System.err.println("SQLException atualiza " + ex);
        }

        // deleta no banco  desde que seja nao nulo t_item_id e  t_item_st=3
        int selecao = beans.getId_protocolo();
        conex.executaSql2(" SELECT id, t_produto_id, t_complemento, t_qtd, item_id_protocolo, t_item_st, t_item_id  FROM public.tbl_item_temp "
                + "where item_id_protocolo=" + selecao + " and t_item_st=3  order by id  desc");
        try {
            conex.rs.first();
            do {
                String delete = conex.rs.getString("t_item_id");

                java.sql.PreparedStatement pst = conex.con.prepareStatement("DELETE FROM public.tbl_item WHERE item_id=" + delete + " ");
                pst.execute();
                //JOptionPane.showMessageDialog(null, "Deletou no banco -- id protocolo = " + selecao+"id item = "+delete);
            } while (conex.rs.next());
            JOptionPane.showMessageDialog(null, "dao protocolo ok");
        } catch (SQLException ex) {
            System.err.println("SQLException delete " + ex);
//             JOptionPane.showMessageDialog(null, "dao protocolo erro \n"+ex);
        }

        conex.desconecta();
    }

    public void AtualizaObs(BeansProtocolo beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_protocolo   SET  protocolo_observacao=?  WHERE id_protocolo=?;");
            pst.setString(1, beans.getprotocolo_observacao());
            pst.setInt(2, beans.getId_protocolo());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Obs atualiza com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQLException Obs atualiza -- " + ex);
        }
        conex.desconecta();
    }

    public void PuxaDados(BeansProtocolo beans) {
        conex.conexao();
        try {
            // salva no banco  desde que seja nulo t_item_id e  t_item_st=1
            java.sql.PreparedStatement pst = conex.con.prepareStatement("INSERT INTO public.tbl_item_temp(\n"
                    + "                    t_produto_id,t_complemento ,t_qtd ,item_id_protocolo,t_item_st,t_item_id )\n"
                    + "                     SELECT  item_produto_id, item_complemento,item_qtd , item_protocolo_id ,1,item_id\n"
                    + "                    FROM public.tbl_item where  item_protocolo_id =?   ");
            pst.setInt(1, beans.getId_protocolo());
            pst.execute();
            //  JOptionPane.showMessageDialog(null, "Puxou no banco -- " + beans.getId_protocolo());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao puxar . \n\n" + ex);
            System.err.println("SQLException puxar " + ex);
        }
        conex.desconecta();
    }

    public void LimpaValidar(BeansProtocolo beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" DELETE FROM public.tbl_item_temp WHERE item_id_protocolo=?");

            pst.setInt(1, beans.getId_protocolo());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Limpa validar com sucesso. "+ beans.getId_protocolo());
        } catch (SQLException ex) {
            // JOptionPane.showMessageDialog(null, "SQLException --Limpa validar -- " + ex);
        }
        conex.desconecta();
    }

    public void ValidaStatusprotocolo(BeansProtocolo beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_protocolo   SET  protocolo_status=?  WHERE id_protocolo=?;");
            pst.setInt(1, beans.getprotocolo_status());
            pst.setInt(2, beans.getId_protocolo());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Obs atualiza com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQLException Obs atualiza -- " + ex);
        }
        conex.desconecta();
    }

    public void ZeraTabelaItemTemp(BeansProtocolo beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" TRUNCATE TABLE tbl_item_temp RESTART IDENTITY;");
            pst.execute();
        } catch (SQLException ex) {
        }
        conex.desconecta();
    }
}
