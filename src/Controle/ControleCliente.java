/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import ModeloBeans.BeansCliente;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class ControleCliente {

  //  BeansCliente cliente = new BeansCliente();

    public ArrayList<BeansCliente> AjustaCampoGenerico(BeansCliente BEANS) {
        ArrayList clientes = new ArrayList();
        int limite = BEANS.getTamanho();
        int tamanho;
        String s = BEANS.getTexto();
        String textoLimitado = s.length() <= limite ? s : s.substring(0, limite);
        String info ;
        if (textoLimitado.length() >= limite) {
            tamanho = 1;
            info = ("Campo com limite execido!! Max. " + textoLimitado.length());
        } else {
            tamanho = 0;
//            cliente.setTexto(textoLimitado);
            info = ("Campo com limite de " + textoLimitado.length() + "/" + limite);
        }
        textoLimitado = textoLimitado.toUpperCase();
        BEANS.setTamanho(tamanho);
        BEANS.setTexto(textoLimitado);
        BEANS.setInformacao(info);
        clientes.add(BEANS);
        return clientes;
    }

//    public ArrayList<BeansCliente> AjustaMeuNome(BeansCliente BEANS) {
//        ArrayList clientes = new ArrayList();
//        String s = BEANS.getCliente_nome();
//        String textoLimitado = s.length() <= 25 ? s : s.substring(0, 25);
//        if (textoLimitado.length() >= 24) {
//            cliente.setInformacao("Campo com limite execido!! Max. " + textoLimitado.length());
//            cliente.setCliente_nome(textoLimitado);
//        } else {
//            cliente.setCliente_descricao(textoLimitado);
//            cliente.setInformacao(" ");
//        }
//        clientes.add(cliente);
//        return clientes;
//    }
}
