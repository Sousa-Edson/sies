/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansNota;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class DaoNota {

    // chama conexao
    ConexaoBD conex = new ConexaoBD();

    // Evento salvar
    public void Processar(BeansNota beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("INSERT INTO public.tbl_nota(\n"
                    + "              nota_id_cfop, nota_id_fornecedor, nota_data, nota_hora, \n"
                    + "            nota_numero, nota_chave, nota_observacao, nota_total, nota_status, nota_id_usuario)\n"
                    + "    VALUES ( ?, ?, ?, ?, \n"
                    + "            ?, ?, ?, ?,?,?);");

            pst.setInt(1, beans.getNota_id_cfop());
            pst.setInt(2, beans.getNota_id_fornecedor());
            pst.setString(3, beans.getNota_data());
            pst.setString(4, beans.getNota_hora());
            pst.setString(5, beans.getNota_numero());
            pst.setString(6, beans.getNota_chave());
            pst.setString(7, beans.getNota_observacao());
            pst.setDouble(8, beans.getNota_total());
            pst.setInt(9, beans.getNota_status());
            pst.setInt(10, beans.getNota_id_usuario());

            pst.execute();
//            JOptionPane.showMessageDialog(null, "Cfop cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void ProcessarAlteracao(BeansNota beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_nota\n"
                    + "   SET  nota_id_cfop=?, nota_id_fornecedor=?, nota_numero=?, \n"
                    + "       nota_chave=?, nota_observacao=?, nota_total=?, nota_hora=?, nota_data=?, \n"
                    + "       nota_status=?, nota_id_usuario=?\n"
                    + " WHERE id_nota=?;");

            pst.setInt(1, beans.getNota_id_cfop());
            pst.setInt(2, beans.getNota_id_fornecedor());
            pst.setString(3, beans.getNota_numero());
            pst.setString(4, beans.getNota_chave());
            pst.setString(5, beans.getNota_observacao());
            pst.setDouble(6, beans.getNota_total());
            pst.setString(7, beans.getNota_hora());
            pst.setString(8, beans.getNota_data());
            pst.setInt(9, beans.getNota_status());
            pst.setInt(10, beans.getNota_id_usuario());
            pst.setInt(11, beans.getId_nota());

            pst.execute();
//            JOptionPane.showMessageDialog(null, "Cfop cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void AtualizaObs(BeansNota beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_nota   SET  nota_observacao=?  WHERE id_nota=?;");
            pst.setString(1, beans.getNota_observacao());
            pst.setInt(2, beans.getId_nota());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Obs atualiza com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQLException Obs atualiza -- " + ex);
        }
        conex.desconecta();
    }

    public void PuxaDados(BeansNota beans) {
        conex.conexao();
        try {
            // salva no banco  desde que seja nulo t_item_id e  t_item_st=1
            java.sql.PreparedStatement pst = conex.con.prepareStatement("INSERT INTO public.tbl_item_temp(\n"
                    + "                    t_produto_id,t_complemento ,t_qtd ,item_id_nota,t_item_st,t_item_id )\n"
                    + "                     SELECT  item_produto_id, item_complemento,item_qtd , item_nota_id ,1,item_id\n"
                    + "                    FROM public.tbl_item where  item_nota_id =?   ");
            pst.setInt(1, beans.getId_nota());
            pst.execute();
            //  JOptionPane.showMessageDialog(null, "Puxou no banco -- " + beans.getId_nota());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao puxar . \n\n" + ex);
            System.err.println("SQLException puxar " + ex);
        }
        conex.desconecta();
    }

    public ArrayList<BeansNota> Chama(String a) {
        ArrayList notas = new ArrayList();
        System.err.println("add linha 59");
        conex.conexao();
        conex.executaSql2(" SELECT *"
                + "  FROM public.tbl_nota where id_nota=" + a + "  \n"
                + "  order by id_nota  asc");
        try {
            BeansNota n = new BeansNota();
            conex.rs.last();
            n.setId_nota(conex.rs.getInt("id_nota"));
            n.setNota_id_cfop(conex.rs.getInt("nota_id_cfop"));
            n.setNota_id_fornecedor(conex.rs.getInt("nota_id_fornecedor"));
            n.setNota_data(conex.rs.getString("nota_hora"));
            n.setNota_numero(conex.rs.getString("nota_numero"));
            n.setNota_chave(conex.rs.getString("nota_chave"));
            n.setNota_observacao(conex.rs.getString("nota_observacao"));
            n.setNota_data(conex.rs.getString("nota_data"));
            notas.add(n);
            System.out.println("carregado");
        } catch (SQLException ex) {
            System.out.println("erro " + ex);
        }
        conex.desconecta();
        return notas;
    }

