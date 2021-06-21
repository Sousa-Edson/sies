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
public class BeansItem {

    int id, t_produto_id, item_id_nota, t_item_st, t_item_id, item_id_protoloco, item_id_pedido;
    String t_complemento;
    Double t_qtd;

    String p_qtd, p_descricao;

    public int getItem_id_pedido() {
        return item_id_pedido;
    }

    public void setItem_id_pedido(int item_id_pedido) {
        this.item_id_pedido = item_id_pedido;
    }

    public String getP_descricao() {
        return p_descricao;
    }

    public void setP_descricao(String p_descricao) {
        this.p_descricao = p_descricao;
    }

    public String getP_qtd() {
        return p_qtd;
    }

    public void setP_qtd(String p_qtd) {
        this.p_qtd = p_qtd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getT_produto_id() {
        return t_produto_id;
    }

    public void setT_produto_id(int t_produto_id) {
        this.t_produto_id = t_produto_id;
    }

    public String getT_complemento() {
        return t_complemento;
    }

    public void setT_complemento(String t_complemento) {
        this.t_complemento = t_complemento;
    }

    public Double getT_qtd() {
        return t_qtd;
    }

    public void setT_qtd(Double t_qtd) {
        this.t_qtd = t_qtd;
    }

    public int getItem_id_nota() {
        return item_id_nota;
    }

    public void setItem_id_nota(int item_id_nota) {
        this.item_id_nota = item_id_nota;
    }

    public int getT_item_st() {
        return t_item_st;
    }

    public void setT_item_st(int t_item_st) {
        this.t_item_st = t_item_st;
    }

    public int getT_item_id() {
        return t_item_id;
    }

    public void setT_item_id(int t_item_id) {
        this.t_item_id = t_item_id;
    }

    public int getItem_id_protoloco() {
        return item_id_protoloco;
    }

    public void setItem_id_protoloco(int item_id_protoloco) {
        this.item_id_protoloco = item_id_protoloco;
    }

}
