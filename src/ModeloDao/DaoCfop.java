/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansCfop;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class DaoCfop {

    // chama conexao
    ConexaoBD conex = new ConexaoBD();

    // Chama beans
    BeansCfop beans = new BeansCfop();

    // Evento salvar
    public void Salvar(BeansCfop beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" INSERT INTO  tbl_cfop( sigla_cfop, descricao_cfop, \n"
                    + "             status_cfop ,registro_cfop_usuario)"
                    + "    VALUES (   ?, ?, ?, ?);");

            pst.setString(1, beans.getSigla_cfop());
            pst.setString(2, beans.getDescricao_cfop().toUpperCase());
            pst.setInt(3, beans.getStatus_cfop());
            pst.setInt(4, beans.getId_usuario());

            pst.execute();
//            JOptionPane.showMessageDialog(null, "Cfop cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void Editar(BeansCfop beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_cfop\n"
                    + "   SET  sigla_cfop=?, descricao_cfop=?, status_cfop=?, registro_cfop_usuario=?\n"
                    + " WHERE id_cfop=?");
            pst.setString(1, beans.getSigla_cfop());
            pst.setString(2, beans.getDescricao_cfop().toUpperCase());
            pst.setInt(3, beans.getStatus_cfop());
            pst.setInt(4, beans.getId_usuario());
            pst.setInt(5, beans.getId_cfop());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Alterado");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public void UpdateStatus(BeansCfop beans) {

        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE tbl_cfop"
                    + "   SET status_cfop=?"
                    + " WHERE id_cfop=?;");
            pst.setInt(1, beans.getStatus_cfop());
            pst.setInt(2, beans.getId_cfop());
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public void ExcluirItem(BeansCfop beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("DELETE FROM public.tbl_cfop WHERE id_cfop=?;");
            pst.setInt(1, beans.getId_cfop());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Item " + beans.getId_cfop() + " excluido com sucesso .");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro \n" + ex);
        }
        conex.desconecta();
    }

    public ArrayList<BeansCfop> PreencheTabelaA(String b) {
        String resposta = b;
        ArrayList dados = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_cfop inner join public.tbl_usuario on id_usuario=registro_cfop_usuario where (coalesce((sigla_cfop)) ||' '||coalesce((descricao_cfop))ilike '%" + resposta + "%') and status_cfop=1    order by id_cfop asc ");
        try {
            conex.rs.first();
            do {
                String status = conex.rs.getString("status_cfop");
                if (status.equals("0")) {
                    status = "Inativo";
                } else if (status.equals("1")) {
                    status = "Ativo";
                } else if (status.equals("2")) {
                    status = "Alterado";
                } else if (status.equals("3")) {
                    status = "Excluido";
                }
                String MenuMinhaData = conex.rs.getString("registro_cfop");
                String MenuMeuUsuario = conex.rs.getString("nome_usuario");
                dados.add(new Object[]{conex.rs.getInt("id_cfop"), status,
                    conex.rs.getString("sigla_cfop"), conex.rs.getString("descricao_cfop"), MenuMeuUsuario + " - " + MenuMinhaData});
            } while (conex.rs.next());
        } catch (SQLException ex) {
            System.out.println("Erro "+ex);
        }
        conex.desconecta();
        return dados;
    }

    public ArrayList<BeansCfop> PreencheTabelaB(String b) {
        String resposta = b;
        ArrayList dados = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_cfop inner join public.tbl_usuario on id_usuario=registro_cfop_usuario where (coalesce((sigla_cfop)) ||' '||coalesce((descricao_cfop))ilike '%" + resposta + "%') and status_cfop=0 "
                + "   order by id_cfop asc ");
        try {
            conex.rs.first();
            do {
                String status = conex.rs.getString("status_cfop");
                if (status.equals("0")) {
                    status = "Inativo";
                } else if (status.equals("1")) {
                    status = "Ativo";
                } else if (status.equals("2")) {
                    status = "Alterado";
                } else if (status.equals("3")) {
                    status = "Excluido";
                }
                String MenuMinhaData = conex.rs.getString("registro_cfop");
                String MenuMeuUsuario = conex.rs.getString("nome_usuario");
                dados.add(new Object[]{conex.rs.getInt("id_cfop"), status,
                    conex.rs.getString("sigla_cfop"), conex.rs.getString("descricao_cfop"), MenuMeuUsuario + " - " + MenuMinhaData});
            } while (conex.rs.next());
        } catch (SQLException ex) {
        }
        conex.desconecta();
        return dados;
    }

    public ArrayList<BeansCfop> Chama(String a) {
        String A = a;
        ArrayList cfops = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT  *  FROM tbl_cfop where id_cfop ='" + A + "' order by id_cfop asc ");
        try {
            BeansCfop cfop = new BeansCfop();
            conex.rs.last();
            cfop.setId_cfop(conex.rs.getInt("id_cfop"));
            cfop.setSigla_cfop(String.valueOf(conex.rs.getInt("sigla_cfop")));
            cfop.setDescricao_cfop(conex.rs.getString("descricao_cfop"));
            cfops.add(cfop);
        } catch (SQLException ex) {
            System.out.println("Uerro -- " + ex);
        }
        conex.desconecta();
        return cfops;
    }
    
     public ArrayList<BeansCfop> PreencheCfop() {
        ArrayList cfops = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_cfop where   status_cfop=1   order by id_cfop  asc ");
        try {
            conex.rs.first();
            do {
                BeansCfop cfop = new BeansCfop();
                cfop.setSigla_cfop(conex.rs.getString("sigla_cfop"));
                cfop.setId_cfop(Integer.parseInt(conex.rs.getString("id_cfop")));
                cfop.setDescricao_cfop(conex.rs.getString("descricao_cfop"));;
                cfops.add(cfop);
                System.out.println("--- " + cfop.getDescricao_cfop());
            } while (conex.rs.next());
        } catch (SQLException ex) {
        }
        conex.desconecta();
        return cfops;
    }
      public ArrayList<BeansCfop> PreencheCfop2(int a) {
        ArrayList cfops = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_cfop where id_cfop="+a+"   order by id_cfop  asc ");
        try {
            conex.rs.first();
            do {
                BeansCfop cfop = new BeansCfop();
                cfop.setSigla_cfop(conex.rs.getString("sigla_cfop"));
                cfop.setId_cfop(Integer.parseInt(conex.rs.getString("id_cfop")));
                cfop.setDescricao_cfop(conex.rs.getString("descricao_cfop"));;
                cfops.add(cfop);
                System.out.println("--- " + cfop.getDescricao_cfop());
            } while (conex.rs.next());
        } catch (SQLException ex) {
        }
        conex.desconecta();
        return cfops;
    }
    
    
}