//    public void ZeraTabelaItemTemp(BeansNota beans) {
//        conex.conexao();
//        try {
//            java.sql.PreparedStatement pst = conex.con.prepareStatement(" TRUNCATE TABLE tbl_item_temp RESTART IDENTITY;");
//            pst.execute();
//        } catch (SQLException ex) {
//        }
//        conex.desconecta();
//    };
    public String CarregaUltimo() {
        String A = null;
        conex.conexao();
        conex.executaSql2(" SELECT * FROM public.tbl_nota  order by id_nota  asc");
        try {
            conex.rs.last();
            A = conex.rs.getString("id_nota");
            System.out.println("carregado - CarregaUltimo " + A);
        } catch (SQLException ex) {
            System.out.println("erro - CarregaUltimo " + ex);
        }
        conex.desconecta();
        return A;
    }

    public void ExcluirNota(BeansNota beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("  DELETE FROM public.tbl_item_temp WHERE item_id_nota =?;");
            pst.setInt(1, beans.getId_nota());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Item " + beans.getId_nota() + " excluido com sucesso .");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro  \n excuir 1\n" + ex);
        }
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("  DELETE FROM public.tbl_item WHERE item_nota_id=?;");
            pst.setInt(1, beans.getId_nota());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Item " + beans.getId_nota() + " excluido com sucesso .");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro  \n excuir 2\n" + ex);
        }
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("  DELETE FROM public.tbl_nota WHERE id_nota=?;");
            pst.setInt(1, beans.getId_nota());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Item " + beans.getId_nota() + " excluido com sucesso .");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro  \n excuir 3\n" + ex);
        }
        conex.desconecta();
    }

    public void ProcessarTotal(BeansNota beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_nota  SET   nota_total=? WHERE id_nota=?;");
            pst.setDouble(1, beans.getNota_total());
            pst.setInt(2, beans.getId_nota());
            pst.execute();
            JOptionPane.showMessageDialog(null, " cadastrar . \n" + beans.getId_nota() + "\n total \n" + beans.getNota_total());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void UpdateInformacoes(BeansNota beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_nota SET nota_informacao=?  WHERE id_nota=?");
            pst.setString(1, beans.getNota_infomacao());
            pst.setInt(2, beans.getId_nota());
            pst.execute();
//            JOptionPane.showMessageDialog(null, "Alterado getNota_infomacao   ="+beans.getNota_infomacao()+" beans.getId_nota() "+beans.getId_nota());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public String RetornaInformacao(String p) {
        String Rid = p;
        String texto = "";
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_nota  where id_nota ='" + Rid + "' order by id_nota asc ");
        try {
            conex.rs.first();
            texto = ((conex.rs.getString("nota_informacao")));
        } catch (SQLException ex) {

        }
        conex.desconecta();
        return texto;
    }

    public ArrayList<BeansNota> PreencheTabela(String a) {

        ArrayList dados = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_nota  inner join tbl_usuario on id_usuario =nota_id_usuario \n"
                + "inner join tbl_fornecedor on fornecedor_id =nota_id_fornecedor  inner join tbl_cfop on id_cfop =nota_id_cfop \n"
                + " where (coalesce((nota_numero)) ||' '||coalesce((nota_chave))"
                + "||' '||coalesce((nota_data))||' '||coalesce((sigla_cfop))||' '||coalesce((fornecedor_nome))"
                + "||' '||coalesce((nota_observacao))  )ilike '%" + a + "%'   order by id_nota  asc ");
        try {
            conex.rs.first();
            do {
                String status = conex.rs.getString("nota_status");
                if (status.equals("0")) {
                    status = "Inativo";
                } else if (status.equals("1")) {
                    status = "Ativo";
                } else if (status.equals("2")) {
                    status = "Alterado";
                } else if (status.equals("3")) {
                    status = "Excluido";
                } else if (status.equals("4")) {
                    status = "Edição";
                } else if (status.equals("5")) {
                    status = "Validado";
                } else {
                }
                String MenuMinhaData = conex.rs.getString("nota_registro");
                String MenuMeuUsuario = conex.rs.getString("nome_usuario");
                String MinhaObs = conex.rs.getString("nota_observacao");
                dados.add(new Object[]{conex.rs.getInt("id_nota"), status,
                    conex.rs.getString("sigla_cfop"), conex.rs.getString("nota_data"), conex.rs.getString("fornecedor_nome"),
                    conex.rs.getString("nota_numero"), MinhaObs,
                    MenuMeuUsuario + " - " + MenuMinhaData});

            } while (conex.rs.next());
        } catch (SQLException ex) {
            System.out.println("ModeloDao.DaoNota.PreencheTabela() - erro " + ex);
        }
        conex.desconecta();
        return dados;
    }

    public ArrayList<BeansNota> PreencheTabelaDashBoardNota(String a) {

        ArrayList dados = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT item_id,nota_data,protocolo_data,n.fornecedor_nome as forn_n, p.fornecedor_nome as forn_p, produto_nome, sigla_unidade, item_complemento, item_qtd, item_nota_id, \n"
                + "       item_protocolo_id, item_pedido_id, produto_valor, produto_descricao\n"
                + "  FROM public.tbl_item\n"
                + "  INNER JOIN tbl_produto on produto_id=item_produto_id \n"
                + "  INNER JOIN tbl_produto_unidade on produto_unidade=id_unidade\n"
                + "  LEFT  JOIN tbl_protocolo on id_protocolo=item_protocolo_id\n"
                + "LEFT JOIN tbl_nota on id_nota=item_nota_id\n"
                + "LEFT JOIN tbl_fornecedor as n on n.fornecedor_id=nota_id_fornecedor\n"
                + "LEFT JOIN tbl_fornecedor as p on p.fornecedor_id=protocolo_id_fornecedor\n"
                + "where item_pedido_id is null order by  item_id desc LIMIT 15");
        try {
            conex.rs.first();
            do {
                String nota_data = conex.rs.getString("nota_data");
                String protocolo_data = conex.rs.getString("protocolo_data");

                String forn_n = conex.rs.getString("forn_n");
                String forn_p = conex.rs.getString("forn_p");

                String item_id = conex.rs.getString("item_id");
                String item_nota_id = conex.rs.getString("item_nota_id");
                String item_protocolo_id = conex.rs.getString("item_protocolo_id");
                String produto_nome = conex.rs.getString("produto_nome");
                String sigla_unidade = conex.rs.getString("sigla_unidade");
                String item_complemento = conex.rs.getString("item_complemento");
                String item_qtd = conex.rs.getString("item_qtd");
                String produto_valor = conex.rs.getString("produto_valor");
                String produto_descricao = conex.rs.getString("produto_descricao");

                String data = null, fornecedor = null;
                if (item_nota_id != null) {
                    data = nota_data;
                    fornecedor = forn_n;
                }
                if (item_protocolo_id != null) {
                    data = protocolo_data;
                    fornecedor = forn_p;
                }

                String n = produto_nome + " " + item_complemento;
                dados.add(new Object[]{item_id, data, fornecedor, n, sigla_unidade, item_qtd});

            } while (conex.rs.next());
        } catch (SQLException ex) {
            System.out.println("PreencheTabela_UltimasEntradas - erro " + ex);
        }
        conex.desconecta();
        return dados;
    }

    public ArrayList<BeansNota> PreencheTabelaDashBoardExpedicao(String a) {

        ArrayList dados = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT item_id,c.cliente_nome as cliente, n.data as data_n, p.data as data_p, \n"
                + " produto_nome, sigla_unidade, item_complemento, item_qtd, item_nota_id, \n"
                + "                item_protocolo_id, item_pedido_id, produto_valor, produto_descricao\n"
                + "                FROM public.tbl_item\n"
                + "                INNER JOIN tbl_produto on produto_id=item_produto_id \n"
                + "                INNER JOIN tbl_produto_unidade on produto_unidade=id_unidade\n"
                + "                INNER  JOIN tbl_pedido on id_pedido=item_pedido_id\n"
                + "                INNER JOIN tbl_cliente as c on c.cliente_id=pedido_id_cliente\n"
                + "                left JOIN tbl_exp_nota as n on n.exp_pedido=id_pedido\n"
                + "                left JOIN tbl_exp_protocolo as p on p.exp_pedido=id_pedido\n"
                + "                \n"
                + "              where pedido_status = 9 order by  item_id desc LIMIT 15");
        try {
            conex.rs.first();
            do {
                String nota_data = conex.rs.getString("data_n");
                String protocolo_data = conex.rs.getString("data_p");

                String clie_n = conex.rs.getString("cliente");
                String clie_p = conex.rs.getString("cliente");

                String item_id = conex.rs.getString("item_id");
//                String item_nota_id = conex.rs.getString("item_nota_id");
//                String item_protocolo_id = conex.rs.getString("item_protocolo_id");
                String produto_nome = conex.rs.getString("produto_nome");
                String sigla_unidade = conex.rs.getString("sigla_unidade");
                String item_complemento = conex.rs.getString("item_complemento");
                String item_qtd = conex.rs.getString("item_qtd");
                String produto_valor = conex.rs.getString("produto_valor");
                String produto_descricao = conex.rs.getString("produto_descricao");

                String data = null, fornecedor = null;
//                if (item_nota_id != null) {
//                    data = nota_data;
//                    fornecedor = clie_n;
//                }
//                if (item_protocolo_id != null) {
                    data = protocolo_data;
                    fornecedor = clie_p;
                //}

                String n = produto_nome + " " + item_complemento;
                dados.add(new Object[]{item_id, data, fornecedor, n, sigla_unidade, item_qtd});

            } while (conex.rs.next());
        } catch (SQLException ex) {
            System.out.println("PreencheTabela_UltimasSaidas - erro " + ex);
        }
        conex.desconecta();
        return dados;
    }
}
