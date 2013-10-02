/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imobiliariaservices.dao;

import br.com.imobiliariaservices.model.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ricardo
 */
public class VerificarAbrangenciaDaImobiliariaInglesaDAO {
    
    private static VerificarAbrangenciaDaImobiliariaInglesaDAO instancia;
    private static Conexao conexaoInstancias;
    protected ConexaoImbInglesa dao = new ConexaoImbInglesa();
    Connection conexao;
    
    public static VerificarAbrangenciaDaImobiliariaInglesaDAO getInstance(){
        if(instancia == null){
            instancia = new VerificarAbrangenciaDaImobiliariaInglesaDAO();
            conexaoInstancias = new Conexao("jdbc:postgresql://localhost:5432/BD_Inglesa","postgres","12345678");
        }
        return instancia;
    }
    
    public List<String> abrangenciaInglesa() throws Exception{
        List<String> listBairro = new ArrayList<String>();
        String bairro = null;
        try{
            conexao = dao.abrirConexao(false, conexaoInstancias);          
            Statement stmt = conexao.createStatement();
            ResultSet rs = null;
                        
            StringBuilder sql = new StringBuilder();
            sql.append("select distinct dis_name from district inner join immobile on dis_id=imm_dis_id inner join scope on sco_dis_id=dis_id");
            rs = stmt.executeQuery(sql.toString());
            
            while(rs.next()){
                bairro = rs.getString("dis_name");
                listBairro.add(bairro);
            }
        }finally{
            if (!conexao.isClosed()){
                ConexaoImbOnline.fecharConexao();
            }
        }
        return listBairro;
    }
}
