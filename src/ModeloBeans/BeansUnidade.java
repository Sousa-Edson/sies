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
public class BeansUnidade {
   int id_unidade, frag_unidade, status_unidade,id_usuario;
   String  sigla_unidade, descricao_unidade;

    public int getId_unidade() {
        return id_unidade;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setId_unidade(int id_unidade) {
        this.id_unidade = id_unidade;
    }

    public int getFrag_unidade() {
        return frag_unidade;
    }

    public void setFrag_unidade(int frag_unidade) {
        this.frag_unidade = frag_unidade;
    }

    public int getStatus_unidade() {
        return status_unidade;
    }

    public void setStatus_unidade(int status_unidade) {
        this.status_unidade = status_unidade;
    }

    public String getSigla_unidade() {
        return sigla_unidade;
    }

    public void setSigla_unidade(String sigla_unidade) {
        this.sigla_unidade = sigla_unidade;
    }

    public String getDescricao_unidade() {
        return descricao_unidade;
    }

    public void setDescricao_unidade(String descricao_unidade) {
        this.descricao_unidade = descricao_unidade;
    }

    @Override
    public String toString() {
        return getDescricao_unidade() ;
    }
    
}
