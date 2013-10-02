package br.com.imobiliariaservices.services;

import java.util.List;

import br.com.imobiliariaservices.dao.VerificarAbrangenciaDaImobiliariaOnlineDAO;

public class VerificarAbrangenciaImobiliariaOnline {

	  public List<String> abrangenciaOnline() throws Exception {
	        VerificarAbrangenciaDaImobiliariaOnlineDAO dao = new VerificarAbrangenciaDaImobiliariaOnlineDAO();
	        List<String> abrangenciaOnline = dao.abrangenciaOnline();
	        
	        return abrangenciaOnline;
	    }
}
