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
public class BeansImagem {

    int id_imagem, id_imagem_produto;
    String caminho_imagem,nome_imagem;

    public int getId_imagem() {
        return id_imagem;
    }

    public void setId_imagem(int id_imagem) {
        this.id_imagem = id_imagem;
    }

    public int getId_imagem_produto() {
        return id_imagem_produto;
    }

    public void setId_imagem_produto(int id_imagem_produto) {
        this.id_imagem_produto = id_imagem_produto;
    }

    public String getCaminho_imagem() {
        return caminho_imagem;
    }

    public void setCaminho_imagem(String caminho_imagem) {
        this.caminho_imagem = caminho_imagem;
    }

    public String getNome_imagem() {
        return nome_imagem;
    }

    public void setNome_imagem(String nome_imagem) {
        this.nome_imagem = nome_imagem;
    }

}
