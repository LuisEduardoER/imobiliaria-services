/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imobiliariaservices.dao;

import br.com.imobiliariaservices.model.Conexao;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Ricardo
 */
public class ConexaoImbAlema {
    
    private static String dbURL = "jdbc:postgresql://localhost:5432/BD_Alema";
    private static String usuario = "postgres";
    private static String senha = "admin";
    private static Connection conexao;
    
    public Connection abrirConexao(Boolean autoCommit, Conexao con){
        try{
            Class.forName("org.postgresql.Driver").newInstance();
            conexao = DriverManager.getConnection(dbURL, usuario, senha);
        }catch (Exception except){
            except.printStackTrace();
        }
        return conexao;
    }
    
    public static void fecharConexao(){
        try{
            if(!conexao.isClosed())
                conexao.close();
        }catch (Exception except){
            except.printStackTrace();
        }
    }
    
}
