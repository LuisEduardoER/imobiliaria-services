package br.com.imobiliariaservices.services;


import java.util.List;

import br.com.imobiliariaservices.dao.VerificarAbrangenciaDaImobiliariaInglesaDAO;

public class VerificarAbrangeciaImobiliariaInglesa {

    public List<String> abrangenciaInglesa() throws Exception {
        VerificarAbrangenciaDaImobiliariaInglesaDAO dao = new VerificarAbrangenciaDaImobiliariaInglesaDAO();
        List<String> abrangenciaInglesa = dao.abrangenciaInglesa();
        
        return abrangenciaInglesa;
    }
}
