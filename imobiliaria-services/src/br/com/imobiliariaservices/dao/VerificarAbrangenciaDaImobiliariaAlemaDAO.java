/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imobiliariaservices.dao;

import br.com.imobiliariaservices.model.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ricardo
 */
public class VerificarAbrangenciaDaImobiliariaAlemaDAO {
    
    private static VerificarAbrangenciaDaImobiliariaAlemaDAO instancia;
    private static Conexao conexaoInstancias;
    protected ConexaoImbAlema dao = new ConexaoImbAlema();
    Connection conexao;
    
    public static VerificarAbrangenciaDaImobiliariaAlemaDAO getInstance(){
        if(instancia == null){
            instancia = new VerificarAbrangenciaDaImobiliariaAlemaDAO();
            conexaoInstancias = new Conexao("jdbc:postgresql://localhost:5432/BD_Alema","postgres","12345678");
        }
        return instancia;
    }
    
    public List<String> abrangenciaAlema() throws Exception{
        List<String> listBairro = new ArrayList<String>();
        String bairro = null;
        try{
            conexao = dao.abrirConexao(false, conexaoInstancias);          
            Statement stmt = conexao.createStatement();
            ResultSet rs = null;
                        
            StringBuilder sql = new StringBuilder();
            sql.append("select distinct stl_name from stadtteil inner join immobilie on stl_id=imm_stl_id inner join umfasst on umf_stl_id= stl_id");
            rs = stmt.executeQuery(sql.toString());
            
            while(rs.next()){
                bairro = rs.getString("stl_name");
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
