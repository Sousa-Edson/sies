/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloBeans;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author edson
 */
public class BeansProtocolo {

    int id_protocolo,   protocolo_id_fornecedor,protocolo_status,protocolo_id_usuario;
    String   protocolo_observacao, protocolo_numero, protocolo_hora, protocolo_data;

    double protocolo_total;

    public int getprotocolo_id_usuario() {
        return protocolo_id_usuario;
    }

    public void setprotocolo_id_usuario(int protocolo_id_usuario) {
        this.protocolo_id_usuario = protocolo_id_usuario;
    }

    public int getprotocolo_status() {
        return protocolo_status;
    }

    public void setprotocolo_status(int protocolo_status) {
        this.protocolo_status = protocolo_status;
    }

    public int getId_protocolo() {
        return id_protocolo;
    }

    public void setId_protocolo(int id_protocolo) {
        this.id_protocolo = id_protocolo;
    }

     

    public int getprotocolo_id_fornecedor() {
        return protocolo_id_fornecedor;
    }

    public void setprotocolo_id_fornecedor(int protocolo_id_fornecedor) {
        this.protocolo_id_fornecedor = protocolo_id_fornecedor;
    }

    public String getprotocolo_numero() {
        return protocolo_numero;
    }

    public void setprotocolo_numero(String protocolo_numero) {
        this.protocolo_numero = protocolo_numero;
    }

    
    public String getprotocolo_observacao() {
        return protocolo_observacao;
    }

    public void setprotocolo_observacao(String protocolo_observacao) {
        this.protocolo_observacao = protocolo_observacao;
    }

    public String getprotocolo_data() {
        return protocolo_data;
    }

    public void setprotocolo_data(String protocolo_data) {
        this.protocolo_data = protocolo_data;
    }

    public String getprotocolo_hora() {
        return protocolo_hora;
    }

    public void setprotocolo_hora(String protocolo_hora) {
        this.protocolo_hora = protocolo_hora;
    }

    public double getprotocolo_total() {
        return protocolo_total;
    }

    public void setprotocolo_total(double protocolo_total) {
        this.protocolo_total = protocolo_total;
    }

}
