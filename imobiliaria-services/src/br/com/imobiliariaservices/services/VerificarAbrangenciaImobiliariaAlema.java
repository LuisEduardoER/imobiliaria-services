package br.com.imobiliariaservices.services;

import java.util.List;

import br.com.imobiliariaservices.dao.VerificarAbrangenciaDaImobiliariaAlemaDAO;

public class VerificarAbrangenciaImobiliariaAlema {

    public List<String> abrangenciaAlema() throws Exception {
        VerificarAbrangenciaDaImobiliariaAlemaDAO dao = new VerificarAbrangenciaDaImobiliariaAlemaDAO();
        List<String> abrangenciaAlema = dao.abrangenciaAlema();
        
        return abrangenciaAlema;
    }
}
