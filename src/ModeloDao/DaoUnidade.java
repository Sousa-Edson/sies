/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansUnidade;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class DaoUnidade {

    // chama conexao
    ConexaoBD conex = new ConexaoBD();

    // Chama beans
    BeansUnidade beans = new BeansUnidade();

    // Evento salvar
    public void Salvar(BeansUnidade beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" INSERT INTO  tbl_produto_unidade( sigla_unidade, descricao_unidade, \n"
                    + "            frag_unidade, status_unidade ,registro_unidade_usuario)"
                    + "    VALUES ( ?, ?, ?, ?, ?);");
            pst.setString(1, beans.getSigla_unidade());
            pst.setString(2, beans.getDescricao_unidade());
            pst.setInt(3, beans.getFrag_unidade());
            pst.setInt(4, beans.getStatus_unidade());
            pst.setInt(5, beans.getId_usuario());
            pst.execute();
//            JOptionPane.showMessageDialog(null, "Unidade cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void Editar(BeansUnidade beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_produto_unidade\n"
                    + "   SET  sigla_unidade=?, descricao_unidade=?, frag_unidade=?, \n"
                    + "       status_unidade=?, registro_unidade_usuario=?\n"
                    + " WHERE id_unidade=?;");
            pst.setString(1, beans.getSigla_unidade());
            pst.setString(2, beans.getDescricao_unidade());
            pst.setInt(3, beans.getFrag_unidade());
            pst.setInt(4, beans.getStatus_unidade());
            pst.setInt(5, beans.getId_usuario());
            pst.setInt(6, beans.getId_unidade());
            pst.execute();
            //  JOptionPane.showMessageDialog(null, "Alterado - "+beans.getId_unidade());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public void UpdateStatus(BeansUnidade beans) {

        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE tbl_produto_unidade"
                    + "   SET status_unidade=?"
                    + " WHERE id_unidade=?;");

            pst.setInt(1, beans.getStatus_unidade());
            pst.setInt(2, beans.getId_unidade());
            pst.execute();
