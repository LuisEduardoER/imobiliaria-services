package br.com.imobiliariaservices.services;

import java.util.List;

import br.com.imobiliariaservices.dao.RegistrarCriteriosDePesquisaDAO;

public class RegistrarCriteriosDePesquisa {

    public Boolean registrarPesquisa (String cpf, List<String> bairro, double valMin, double valMax, int numQuartos, boolean posVagGaragem, String horSol) throws Exception {
        RegistrarCriteriosDePesquisaDAO dao = new RegistrarCriteriosDePesquisaDAO();
        return dao.registrarPesquisa(cpf, bairro, valMin, valMax, numQuartos, posVagGaragem, horSol) ? true : false;
    
    }
}
