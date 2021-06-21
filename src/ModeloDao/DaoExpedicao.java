/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansExpedicao;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class DaoExpedicao {
    // / chama conexao

    ConexaoBD conex = new ConexaoBD();

    public void SalvarNota(BeansExpedicao beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" INSERT INTO public.tbl_exp_nota(\n"
                    + "              data, hora, numero, chave, motorista, placa, status, exp_pedido)\n"
                    + "    VALUES (  ?, ?, ?, ?, ?, ?, ?, ?);");
            pst.setString(1, beans.getExpData());
            pst.setString(2, beans.getExpHora());
            pst.setString(3, beans.getExpNota());
            pst.setString(4, beans.getExpChave());
            pst.setString(5, beans.getExpMotorista());
            pst.setString(6, beans.getExpPlaca());
            pst.setInt(7, beans.getExpStatus());
            pst.setInt(8, beans.getExpPedido());
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void AtualizarNota(BeansExpedicao beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_exp_nota\n"
                    + "   SET  data=?, hora=?, numero=?, chave=?, motorista=?, placa=?, \n"
                    + "       status=?, exp_pedido=?\n"
                    + " WHERE id=?;");
            pst.setString(1, beans.getExpData());
            pst.setString(2, beans.getExpHora());
            pst.setString(3, beans.getExpNota());
            pst.setString(4, beans.getExpChave());
            pst.setString(5, beans.getExpMotorista());
            pst.setString(6, beans.getExpPlaca());
            pst.setInt(7, beans.getExpStatus());
            pst.setInt(8, beans.getExpPedido());
            pst.setInt(9, beans.getId());
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar nota . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void SalvarProtocolo(BeansExpedicao beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("INSERT INTO public.tbl_exp_protocolo(\n"
                    + "             data, hora, numero, chave, motorista, placa, status, exp_pedido)\n"
                    + "    VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);");
            pst.setString(1, beans.getExpData());
            pst.setString(2, beans.getExpHora());
            pst.setString(3, beans.getExpNota());
            pst.setString(4, beans.getExpChave());
            pst.setString(5, beans.getExpMotorista());
            pst.setString(6, beans.getExpPlaca());
            pst.setInt(7, beans.getExpStatus());
            pst.setInt(8, beans.getExpPedido());
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void AtualizarProtocolo(BeansExpedicao beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_exp_protocolo\n"
                    + "   SET  data=?, hora=?, numero=?, chave=?, motorista=?, placa=?, \n"
                    + "       status=?, exp_pedido=?\n"
                    + " WHERE id=?;");
            pst.setString(1, beans.getExpData());
            pst.setString(2, beans.getExpHora());
            pst.setString(3, beans.getExpNota());
            pst.setString(4, beans.getExpChave());
            pst.setString(5, beans.getExpMotorista());
            pst.setString(6, beans.getExpPlaca());
            pst.setInt(7, beans.getExpStatus());
            pst.setInt(8, beans.getExpPedido());
            pst.setInt(9, beans.getId());
            pst.execute();
//            JOptionPane.showMessageDialog(null, "altera protocolo -BeansExpedicao "+beans.getId());
        } catch (SQLException ex) {
          
            JOptionPane.showMessageDialog(null, "Erro ao atualizar protocolo . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void AtualizaStatusPedido(BeansExpedicao beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("  UPDATE public.tbl_pedido\n"
                    + "   SET   pedido_observacao=? ,pedido_status=? "
                    + " WHERE id_pedido=?;");
            pst.setString(1, beans.getExpObservacao());
            pst.setInt(2, beans.getExpStatus());
            pst.setInt(3, beans.getExpPedido());
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public int UltimoProtocolo() {
        int ultimo = 0;
        conex.conexao();
        conex.executaSql2("SELECT id  FROM public.tbl_exp_protocolo order by id asc; ");
        try {
            conex.rs.last();
            do {
                ultimo = conex.rs.getInt("id");
                ultimo = ultimo + 1;
            } while (conex.rs.next());
        } catch (SQLException ex) {
            ultimo = 1;
            System.out.println("UltimoProtocolo - erro " + ex);
        }
        conex.desconecta();
        return ultimo;
    }

    public ArrayList<BeansExpedicao> BuscaExpedicao(int a) {

        ArrayList dados = new ArrayList();
        dados = BuscaExpNota(a);
        if (dados.isEmpty()) {
            // JOptionPane.showMessageDialog(null, "nota vazia");
            dados = BuscaExpProtocolo(a);
        }
        if (dados.isEmpty()) {
            //JOptionPane.showMessageDialog(null, "protocolo vazia");
            dados = null;
        }
        //dados = BuscaExpProtocolo(a);

        return dados;
    }

    public ArrayList<BeansExpedicao> BuscaExpNota(int a) {
        System.out.println("BuscaExpNota carregou - " + a);
        ArrayList dados = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT id, data, hora, numero, chave, motorista, placa, status, exp_pedido\n"
                + "  FROM public.tbl_exp_nota WHERE exp_pedido= " + a + " ");
        try {
            conex.rs.last();

            BeansExpedicao p = new BeansExpedicao();
            p.setExpData(conex.rs.getString("data"));
            p.setExpHora(conex.rs.getString("hora"));
            p.setExpChave(conex.rs.getString("chave"));
            p.setExpNota(conex.rs.getString("numero"));
            p.setExpMotorista(conex.rs.getString("motorista"));
            p.setExpPlaca(conex.rs.getString("placa"));
            p.setId(conex.rs.getInt("id"));
            p.setExpTipo("Nota");
            dados.add(p);
            System.out.println("dados " + dados);

        } catch (SQLException ex) {
            System.out.println("BuscaExpNota - erro " + ex);

        }
        conex.desconecta();
        return dados;
    }

    public ArrayList<BeansExpedicao> BuscaExpProtocolo(int a) {
        System.out.println("BuscaExpProtocolo carregou - " + a);
        ArrayList dados = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT id, data, hora, numero, chave, motorista, placa, status, exp_pedido\n"
                + "  FROM public.tbl_exp_protocolo WHERE exp_pedido= " + a + " ");
        try {
            conex.rs.last();

            BeansExpedicao p = new BeansExpedicao();
            p.setExpData(conex.rs.getString("data"));
            p.setExpHora(conex.rs.getString("hora"));
            p.setExpChave(conex.rs.getString("chave"));
            p.setExpNota(conex.rs.getString("numero"));
            p.setExpMotorista(conex.rs.getString("motorista"));
            p.setExpPlaca(conex.rs.getString("placa"));
            p.setId(conex.rs.getInt("id"));
            p.setExpTipo("Protocolo");
            dados.add(p);

        } catch (SQLException ex) {
            System.out.println("BuscaExpProtocolo - erro " + ex);
        }
        conex.desconecta();
        return dados;
    }
}
