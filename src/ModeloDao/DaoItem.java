/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansItem;
import ModeloBeans.BeansProduto;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class DaoItem {

    // chama conexao
    ConexaoBD conex = new ConexaoBD();

    // Chama beans
   // BeansItem beans = new BeansItem();

      String item_id_campo;
    // Evento salvar
    public void Salvar(BeansItem beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("INSERT INTO public.tbl_item_temp(\n"
                    + "             t_produto_id, t_complemento, t_qtd,item_id_nota,t_item_st,item_id_protocolo, item_id_pedido)\n"
                    + "    VALUES ( ?, ?, ?, ?, ?,?,?);");

            pst.setInt(1, beans.getT_produto_id());
            pst.setString(2, beans.getT_complemento());
            pst.setDouble(3, beans.getT_qtd());
            pst.setInt(4, beans.getItem_id_nota());
            pst.setInt(5, beans.getT_item_st());
            pst.setInt(6, beans.getItem_id_protoloco());
            pst.setInt(7, beans.getItem_id_pedido());
            pst.execute();
            // JOptionPane.showMessageDialog(null, "Cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar linha 46 . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void Alterar(BeansItem beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("UPDATE public.tbl_item_temp\n"
                    + "   SET  t_produto_id=?, t_complemento=?, t_qtd=?, item_id_nota=?,t_item_st=? \n"
                    + " WHERE id=?;");

            pst.setInt(1, beans.getT_produto_id());
            pst.setString(2, beans.getT_complemento());
            pst.setDouble(3, beans.getT_qtd());
            pst.setInt(4, beans.getItem_id_nota());
            pst.setInt(5, beans.getT_item_st());
            pst.setInt(6, beans.getId());
            pst.execute();
            // JOptionPane.showMessageDialog(null, "Cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void Deletar(BeansItem beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("UPDATE public.tbl_item_temp\n"
                    + "   SET  t_item_st=? WHERE id=?;");

            pst.setInt(1, beans.getT_item_st());

            pst.setInt(2, beans.getId());
            pst.execute();
            // JOptionPane.showMessageDialog(null, "Cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public ArrayList<BeansProduto> PreencheListaA(String b) {
        ArrayList produtos = new ArrayList();
        String A = b;
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_produto \n"
                + "                inner join tbl_usuario on id_usuario = produto_usuario_id\n"
                + "                inner join tbl_produto_unidade on id_unidade = produto_unidade\n"
                + "                 where ( coalesce((produto_nome)) ||' '||coalesce((produto_descricao))\n"
                + "                ||' '||coalesce((descricao_unidade))||' '||coalesce((produto_registro ))ilike '%" + A + "%') and produto_status=1  \n"
                + "                 order by produto_id  desc");
        try {
            conex.rs.first();
            do {
                BeansProduto prod = new BeansProduto();
                prod.setProduto_nome(conex.rs.getString("produto_nome"));
                prod.setProduto_id(Integer.parseInt(conex.rs.getString("produto_id")));;
                System.out.println("ModeloDao.DaoProduto.PreencheListaA()" + conex.rs.getString("produto_nome"));
                produtos.add(prod);
            } while (conex.rs.next());

        } catch (SQLException ex) {
        }
        conex.desconecta();

        return produtos;

    }

    public ArrayList<BeansProduto> PreencheTabela(String a, String b) {
        
        item_id_campo=b;
System.out.println("PreencheTabela ----- item_id_campo - "+item_id_campo+" a "+a);
        ArrayList dados = new ArrayList();
        conex.conexao();
        String[] colunas = new String[]{"Codigo ", "Produto", "UN", "Valor Un", "Quantidade", "Sub total"};
        conex.executaSql2("SELECT * ,SUM ((produto_valor * t_qtd)) AS total FROM public.tbl_item_temp \n"
                + "                inner join public.tbl_produto on produto_id  =t_produto_id \n"
                + "                inner join public.tbl_produto_unidade on id_unidade =produto_unidade   where " + item_id_campo + " =" + a + " and t_item_st !=3 and t_item_st !=2 \n"
                + "                GROUP BY tbl_item_temp.id,tbl_produto.produto_id,tbl_produto_unidade.id_unidade, produto_valor");
        try {
            conex.rs.first();
            do {
                String MinhaQtd = conex.rs.getString("t_qtd");
                Double num1 = (Double.parseDouble(MinhaQtd));
                BigDecimal df1 = new BigDecimal(num1);
                NumberFormat nf1 = NumberFormat.getInstance();// getCurrencyInstance
                nf1.setMinimumFractionDigits(3);
                nf1.setMaximumFractionDigits(3);
                String FormatoQtd = nf1.format(df1);

                String MenuMeuValor = conex.rs.getString("produto_valor");
                Double num2 = (Double.parseDouble(MenuMeuValor));
                BigDecimal df2 = new BigDecimal(num2);
                NumberFormat nf2 = NumberFormat.getInstance();// getCurrencyInstance
                nf2.setMinimumFractionDigits(4);
                nf2.setMaximumFractionDigits(4);
                String FormatoValor = nf2.format(df2);
                FormatoValor = "R$ " + FormatoValor;

                String MenuSubTotal = conex.rs.getString("total");
                Double num3 = (Double.parseDouble(MenuSubTotal));
                BigDecimal df3 = new BigDecimal(num3);
                NumberFormat nf3 = NumberFormat.getInstance();// getCurrencyInstance
                nf3.setMinimumFractionDigits(4);
                nf3.setMaximumFractionDigits(4);
                String FormatoSubTotal = nf3.format(df3);
                FormatoSubTotal = "R$ " + FormatoSubTotal;

                System.out.println("FormatoSubTotal = " + FormatoSubTotal);
                String produto = conex.rs.getString("produto_nome") + " " + conex.rs.getString("t_complemento");
                dados.add(new Object[]{
                    conex.rs.getInt("id"), produto, conex.rs.getString("sigla_unidade"), FormatoValor,
                    FormatoQtd, FormatoSubTotal
                });

            } while (conex.rs.next());
        } catch (SQLException ex) {
            System.out.println("erro " + ex); System.err.println("----------------Erro " + ex);
        }
        conex.desconecta();
        return dados;
    }

    public String PreencherTotal(String a, String b) {
        String A = "0";
      item_id_campo=b;
        System.err.println("item_id_campo  = "+item_id_campo);
        System.out.println("chamou - PreencherTotal a - "+a);
        conex.conexao();
        conex.executaSql2(" SELECT SUM ((produto_valor * t_qtd)) AS total FROM public.tbl_item_temp \n"
                + "                inner join public.tbl_produto on produto_id  =t_produto_id \n"
                + "               where "+item_id_campo+"=" + a + " and t_item_st !=3 and t_item_st !=2\n"
                + "                GROUP BY tbl_item_temp."+item_id_campo+"");
        try {
            conex.rs.first();
            String MeuTotal = conex.rs.getString("total");
            Double num4 = (Double.parseDouble(MeuTotal));
            BigDecimal df4 = new BigDecimal(num4);
            NumberFormat nf4 = NumberFormat.getInstance();// getCurrencyInstance
            nf4.setMinimumFractionDigits(4);
            nf4.setMaximumFractionDigits(4);
            String FormatoTotal = nf4.format(df4);
            FormatoTotal = "R$ " + FormatoTotal;

            A = FormatoTotal;
        } catch (SQLException ex) {
            System.out.println("PreencherTotal " + ex);
        }
        conex.desconecta();
        return A;

    }

    public ArrayList<BeansItem> BuscaDados(String a) {
        ArrayList produtos = new ArrayList();
        conex.conexao();
        conex.executaSql2(" SELECT *  FROM tbl_produto  inner join tbl_item_temp on produto_id = t_produto_id where id=" + a + "  order by produto_id  desc");

        try {
            BeansItem p = new BeansItem();
            conex.rs.first();
            p.setT_produto_id(conex.rs.getInt("produto_id"));
            p.setP_descricao(conex.rs.getString("produto_nome"));

            String MinhaQtd = conex.rs.getString("t_qtd");
            Double num4 = (Double.parseDouble(MinhaQtd));
            BigDecimal df1 = new BigDecimal(num4);
            NumberFormat nf1 = NumberFormat.getInstance();// getCurrencyInstance
            nf1.setMinimumFractionDigits(3);
            nf1.setMaximumFractionDigits(3);
            String FormatoQtd = nf1.format(df1);
            p.setP_qtd(FormatoQtd);
            p.setId(conex.rs.getInt("id"));
            System.out.println("carregado");
            produtos.add(p);
        } catch (SQLException ex) {

            System.out.println("SQLException " + ex);
        }
        conex.desconecta();
        return produtos;

    }
}
