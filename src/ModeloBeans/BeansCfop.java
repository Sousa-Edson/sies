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
public class BeansCfop {
   int id_cfop,  status_cfop,id_usuario;
   String  sigla_cfop, descricao_cfop;

    public int getId_cfop() {
        return id_cfop;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setId_cfop(int id_cfop) {
        this.id_cfop = id_cfop;
    }

   

    public int getStatus_cfop() {
        return status_cfop;
    }

    public void setStatus_cfop(int status_cfop) {
        this.status_cfop = status_cfop;
    }

    public String getSigla_cfop() {
        return sigla_cfop;
    }

    public void setSigla_cfop(String sigla_cfop) {
        this.sigla_cfop = sigla_cfop;
    }

    public String getDescricao_cfop() {
        return descricao_cfop;
    }

    public void setDescricao_cfop(String descricao_cfop) {
        this.descricao_cfop = descricao_cfop;
    }
}
