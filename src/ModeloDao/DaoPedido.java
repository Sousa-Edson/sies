/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansPedido;
import java.math.BigDecimal;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class DaoPedido {

    // chama conexao
    ConexaoBD conex = new ConexaoBD();

    // Evento salvar
    public void Processar(BeansPedido beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("INSERT INTO public.tbl_pedido(\n"
                    + "pedido_id_cliente, pedido_data, pedido_hora, pedido_observacao, pedido_total, pedido_status, pedido_id_usuario)\n"
                    + "    VALUES (  ?, ?, ?, \n"
                    + "            ?, ?, ?, ? );");

            pst.setInt(1, beans.getPedido_id_cliente());
            pst.setString(2, beans.getPedido_data());
            pst.setString(3, beans.getPedido_hora());
            pst.setString(4, beans.getPedido_observacao());
            pst.setDouble(5, beans.getPedido_total());
            pst.setInt(6, beans.getPedido_status());
            pst.setInt(7, beans.getPedido_id_usuario());

            pst.execute();
//            JOptionPane.showMessageDialog(null, "Cfop cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void ProcessarAlteracao(BeansPedido beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_pedido\n"
                    + "   SET    pedido_id_cliente=?, pedido_observacao=?, pedido_total=?, pedido_hora=?, pedido_data=?, \n"
                    + "       pedido_status=?, pedido_id_usuario=?\n"
                    + " WHERE id_pedido=?;");

            pst.setInt(1, beans.getPedido_id_cliente());
            pst.setString(2, beans.getPedido_observacao());
            pst.setDouble(3, beans.getPedido_total());
            pst.setString(4, beans.getPedido_hora());
            pst.setString(5, beans.getPedido_data());
            pst.setInt(6, beans.getPedido_status());
            pst.setInt(7, beans.getPedido_id_usuario());
            pst.setInt(8, beans.getId_pedido());

            pst.execute();
//            JOptionPane.showMessageDialog(null, "Cfop cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void AtualizaObs(BeansPedido beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_pedido   SET  pedido_observacao=?  WHERE id_pedido=?;");
            pst.setString(1, beans.getPedido_observacao());
            pst.setInt(2, beans.getId_pedido());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Obs atualiza com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQLException Obs atualiza -- " + ex);
        }
        conex.desconecta();
    }

    public void PuxaDados(BeansPedido beans) {
        conex.conexao();
        try {
            // salva no banco  desde que seja nulo t_item_id e  t_item_st=1
            java.sql.PreparedStatement pst = conex.con.prepareStatement("INSERT INTO public.tbl_item_temp(\n"
                    + "                    t_produto_id,t_complemento ,t_qtd ,item_id_pedido,t_item_st,t_item_id )\n"
                    + "                     SELECT  item_produto_id, item_complemento,item_qtd , item_pedido_id ,1,item_id\n"
                    + "                    FROM public.tbl_item where  item_pedido_id =?   ");
            pst.setInt(1, beans.getId_pedido());
            pst.execute();
            //  JOptionPane.showMessageDialog(null, "Puxou no banco -- " + beans.getId_pedido());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao puxar . \n\n" + ex);
            System.err.println("SQLException puxar " + ex);
        }
        conex.desconecta();
    }

    public ArrayList<BeansPedido> Chama(String a) {
        ArrayList notas = new ArrayList();
        System.err.println("add linha 59");
        conex.conexao();
        conex.executaSql2(" SELECT *"
                + "  FROM public.tbl_pedido where id_pedido=" + a + "  \n"
                + "  order by id_pedido  asc");
        try {
            BeansPedido n = new BeansPedido();
            conex.rs.last();
            n.setId_pedido(conex.rs.getInt("id_pedido"));
            n.setPedido_id_cliente(conex.rs.getInt("pedido_id_cliente"));
            n.setPedido_data(conex.rs.getString("pedido_hora"));
            n.setPedido_observacao(conex.rs.getString("pedido_observacao"));
            n.setPedido_data(conex.rs.getString("pedido_data"));
            notas.add(n);
            System.out.println("carregado");
        } catch (SQLException ex) {
            System.out.println("erro " + ex);
        }
        conex.desconecta();
        return notas;
    }

    public String CarregaUltimo() {
        String A = null;
        conex.conexao();
        conex.executaSql2(" SELECT * FROM public.tbl_pedido  order by id_pedido  asc");
        try {
            conex.rs.last();
            A = conex.rs.getString("id_pedido");
        } catch (SQLException ex) {
            System.out.println("erro " + ex);
        }
        conex.desconecta();
        return A;
    }

    public void ExcluirNota(BeansPedido beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("  DELETE FROM public.tbl_item_temp WHERE item_id_pedido =?;");
            pst.setInt(1, beans.getId_pedido());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Item " + beans.getId_pedido() + " excluido com sucesso .");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro  \n excuir 1\n" + ex);
        }
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("  DELETE FROM public.tbl_item WHERE item_pedido_id=?;");
            pst.setInt(1, beans.getId_pedido());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Item " + beans.getId_pedido() + " excluido com sucesso .");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro  \n excuir 2\n" + ex);
        }
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("  DELETE FROM public.tbl_pedido WHERE id_pedido=?;");
            pst.setInt(1, beans.getId_pedido());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Item " + beans.getId_pedido() + " excluido com sucesso .");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro  \n excuir 3\n" + ex);
        }
        conex.desconecta();
    }

    public void ProcessarTotal(BeansPedido beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_pedido  SET   pedido_total=? WHERE id_pedido=?;");
            pst.setDouble(1, beans.getPedido_total());
            pst.setInt(2, beans.getId_pedido());
            pst.execute();
            JOptionPane.showMessageDialog(null, " cadastrar . \n" + beans.getId_pedido() + "\n total \n" + beans.getPedido_total());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public ArrayList<BeansPedido> PreencheTabela(String a) {
        ArrayList dados = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_pedido  inner join tbl_usuario on id_usuario =pedido_id_usuario \n"
                + "inner join tbl_cliente on cliente_id =pedido_id_cliente  \n"
                + " where ( coalesce((pedido_data)) ||' '||coalesce((cliente_nome)) \n"
                + " ||' '||coalesce((pedido_observacao))  )ilike '%" + a + "%'    order by id_pedido  asc ");
        try {
            conex.rs.first();
            do {
                String status = conex.rs.getString("pedido_status");
                if (status.equals("0")) {
                    status = "Inativo";
                } else if (status.equals("1")) {
                    status = "Ativo";
                } else if (status.equals("2")) {
                    status = "Alterado";
                } else if (status.equals("3")) {
                    status = "Excluido";
                } else if (status.equals("9")) {
                    status = "Em edição";
                } else if (status.equals("8")) {
                    status = "Em produção";
                } else if (status.equals("7")) {
                    status = "Pronto";
                } else if (status.equals("6")) {
                    status = "Finalizado";
                } else {
                }
                String MenuMinhaData = conex.rs.getString("pedido_registro");
                String MenuMeuUsuario = conex.rs.getString("nome_usuario");
                String MinhaObs = conex.rs.getString("pedido_observacao");
                dados.add(new Object[]{conex.rs.getInt("id_pedido"), status,
                    conex.rs.getString("pedido_data"), conex.rs.getString("cliente_nome"),
                    MinhaObs,
                    MenuMeuUsuario + " - " + MenuMinhaData});

            } while (conex.rs.next());
        } catch (SQLException ex) {
            System.out.println("ModeloDao.DaoNota.PreencheTabela() - erro " + ex);
        }
        conex.desconecta();
        return dados;
    }

    public ArrayList<BeansPedido> DashBoard_PreencheTabelaA2(String a) {
        ArrayList dados = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_pedido  inner join tbl_usuario on id_usuario =pedido_id_usuario \n"
                + "inner join tbl_cliente on cliente_id =pedido_id_cliente  \n"
                + " where ( coalesce((pedido_data)) ||' '||coalesce((cliente_nome)) \n"
                + " ||' '||coalesce((pedido_observacao))  )ilike '%" + a + "%' and pedido_status !=9 and pedido_status !=8  and pedido_status !=7 and pedido_status !=6   order by id_pedido  asc ");
        try {
            conex.rs.first();
            do {
                String status = conex.rs.getString("pedido_status");
                if (status.equals("0")) {
                    status = "Inativo";
                } else if (status.equals("1")) {
                    status = "Ativo";
                } else if (status.equals("2")) {
                    status = "Alterado";
                } else if (status.equals("3")) {
                    status = "Excluido";
                } else if (status.equals("4")) {
                    status = "Em edição";
                } else if (status.equals("5")) {
                    status = "Em produção";
                } else if (status.equals("6")) {
                    status = "Pronto";
                } else if (status.equals("7")) {
                    status = "Finalizado";
                } else {
                }
                String MenuMinhaData = conex.rs.getString("pedido_registro");
                String MenuMeuUsuario = conex.rs.getString("nome_usuario");
                String MinhaObs = conex.rs.getString("pedido_observacao");
                dados.add(new Object[]{conex.rs.getInt("id_pedido"), status,
                    conex.rs.getString("pedido_data"), conex.rs.getString("cliente_nome"),
                    MinhaObs,
                    MenuMeuUsuario + " - " + MenuMinhaData});

            } while (conex.rs.next());
        } catch (SQLException ex) {
            System.out.println("PreencheTabela_Ativo - erro " + ex);
        }
        conex.desconecta();
        return dados;
    }

    public ArrayList<BeansPedido> DashBoard_PreencheTabela_Expedicao(String a) {
        ArrayList dados = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_pedido  inner join tbl_usuario on id_usuario =pedido_id_usuario \n"
                + "inner join tbl_cliente on cliente_id =pedido_id_cliente  \n"
                + " where ( coalesce((pedido_data)) ||' '||coalesce((cliente_nome)) \n"
                + " ||' '||coalesce((pedido_observacao))  )ilike '%" + a + "%' and pedido_status =8 or   pedido_status  =6   order by id_pedido  asc ");
        try {
            conex.rs.first();
            do {
                String status = conex.rs.getString("pedido_status");
                if (status.equals("0")) {
                    status = "Inativo";
                } else if (status.equals("1")) {
                    status = "Ativo";
                } else if (status.equals("2")) {
                    status = "Alterado";
                } else if (status.equals("3")) {
                    status = "Excluido";
                } else if (status.equals("4")) {
                    status = "Em edição";
                } else if (status.equals("5")) {
                    status = "Em produção";
                } else if (status.equals("6")) {
                    status = "Pronto";
                } else if (status.equals("7")) {
                    status = "Finalizado";
                } else if (status.equals("8")) {
                    status = "Aguardando";
                } else {
                }
                String MenuMinhaData = conex.rs.getString("pedido_registro");
                String MenuMeuUsuario = conex.rs.getString("nome_usuario");
                String MinhaObs = conex.rs.getString("pedido_observacao");
                dados.add(new Object[]{conex.rs.getInt("id_pedido"), status,
                    conex.rs.getString("pedido_data"), conex.rs.getString("cliente_nome"),
                    MinhaObs,
                    MenuMeuUsuario + " - " + MenuMinhaData});

            } while (conex.rs.next());
        } catch (SQLException ex) {
            System.out.println("PreencheTabela_Expedicao - erro " + ex);
        }
        conex.desconecta();
        return dados;
    }

    public void ProcessarExpedicao(BeansPedido beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_pedido\n"
                    + "   SET      pedido_status=? WHERE id_pedido=?;");
            pst.setInt(1, beans.getPedido_status());
            pst.setInt(2, beans.getId_pedido());
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ProcessarExpedicao . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void ProcessarRetornoParaEdicao(BeansPedido beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_pedido\n"
                    + "   SET      pedido_status=? WHERE id_pedido=?;");
            pst.setInt(1, beans.getPedido_status());
            pst.setInt(2, beans.getId_pedido());
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ProcessarExpedicao . \n\n" + ex);
        }
        conex.desconecta();
    }

    public ArrayList<BeansPedido> CarregaPedido(int a) {
        ArrayList dados = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT id_pedido, pedido_id_cliente, pedido_observacao, pedido_total, \n"
                + "       pedido_hora, pedido_data, pedido_status, pedido_id_usuario, pedido_registro\n"
                + "  FROM public.tbl_pedido WHERE id_pedido= " + a + " " );
        try {
            conex.rs.first();
            do {
                BeansPedido p = new BeansPedido();
                p.setId_pedido(conex.rs.getInt("id_pedido"));
                p.setPedido_id_cliente(conex.rs.getInt("pedido_id_cliente"));
                p.setPedido_observacao(conex.rs.getString("pedido_observacao"));
                p.setPedido_data(conex.rs.getString("pedido_data"));
                p.setPedido_hora(conex.rs.getString("pedido_hora"));
                p.setPedido_total(conex.rs.getDouble("pedido_total"));
                dados.add(p);
            } while (conex.rs.next());
        } catch (SQLException ex) {
            System.out.println("Erro " + ex);
        }
        conex.desconecta();
        return dados;

    }

    public String Total(double MeuTotal) {
        String A = "0";
        Double num4 = ((MeuTotal));
        BigDecimal df4 = new BigDecimal(num4);
        NumberFormat nf4 = NumberFormat.getInstance();// getCurrencyInstance
        nf4.setMinimumFractionDigits(4);
        nf4.setMaximumFractionDigits(4);
        String FormatoTotal = nf4.format(df4);
        FormatoTotal = "R$ " + FormatoTotal;
        A = FormatoTotal;
        return A;
    }
}
