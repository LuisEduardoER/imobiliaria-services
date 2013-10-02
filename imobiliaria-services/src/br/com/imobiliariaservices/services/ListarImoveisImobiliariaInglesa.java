package br.com.imobiliariaservices.services;

import java.util.List;

import br.com.imobiliariaservices.dao.ListarImoveisDaImobiliariaInglesaDAO;
import br.com.imobiliariaservices.model.Imovel;

public class ListarImoveisImobiliariaInglesa {

    public List<Imovel> listarInglesa(List<String> bairro, double valMin, double valMax, int numQuartos, boolean posVagGaragem, String horSol) throws Exception {
        ListarImoveisDaImobiliariaInglesaDAO dao = new ListarImoveisDaImobiliariaInglesaDAO();
        List<Imovel> listInglesa = dao.listarInglesa(bairro,valMin,valMax,numQuartos,posVagGaragem,horSol);
        
        return listInglesa;
    }
}