//            JOptionPane.showMessageDialog(null, "Alterado");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public void ExcluirItem(BeansUnidade beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" DELETE FROM public.tbl_produto_unidade where id_unidade=?;");
            pst.setInt(1, beans.getId_unidade());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Item " + beans.getId_unidade() + " excluido com sucesso .");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro \n" + ex);
        }
        conex.desconecta();
    }

    public ArrayList<BeansUnidade> PreencheTabelaA(String b) {
        String resposta = b;
        ArrayList dados = new ArrayList();

        conex.conexao();

        conex.executaSql2("SELECT *  FROM tbl_produto_unidade inner join public.tbl_usuario on id_usuario=registro_unidade_usuario where (coalesce((sigla_unidade)) ||' '||coalesce((descricao_unidade))ilike '%" + resposta + "%') and status_unidade=1    order by id_unidade asc ");
        try {
            conex.rs.first();
            do {
                String frag = conex.rs.getString("frag_unidade");
                String status = conex.rs.getString("status_unidade");
                if (frag.equals("0")) {
                    frag = "Não";
                } else if (frag.equals("1")) {
                    frag = "Sim";
                } else {
                    frag = "Outro";
                }
                if (status.equals("0")) {
                    status = "Inativo";
                } else if (status.equals("1")) {
                    status = "Ativo";
                } else if (status.equals("2")) {
                    status = "Alterado";
                } else if (status.equals("3")) {
                    status = "Excluido";

                } else {

                }
                String MenuMinhaData = conex.rs.getString("registro_unidade");
                String MenuMeuUsuario = conex.rs.getString("nome_usuario");
                dados.add(new Object[]{conex.rs.getInt("id_unidade"), status,
                    conex.rs.getString("sigla_unidade"), conex.rs.getString("descricao_unidade"), frag,
                    MenuMeuUsuario + " - " + MenuMinhaData});

            } while (conex.rs.next());
        } catch (SQLException ex) {
            // JOptionPane.showMessageDialog(rootPane, ex);
//            Logger.getLogger(UsuarioJIF.class.getName()).log(Level.SEVERE, null, ex);
        }
        conex.desconecta();
        return dados;

    }

    public ArrayList<BeansUnidade> PreencheTabelaB(String b) {
        String resposta = b;
        ArrayList dados = new ArrayList();

        conex.conexao();

        conex.executaSql2("SELECT *  FROM tbl_produto_unidade inner join public.tbl_usuario on id_usuario=registro_unidade_usuario where (coalesce((sigla_unidade)) ||' '||coalesce((descricao_unidade))ilike '%" + resposta + "%') and status_unidade=0    order by id_unidade asc ");
        try {
            conex.rs.first();
            do {
                String frag = conex.rs.getString("frag_unidade");
                String status = conex.rs.getString("status_unidade");
                if (frag.equals("0")) {
                    frag = "Não";
                } else if (frag.equals("1")) {
                    frag = "Sim";
                } else {
                    frag = "Outro";
                }
                if (status.equals("0")) {
                    status = "Inativo";
                } else if (status.equals("1")) {
                    status = "Ativo";
                } else if (status.equals("2")) {
                    status = "Alterado";
                } else if (status.equals("3")) {
                    status = "Excluido";

                } else {

                }
                String MenuMinhaData = conex.rs.getString("registro_unidade");
                String MenuMeuUsuario = conex.rs.getString("nome_usuario");
                dados.add(new Object[]{conex.rs.getInt("id_unidade"), status,
                    conex.rs.getString("sigla_unidade"), conex.rs.getString("descricao_unidade"), frag,
                    MenuMeuUsuario + " - " + MenuMinhaData});

            } while (conex.rs.next());
        } catch (SQLException ex) {
            // JOptionPane.showMessageDialog(rootPane, ex);
//            Logger.getLogger(UsuarioJIF.class.getName()).log(Level.SEVERE, null, ex);
        }
        conex.desconecta();
        return dados;

    }

    public ArrayList<BeansUnidade> PreencheUnidade() {
        ArrayList unidades = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT  id_unidade, descricao_unidade FROM tbl_produto_unidade  where status_unidade =1   order by id_unidade desc ");
        try {
            conex.rs.first();

            do {
                BeansUnidade unidade = new BeansUnidade();

                unidade.setDescricao_unidade(conex.rs.getString("descricao_unidade"));
                unidade.setId_unidade(Integer.parseInt(conex.rs.getString("id_unidade")));;

                unidades.add(unidade);
                System.out.println("--- " + unidade);
            } while (conex.rs.next());

        } catch (SQLException ex) {

        }

        conex.desconecta();

        return unidades;

    }

    public ArrayList<BeansUnidade> PreencheUnidadeB(String b) {
        ArrayList unidades = new ArrayList();
        String B = b;
        conex.conexao();
        conex.executaSql2("SELECT  id_unidade, descricao_unidade FROM tbl_produto_unidade  where id_unidade =" + B + "     order by id_unidade desc ");
        try {
            conex.rs.first();

            do {
                BeansUnidade unidade = new BeansUnidade();

                unidade.setDescricao_unidade(conex.rs.getString("descricao_unidade"));
                unidade.setId_unidade(Integer.parseInt(conex.rs.getString("id_unidade")));;

                unidades.add(unidade);
                System.out.println("--- " + unidade);
            } while (conex.rs.next());

        } catch (SQLException ex) {

        }
        conex.desconecta();
        return unidades;
    }

    public ArrayList<BeansUnidade> Chama(String a) {
        String A = a;
        System.out.println("A-- " + A);
        ArrayList unidades = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT  *  FROM tbl_produto_unidade where id_unidade ='" + A + "' order by id_unidade asc ");
        try {
            BeansUnidade unidade = new BeansUnidade();
            conex.rs.last();
            unidade.setId_unidade(conex.rs.getInt("id_unidade"));
            unidade.setSigla_unidade(conex.rs.getString("sigla_unidade"));
            unidade.setDescricao_unidade(conex.rs.getString("descricao_unidade"));
            unidade.setFrag_unidade(conex.rs.getInt("frag_unidade"));

            unidades.add(unidade);
        } catch (SQLException ex) {
            System.out.println("Uerro -- " + ex);
        }
        conex.desconecta();
        return unidades;

    }
}
