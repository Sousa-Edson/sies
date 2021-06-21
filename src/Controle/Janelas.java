/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import EntradaNota.Item.JDialog_AddItem_Obs;
import View.Cfop.ListaCfop;
import View.Cliente.ListaCliente;
import EntradaNota.ListaNota;
import EntradaProtocolo.ListaProtocolo;
import View.Fornecedor.ListaFornecedor;
import Principal.Login;
import Principal.Menu;
import View.Expedicao.JDialogExpedicao;
import View.Pedido.AddPedido2;
import View.Pedido.ListaPedidos;
import View.Pedido.framepedido;
import View.Produto.ListaProduto;
import View.Unidade.ListaUnidade;
import View.Usuario.ListaUsuario;

/**
 *
 * @author edson
 */
public class Janelas {

    framepedido add_pedido3 = new framepedido();
    AddPedido2 add_pedido = new AddPedido2();
    ListaPedidos lista_pedido = new ListaPedidos();
    ListaUsuario lista_usuario = new ListaUsuario();
    ListaUnidade lista_unidade = new ListaUnidade();
    ListaFornecedor lista_fornecedor = new ListaFornecedor();
    ListaCliente lista_cliente = new ListaCliente();
    ListaProduto lista_produto = new ListaProduto();
    ListaNota lista_nota = new ListaNota();
    ListaCfop lista_cfop = new ListaCfop();
    Menu menu;
    ListaProtocolo lista_protocolo = new ListaProtocolo();

    protected boolean rootPaneCheckingEnabled = false;

    JDialogExpedicao expedicao = new JDialogExpedicao(menu, rootPaneCheckingEnabled);

//    public void Chama_expedicao() {
//        expedicao.setVisible(true);
//    }

    public void Chama_lista_usuario() {
        lista_usuario.PreencheTabela();
        lista_usuario.setVisible(true);
    }

    public void Chama_lista_unidade() {
        lista_unidade.ValidaPermissao();
        lista_unidade.PreencheTabela();
        lista_unidade.setVisible(true);
    }

    public void Chama_lista_fornecedor() {
        lista_fornecedor.ValidaPermissao();
        lista_fornecedor.PreencheTabela();
        lista_fornecedor.setVisible(true);
    }

    public void Chama_lista_cliente() {
        lista_cliente.ValidaPermissao();
        lista_cliente.PreencheTabela();
        lista_cliente.setVisible(true);
    }

    public void Chama_lista_produto() {
        lista_produto.ValidaPermissao();
        lista_produto.PreencheTabela();
        lista_produto.setVisible(true);
    }

    public void Chama_lista_nota() {
        lista_nota.ValidaPermissao();
        lista_nota.PreencheTabela();
        lista_nota.setVisible(true);
    }

    public void Chama_lista_protocolo() {
        lista_protocolo.ValidaPermissao();
        lista_protocolo.PreencheTabela();
        lista_protocolo.setVisible(true);
    }

    public void Chama_lista_cfop() {
        lista_cfop.ValidaPermissao();
        lista_cfop.PreencheTabela();
        lista_cfop.setVisible(true);
    }

    public void Chama_add_pedido_iniciar() {
        add_pedido.dispose();
    }

    public void Chama_add_pedido(int a) {
        add_pedido.CarregaPedido(a);
        add_pedido.setVisible(true);
      
    }

    public void Chama_lista_pedido() {

        lista_pedido.setVisible(true);
    }

}
