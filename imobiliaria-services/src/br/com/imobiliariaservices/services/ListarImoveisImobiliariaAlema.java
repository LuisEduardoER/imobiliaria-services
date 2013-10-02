package br.com.imobiliariaservices.services;

import java.util.List;

import br.com.imobiliariaservices.dao.ListarImoveisDaImobiliariaAlemaDAO;
import br.com.imobiliariaservices.model.Imovel;

public class ListarImoveisImobiliariaAlema {
	
	public List<Imovel> listarAlema(List<String> bairro, double valMin, double valMax, int numQuartos, boolean posVagGaragem, String horSol) throws Exception {
        ListarImoveisDaImobiliariaAlemaDAO dao = new ListarImoveisDaImobiliariaAlemaDAO();
        List<Imovel> listAlema = dao.listarAlema(bairro,valMin,valMax,numQuartos,posVagGaragem,horSol);
        
        return listAlema;
    }
}
