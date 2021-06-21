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
public class BeansProduto {//produto_observacao,
  String   produto_nome,produto_descricao, produto_unidade_desc;
  int produto_id, produto_unidade, produto_ncm, produto_status, produto_usuario_id,produto_categoria;
  double  produto_valor,produto_min;
  String p_qtd,produto_informacao;

    public String getProduto_informacao() {
        return produto_informacao;
    }

    public void setProduto_informacao(String produto_informacao) {
        this.produto_informacao = produto_informacao;
    }

    public String getP_qtd() {
        return p_qtd;
    }

    public void setP_qtd(String p_qtd) {
        this.p_qtd = p_qtd;
    }

    

   

    public String getProduto_nome() {
        return produto_nome;
    }

    public void setProduto_nome(String produto_nome) {
        this.produto_nome = produto_nome;
    }

   

    public String getProduto_descricao() {
        return produto_descricao;
    }

    public void setProduto_descricao(String produto_descricao) {
        this.produto_descricao = produto_descricao;
    }

//    public String getProduto_observacao() {
//        return produto_observacao;
//    }
//
//    public void setProduto_observacao(String produto_observacao) {
//        this.produto_observacao = produto_observacao;
//    }

    public int getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public int getProduto_unidade() {
        return produto_unidade;
    }

    public void setProduto_unidade(int produto_unidade) {
        this.produto_unidade = produto_unidade;
    }

    public int getProduto_ncm() {
        return produto_ncm;
    }

    public void setProduto_ncm(int produto_ncm) {
        this.produto_ncm = produto_ncm;
    }

    public int getProduto_status() {
        return produto_status;
    }

    public void setProduto_status(int produto_status) {
        this.produto_status = produto_status;
    }

    public int getProduto_usuario_id() {
        return produto_usuario_id;
    }

    public void setProduto_usuario_id(int produto_usuario_id) {
        this.produto_usuario_id = produto_usuario_id;
    }

    public double getProduto_valor() {
        return produto_valor;
    }

    public void setProduto_valor(double produto_valor) {
        this.produto_valor = produto_valor;
    }

    public double getProduto_min() {
        return produto_min;
    }

    public void setProduto_min(double produto_min) {
        this.produto_min = produto_min;
    }

    public int getProduto_categoria() {
        return produto_categoria;
    }

    public void setProduto_categoria(int produto_categoria) {
        this.produto_categoria = produto_categoria;
    }

    public String getProduto_unidade_desc() {
        return produto_unidade_desc;
    }

    public void setProduto_unidade_desc(String produto_unidade_desc) {
        this.produto_unidade_desc = produto_unidade_desc;
    }
    
     
    
}
