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
public class BeansNota {

    int id_nota, nota_id_cfop, nota_id_fornecedor, nota_status, nota_id_usuario;
    String nota_chave, nota_observacao, nota_numero, nota_hora, nota_data;
    String nota_nome_fornecedor,nota_nome_usuario,nota_nome_cfop;

    double nota_total;
    String nota_infomacao;

    public String getNota_nome_cfop() {
        return nota_nome_cfop;
    }

    public void setNota_nome_cfop(String nota_nome_cfop) {
        this.nota_nome_cfop = nota_nome_cfop;
    }

    public String getNota_nome_fornecedor() {
        return nota_nome_fornecedor;
    }

    public void setNota_nome_fornecedor(String nota_nome_fornecedor) {
        this.nota_nome_fornecedor = nota_nome_fornecedor;
    }

    public String getNota_nome_usuario() {
        return nota_nome_usuario;
    }

    public void setNota_nome_usuario(String nota_nome_usuario) {
        this.nota_nome_usuario = nota_nome_usuario;
    }

    public String getNota_infomacao() {
        return nota_infomacao;
    }

    public void setNota_infomacao(String nota_infomacao) {
        this.nota_infomacao = nota_infomacao;
    }

    public int getNota_id_usuario() {
        return nota_id_usuario;
    }

    public void setNota_id_usuario(int nota_id_usuario) {
        this.nota_id_usuario = nota_id_usuario;
    }

    public int getNota_status() {
        return nota_status;
    }

    public void setNota_status(int nota_status) {
        this.nota_status = nota_status;
    }

    public int getId_nota() {
        return id_nota;
    }

    public void setId_nota(int id_nota) {
        this.id_nota = id_nota;
    }

    public int getNota_id_cfop() {
        return nota_id_cfop;
    }

    public void setNota_id_cfop(int nota_id_cfop) {
        this.nota_id_cfop = nota_id_cfop;
    }

    public int getNota_id_fornecedor() {
        return nota_id_fornecedor;
    }

    public void setNota_id_fornecedor(int nota_id_fornecedor) {
        this.nota_id_fornecedor = nota_id_fornecedor;
    }

    public String getNota_numero() {
        return nota_numero;
    }

    public void setNota_numero(String nota_numero) {
        this.nota_numero = nota_numero;
    }

    public String getNota_chave() {
        return nota_chave;
    }

    public void setNota_chave(String nota_chave) {
        this.nota_chave = nota_chave;
    }

    public String getNota_observacao() {
        return nota_observacao;
    }

    public void setNota_observacao(String nota_observacao) {
        this.nota_observacao = nota_observacao;
    }

    public String getNota_data() {
        return nota_data;
    }

    public void setNota_data(String nota_data) {
        this.nota_data = nota_data;
    }

    public String getNota_hora() {
        return nota_hora;
    }

    public void setNota_hora(String nota_hora) {
        this.nota_hora = nota_hora;
    }

    public double getNota_total() {
        return nota_total;
    }

    public void setNota_total(double nota_total) {
        this.nota_total = nota_total;
    }

}
