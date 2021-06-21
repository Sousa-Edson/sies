/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloBeans;

/**
 *
 * @author edson
 */
public class BeansPedido {

    int id_pedido,   pedido_id_cliente,pedido_status,pedido_id_usuario;
    String   pedido_observacao,   pedido_hora, pedido_data;
    double pedido_total;

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getPedido_id_cliente() {
        return pedido_id_cliente;
    }

    public void setPedido_id_cliente(int pedido_id_cliente) {
        this.pedido_id_cliente = pedido_id_cliente;
    }

    public int getPedido_status() {
        return pedido_status;
    }

    public void setPedido_status(int pedido_status) {
        this.pedido_status = pedido_status;
    }

    public int getPedido_id_usuario() {
        return pedido_id_usuario;
    }

    public void setPedido_id_usuario(int pedido_id_usuario) {
        this.pedido_id_usuario = pedido_id_usuario;
    }

    public String getPedido_observacao() {
        return pedido_observacao;
    }

    public void setPedido_observacao(String pedido_observacao) {
        this.pedido_observacao = pedido_observacao;
    }

    public String getPedido_hora() {
        return pedido_hora;
    }

    public void setPedido_hora(String pedido_hora) {
        this.pedido_hora = pedido_hora;
    }

    public String getPedido_data() {
        return pedido_data;
    }

    public void setPedido_data(String pedido_data) {
        this.pedido_data = pedido_data;
    }

    public double getPedido_total() {
        return pedido_total;
    }

    public void setPedido_total(double pedido_total) {
        this.pedido_total = pedido_total;
    }

    
}
