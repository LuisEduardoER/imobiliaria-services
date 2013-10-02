package br.com.imobiliariaservices.services;

import br.com.imobiliariaservices.dao.RegistrarPropostaDeCompraDAO;

public class RegistrarPropostaDeCompra {

	 public boolean registrarProposta(String nome, String cpf, String cnpj, int idOrigem, double valor, String descricao) throws Exception {
	        RegistrarPropostaDeCompraDAO dao = new RegistrarPropostaDeCompraDAO();
	        boolean registrarProposta = dao.registrarProposta(nome, cpf, cnpj, idOrigem, valor, descricao);
	        
	        return registrarProposta;
	    }
}
