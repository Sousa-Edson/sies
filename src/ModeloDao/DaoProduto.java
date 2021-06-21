/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansKit;
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
public class DaoProduto {

    // chama conexao
    ConexaoBD conex = new ConexaoBD();

    // Chama beans
    BeansProduto beans = new BeansProduto();

    // Evento salvar
    public void Salvar(BeansProduto beans) {
        conex.conexao();
        try {

            java.sql.PreparedStatement pst = conex.con.prepareStatement(" INSERT INTO public.tbl_produto(\n"
                    + "             produto_categoria, produto_nome, produto_unidade, produto_valor, produto_ncm, "
                    + "produto_min, produto_descricao, produto_status, produto_usuario_id)\n"
                    + "    VALUES ( ?, ?, ?, \n"
                    + "            ?, ?, ?, ?, ?, \n"
                    + "            ?); ");

            pst.setInt(1, beans.getProduto_categoria());
            pst.setString(2, beans.getProduto_nome());
            pst.setInt(3, beans.getProduto_unidade());
            pst.setDouble(4, beans.getProduto_valor());
            pst.setInt(5, beans.getProduto_ncm());
            pst.setDouble(6, beans.getProduto_min());
            pst.setString(7, beans.getProduto_descricao());
            pst.setInt(8, beans.getProduto_status());
            pst.setInt(9, beans.getProduto_usuario_id());

            pst.execute();
            // JOptionPane.showMessageDialog(null, "Cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();

        ExecutaKit();

    }

    public void ExecutaKit() {
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_produto  order by produto_id asc ");
        try {
            conex.rs.last();

            BeansProduto prod = new BeansProduto();
            int idprod = ((conex.rs.getInt("produto_id")));

            BeansKit Bkit = new BeansKit();
            DaoKit Dkit = new DaoKit();
            Bkit.setId_produto(idprod);
            Bkit.setProduto_kit(idprod);
            Dkit.Salvar(Bkit);

        } catch (SQLException ex) {
        }

        conex.desconecta();

    }

    public void Editar(BeansProduto beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE public.tbl_produto\n"
                    + "   SET   produto_categoria=?, produto_nome=?, produto_unidade=?, \n"
                    + "       produto_valor=?, produto_ncm=?, produto_min=?, produto_descricao=?, \n"
                    + "         produto_status=?, produto_usuario_id=?\n"
                    + " WHERE produto_id=?;");
            pst.setInt(1, beans.getProduto_categoria());
            pst.setString(2, beans.getProduto_nome());
            pst.setInt(3, beans.getProduto_unidade());
            pst.setDouble(4, beans.getProduto_valor());
            pst.setInt(5, beans.getProduto_ncm());
            pst.setDouble(6, beans.getProduto_min());
            pst.setString(7, beans.getProduto_descricao());
            pst.setInt(8, beans.getProduto_status());
            pst.setInt(9, beans.getProduto_usuario_id());
            pst.setInt(10, beans.getProduto_id());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Alterado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public void UpdateStatus(BeansProduto beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE tbl_produto"
                    + "   SET produto_status=?"
                    + " WHERE produto_id=?;");
            pst.setInt(1, beans.getProduto_status());
            pst.setInt(2, beans.getProduto_id());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Alterado  st ="+beans.getProduto_status());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public void ExcluirItem(BeansProduto beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("  DELETE FROM public.tbl_produto WHERE produto_id=?;");
            pst.setInt(1, beans.getProduto_id());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Item " + beans.getProduto_id() + " excluido com sucesso .");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro  \n excuir\n" + ex);
        }
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("  DELETE FROM public.tbl_kits WHERE id_produto=?;");
            pst.setInt(1, beans.getProduto_id());
            pst.execute();
            // JOptionPane.showMessageDialog(null, "Item " + beans.getProduto_id() + " excluido KIT com sucesso .");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro  \n excuir\n" + ex);
        }
        conex.desconecta();
    }

    public ArrayList<BeansProduto> PreencheTabelaA(String b) {
        String resposta = b;
        ArrayList dados = new ArrayList();

        conex.conexao();

        conex.executaSql2("SELECT *  FROM tbl_produto \n"
                + "inner join tbl_usuario on id_usuario = produto_usuario_id\n"
                + "inner join tbl_produto_unidade on id_unidade = produto_unidade\n"
                + " where (coalesce((sigla_unidade)) ||' '||coalesce((produto_nome))"
                + "||' '||coalesce((produto_descricao))||' '||coalesce((produto_registro ))ilike '%" + resposta + "%') and produto_status=1  \n"
                + "  order by produto_id  asc ");

        try {
            conex.rs.first();
            do {
                String status = conex.rs.getString("produto_status");
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
                String MenuMeuValor = conex.rs.getString("produto_valor");
                Double num4 = (Double.parseDouble(MenuMeuValor));
                BigDecimal df1 = new BigDecimal(num4);
                NumberFormat nf1 = NumberFormat.getInstance();// getCurrencyInstance
                nf1.setMinimumFractionDigits(4);
                nf1.setMaximumFractionDigits(4);
                String FormatoValor = nf1.format(df1);

                String MenuMinhaData = conex.rs.getString("produto_registro");
                String MenuMeuUsuario = conex.rs.getString("nome_usuario");
                int cat = conex.rs.getInt("produto_categoria");
                String categoria = "";
                if (cat == 0) {
                    categoria = "Uso interno";

                } else if (cat == 1) {
                    categoria = "Comun / Diversos";
                } else if (cat == 2) {
                    categoria = "Kit / Agrupado";
                } else {
                    categoria = "Erro";
                }
                dados.add(new Object[]{conex.rs.getInt("produto_id"), status,
                    categoria, conex.rs.getString("produto_nome"), conex.rs.getString("sigla_unidade"), FormatoValor,
                    conex.rs.getString("produto_descricao"), MenuMeuUsuario + " - " + MenuMinhaData});

            } while (conex.rs.next());
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rootPane, ex);
            System.err.println(" Erro -- " + ex);
            //Logger.getLogger(b).log(Level.SEVERE, null, ex);
        }
        conex.desconecta();
        return dados;

    }

    public ArrayList<BeansProduto> PreencheTabelaB(String b) {
        String resposta = b;
        ArrayList dados = new ArrayList();

        conex.conexao();

        conex.executaSql2("SELECT *  FROM tbl_produto \n"
                + "inner join tbl_usuario on id_usuario = produto_usuario_id\n"
                + "inner join tbl_produto_unidade on id_unidade = produto_unidade\n"
                + " where (coalesce((sigla_unidade)) ||' '||coalesce((produto_nome))"
                + "||' '||coalesce((produto_descricao))||' '||coalesce((produto_registro ))ilike '%" + resposta + "%') and produto_status=0  \n"
                + "  order by produto_id  asc ");

        try {
            conex.rs.first();
            do {
                String status = conex.rs.getString("produto_status");
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
                String MenuMeuValor = conex.rs.getString("produto_valor");
                Double num4 = (Double.parseDouble(MenuMeuValor));
                BigDecimal df1 = new BigDecimal(num4);
                NumberFormat nf1 = NumberFormat.getInstance();// getCurrencyInstance
                nf1.setMinimumFractionDigits(4);
                nf1.setMaximumFractionDigits(4);
                String FormatoValor = nf1.format(df1);

                String MenuMinhaData = conex.rs.getString("produto_registro");
                String MenuMeuUsuario = conex.rs.getString("nome_usuario");
                int cat = conex.rs.getInt("produto_categoria");
                String categoria = "";
                if (cat == 0) {
                    categoria = "Uso interno";

                } else if (cat == 1) {
                    categoria = "Comun / Diversos";
                } else if (cat == 2) {
                    categoria = "Kit / Agrupado";
                } else {
                    categoria = "Erro";
                }
                dados.add(new Object[]{conex.rs.getInt("produto_id"), status,
                    categoria, conex.rs.getString("produto_nome"), conex.rs.getString("sigla_unidade"), FormatoValor,
                    conex.rs.getString("produto_descricao"), MenuMeuUsuario + " - " + MenuMinhaData});

            } while (conex.rs.next());
        } catch (SQLException ex) {
            //Component rootPane = null;
            System.err.println(" Erro -- " + ex);
            //Logger.getLogger(b).log(Level.SEVERE, null, ex);
        }
        conex.desconecta();
        return dados;

    }

    public ArrayList<BeansProduto> Chama(String p) {
        String Rid = p;

        ArrayList produtos = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_produto"
                + " inner join tbl_usuario on id_usuario = produto_usuario_id "
                + "inner join tbl_produto_unidade on id_unidade = produto_unidade"
                + " where produto_id ='" + Rid + "' order by produto_id asc ");
        try {
            conex.rs.first();

            BeansProduto prod = new BeansProduto();
            prod.setProduto_id((conex.rs.getInt("produto_id")));
            prod.setProduto_categoria((conex.rs.getInt("produto_categoria")));
            prod.setProduto_nome((conex.rs.getString("produto_nome")));
            prod.setProduto_valor((conex.rs.getDouble("produto_valor")));
            prod.setProduto_ncm((conex.rs.getInt("produto_ncm")));
            prod.setProduto_min((conex.rs.getDouble("produto_min")));
            prod.setProduto_descricao((conex.rs.getString("produto_descricao")));
            prod.setProduto_unidade((conex.rs.getInt("produto_unidade")));
            prod.setProduto_unidade_desc((conex.rs.getString("descricao_unidade")));

            produtos.add(prod);

        } catch (SQLException ex) {
        }

        conex.desconecta();

        return produtos;

    }

    public void UpdateInformacoes(BeansProduto beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE tbl_produto"
                    + "   SET produto_informacao=?"
                    + " WHERE produto_id=?;");
            pst.setString(1, beans.getProduto_informacao());
            pst.setInt(2, beans.getProduto_id());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Alterado  ="+beans.getProduto_informacao());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public String RetornaInformacao(String p) {
        String Rid = p;
        String texto = "";
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_produto  where produto_id ='" + Rid + "' order by produto_id asc ");
        try {
            conex.rs.first();
            texto = ((conex.rs.getString("produto_informacao")));
        } catch (SQLException ex) {

        }
        conex.desconecta();
        return texto;
    }
}
