package br.com.imobiliariaservices.services;

import java.util.List;

import br.com.imobiliariaservices.dao.ListarImoveisDaImobiliariaOnlineDAO;
import br.com.imobiliariaservices.model.Imovel;

public class ListarImoveisImobiliariaOnline {

	public List<Imovel> listarOnline(List<String> bairro, double valMin, double valMax, int numQuartos, boolean posVagGaragem, String horSol) throws Exception {
	        ListarImoveisDaImobiliariaOnlineDAO dao = new ListarImoveisDaImobiliariaOnlineDAO();
	        List<Imovel> listOnline = dao.listarOnline(bairro,valMin,valMax,numQuartos,posVagGaragem,horSol);
	        
	        return listOnline;
	    }
}
