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
public class VerificarAbrangenciaDaImobiliariaOnlineDAO {
    
    private static VerificarAbrangenciaDaImobiliariaOnlineDAO instancia;
    private static Conexao conexaoInstancias;
    protected ConexaoImbOnline dao = new ConexaoImbOnline();
    Connection conexao;
    
    public static VerificarAbrangenciaDaImobiliariaOnlineDAO getInstance(){
        if(instancia == null){
            instancia = new VerificarAbrangenciaDaImobiliariaOnlineDAO();
            conexaoInstancias = new Conexao("jdbc:postgresql://localhost:5432/BD_Online","postgres","12345678");
        }
        return instancia;
    }
    
    public List<String> abrangenciaOnline() throws Exception{
        List<String> listBairro = new ArrayList<String>();
        String bairro = null;
        try{
            conexao = dao.abrirConexao(false, conexaoInstancias);          
            Statement stmt = conexao.createStatement();
            ResultSet rs = null;
                        
            StringBuilder sql = new StringBuilder();
            sql.append("select distinct bro_nome from bairro inner join imovel on bro_id=imv_bro_id inner join possui on pos_imv_id=imv_id");
            rs = stmt.executeQuery(sql.toString());
            
            while(rs.next()){
                bairro = rs.getString("bro_nome");
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
