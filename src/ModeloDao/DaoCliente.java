/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDao;

import ConectaBanco.ConexaoBD;
import ModeloBeans.BeansCliente;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class DaoCliente {

    // chama conexao
    ConexaoBD conex = new ConexaoBD();

    // Chama beans
    BeansCliente beans = new BeansCliente();

    // Evento salvar
    public void Salvar(BeansCliente beans) {
        conex.conexao();
        try {

            java.sql.PreparedStatement pst = conex.con.prepareStatement(" INSERT INTO public.tbl_cliente(\n"
                    + "              cliente_nome, cliente_cnpj, \n"
                    + "            cliente_inscricao, cliente_descricao, cliente_telefone, \n"
                    + "            cliente_endereco, cliente_no, cliente_cep, cliente_complemento, \n"
                    + "            cliente_bairro, cliente_cidade, cliente_observacao, \n"
                    + "            cliente_status, cliente_usuario_id)\n"
                    + "    VALUES (?, ?, ?, ?, \n"
                    + "            ?, ?, ?, \n"
                    + "            ?, ?, ?, ?, \n"
                    + "            ?, ?, ? ); ");

            pst.setString(1, beans.getCliente_nome());
            pst.setString(2, beans.getCliente_cnpj());
            pst.setString(3, beans.getCliente_inscricao());
            pst.setString(4, beans.getCliente_descricao());
            pst.setString(5, beans.getCliente_telefone());
            pst.setString(6, beans.getCliente_endereco());
            pst.setString(7, beans.getCliente_no());
            pst.setString(8, beans.getCliente_cep());
            pst.setString(9, beans.getCliente_complemento());
            pst.setString(10, beans.getCliente_bairro());
            pst.setString(11, beans.getCliente_cidade());
            pst.setString(12, beans.getCliente_observacao());
            pst.setInt(13, beans.getCliente_status());
            pst.setInt(14, beans.getCliente_usuario_id());

            pst.execute();
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar . \n\n" + ex);
        }
        conex.desconecta();
    }

    public void Editar(BeansCliente beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement("UPDATE public.tbl_cliente\n"
                    + "   SET  cliente_nome=?, cliente_cnpj=?, cliente_inscricao=?, \n"
                    + "       cliente_descricao=?, cliente_telefone=?, cliente_endereco=?, \n"
                    + "       cliente_no=?, cliente_cep=?, cliente_complemento=?, cliente_bairro=?, \n"
                    + "       cliente_cidade=?, cliente_observacao=?,  cliente_status=?, \n"
                    + "       cliente_usuario_id=?\n"
                    + " WHERE cliente_id=?;");
            pst.setString(1, beans.getCliente_nome());
            pst.setString(2, beans.getCliente_cnpj());
            pst.setString(3, beans.getCliente_inscricao());
            pst.setString(4, beans.getCliente_descricao());
            pst.setString(5, beans.getCliente_telefone());
            pst.setString(6, beans.getCliente_endereco());
            pst.setString(7, beans.getCliente_no());
            pst.setString(8, beans.getCliente_cep());
            pst.setString(9, beans.getCliente_complemento());
            pst.setString(10, beans.getCliente_bairro());
            pst.setString(11, beans.getCliente_cidade());
            pst.setString(12, beans.getCliente_observacao());
            pst.setInt(13, beans.getCliente_status());
            pst.setInt(14, beans.getCliente_usuario_id());
            pst.setInt(15, beans.getCliente_id());
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Alterado");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public void UpdateStatus(BeansCliente beans) {

        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" UPDATE tbl_cliente"
                    + "   SET cliente_status=?"
                    + " WHERE cliente_id=?;");
            pst.setInt(1, beans.getCliente_status());
            pst.setInt(2, beans.getCliente_id());
            pst.execute();
//             JOptionPane.showMessageDialog(null, "Alterado com sucesso. ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar\n" + ex);
        }
        conex.desconecta();
    }

    public void ExcluirItem(BeansCliente beans) {
        conex.conexao();
        try {
            java.sql.PreparedStatement pst = conex.con.prepareStatement(" DELETE FROM public.tbl_cliente  where cliente_id=?\n"
                    + ";");
            pst.setInt(1, beans.getCliente_id());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Item " + beans.getCliente_id() + " excluido com sucesso .");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro  \n excuir\n" + ex);
        }
        conex.desconecta();
    }

    public ArrayList<BeansCliente> PreencheTabelaA(String b) {
        String resposta = b;
        ArrayList dados = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_cliente inner join public.tbl_usuario on id_usuario=cliente_usuario_id where (coalesce((cliente_nome)) ||' '||coalesce((cliente_descricao))ilike '%" + resposta + "%') and cliente_status=1   order by cliente_id asc ");
        try {
            conex.rs.first();
            do {
                String status = conex.rs.getString("cliente_status");

                if (status.equals("0")) {
                    status = "Inativo";
                } else if (status.equals("1")) {
                    status = "Ativo";
                } else if (status.equals("2")) {
                    status = "Alterado";
                } else if (status.equals("3")) {
                    status = "Excluido";
                }
                String MenuMinhaData = conex.rs.getString("cliente_registro");
                String MenuMeuUsuario = conex.rs.getString("nome_usuario");
                dados.add(new Object[]{conex.rs.getInt("cliente_id"), status,
                    conex.rs.getString("cliente_nome"), conex.rs.getString("cliente_descricao"), conex.rs.getString("cliente_telefone"),
                    conex.rs.getString("cliente_observacao"), MenuMeuUsuario + " - " + MenuMinhaData});
            } while (conex.rs.next());
        } catch (SQLException ex) {
        }
        conex.desconecta();
        return dados;
    }

    public ArrayList<BeansCliente> PreencheTabelaB(String b) {
        String resposta = b;
        ArrayList dados = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_cliente inner join public.tbl_usuario on id_usuario=cliente_usuario_id where (coalesce((cliente_nome)) ||' '||coalesce((cliente_descricao))ilike '%" + resposta + "%') and cliente_status=0   order by cliente_id asc ");
        try {
            conex.rs.first();
            do {
                String status = conex.rs.getString("cliente_status");

                if (status.equals("0")) {
                    status = "Inativo";
                } else if (status.equals("1")) {
                    status = "Ativo";
                } else if (status.equals("2")) {
                    status = "Alterado";
                } else if (status.equals("3")) {
                    status = "Excluido";
                }
                String MenuMinhaData = conex.rs.getString("cliente_registro");
                String MenuMeuUsuario = conex.rs.getString("nome_usuario");
                dados.add(new Object[]{conex.rs.getInt("cliente_id"), status,
                    conex.rs.getString("cliente_nome"), conex.rs.getString("cliente_descricao"), conex.rs.getString("cliente_telefone"),
                    conex.rs.getString("cliente_observacao"), MenuMeuUsuario + " - " + MenuMinhaData});
            } while (conex.rs.next());
        } catch (SQLException ex) {
        }
        conex.desconecta();
        return dados;
    }

    public ArrayList<BeansCliente> Chama(String a) {
        String A = a;
        ArrayList clientes = new ArrayList();
        conex.conexao();
        conex.executaSql2("SELECT  *  FROM tbl_cliente where cliente_id='" + A + "' order by cliente_id asc ");
        try {
            BeansCliente cliente = new BeansCliente();
            conex.rs.last();
            cliente.setCliente_id(conex.rs.getInt("cliente_id"));
            cliente.setCliente_nome(conex.rs.getString("cliente_nome"));
            cliente.setCliente_cnpj(conex.rs.getString("cliente_cnpj"));
            cliente.setCliente_inscricao(conex.rs.getString("cliente_inscricao"));
            cliente.setCliente_descricao(conex.rs.getString("cliente_descricao"));
            cliente.setCliente_telefone(conex.rs.getString("cliente_telefone"));
            cliente.setCliente_endereco(conex.rs.getString("cliente_endereco"));
            cliente.setCliente_no(conex.rs.getString("cliente_no"));
            cliente.setCliente_cep(conex.rs.getString("cliente_cep"));
            cliente.setCliente_complemento(conex.rs.getString("cliente_complemento"));
            cliente.setCliente_bairro(conex.rs.getString("cliente_bairro"));
            cliente.setCliente_cidade(conex.rs.getString("cliente_cidade"));
            cliente.setCliente_observacao(conex.rs.getString("cliente_observacao"));
            cliente.setCliente_nome(conex.rs.getString("cliente_nome"));
            clientes.add(cliente);
        } catch (SQLException ex) {
            System.out.println("Uerro -- " + ex);
        }
        conex.desconecta();
        return clientes;
    }

    public ArrayList<BeansCliente> PreencheCliente() {
        ArrayList clientes = new ArrayList();
        conex.conexao();
        conex.executaSql2(" SELECT *  FROM tbl_cliente where   cliente_status=1    order by cliente_id asc");
        try {
            conex.rs.first();
            do {
                BeansCliente cliente = new BeansCliente();
                cliente.setCliente_nome(conex.rs.getString("cliente_nome"));
                cliente.setCliente_id(Integer.parseInt(conex.rs.getString("cliente_id")));
                clientes.add(cliente);
            } while (conex.rs.next());
        } catch (SQLException ex) {
        }
        conex.desconecta();
        return clientes;
    }
      public ArrayList<BeansCliente> PreencheCliente2(int a) {
        ArrayList clientes = new ArrayList();
        conex.conexao();
        conex.executaSql2(" SELECT *  FROM tbl_cliente where   cliente_id="+a+"    order by cliente_id asc");
        try {
            conex.rs.first();
            do {
                BeansCliente cliente = new BeansCliente();
                cliente.setCliente_nome(conex.rs.getString("cliente_nome"));
                cliente.setCliente_id(Integer.parseInt(conex.rs.getString("cliente_id")));
                clientes.add(cliente);
            } while (conex.rs.next());
        } catch (SQLException ex) {
        }
        conex.desconecta();
        return clientes;
    }
}
