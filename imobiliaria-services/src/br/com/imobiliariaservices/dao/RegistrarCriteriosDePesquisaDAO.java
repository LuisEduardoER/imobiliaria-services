/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imobiliariaservices.dao;

import br.com.imobiliariaservices.model.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo
 */
public class RegistrarCriteriosDePesquisaDAO {
    
    private static RegistrarCriteriosDePesquisaDAO instancia;
    private static Conexao conexaoInstancias;
    protected ConexaoImbOnline dao = new ConexaoImbOnline();
    Connection conexao;
    
    public static RegistrarCriteriosDePesquisaDAO getInstance(){
        if(instancia == null){
            instancia = new RegistrarCriteriosDePesquisaDAO();
            conexaoInstancias = new Conexao("jdbc:postgresql://localhost:5432/BD_Online","postgres","admin");
        }
        return instancia;
    }
    
    public boolean registrarPesquisa (String cpf, List<String> bairro, double valMin, double valMax, int numQuartos, boolean posVagGaragem, String horSol) throws Exception{ 
        try{
            conexao = dao.abrirConexao(true, conexaoInstancias);
            Statement stmt = conexao.createStatement();
            
            StringBuilder sqlCriterios = new StringBuilder();
            sqlCriterios.append("INSERT INTO CRITERIOS_DE_PESQUISA (CDP_NUM_QUARTOS, CDP_POS_VAG_GARAGEM, CDP_DTA_PESQUISA, CDP_HOR_SOL, CDP_VAL_MIN, CDP_VAL_MAX, CDP_CLI_CPF) VALUES (");
            sqlCriterios.append(numQuartos).append(", ");
            sqlCriterios.append(posVagGaragem).append(", ");
            sqlCriterios.append("CURRENT_DATE, '");
            sqlCriterios.append(horSol).append("' , ");
            sqlCriterios.append(valMin).append(", ");
            sqlCriterios.append(valMax).append(", '");
            sqlCriterios.append(cpf).append("'); ");
            stmt.executeUpdate(sqlCriterios.toString());
           
            int cdp_id = 0;
            ResultSet rs = null;
            StringBuilder sql = new StringBuilder();
            sql.append("select max (CDP_ID) as cpd_id from CRITERIOS_DE_PESQUISA");
            rs = stmt.executeQuery(sql.toString());
            while(rs.next()){
                cdp_id = rs.getInt("cpd_id");
            }
            
            List<String> abrangenciaOnline = VerificarAbrangenciaDaImobiliariaOnlineDAO.getInstance().abrangenciaOnline();
            List<String> abrangenciaInglesa = VerificarAbrangenciaDaImobiliariaInglesaDAO.getInstance().abrangenciaInglesa();
            List<String> abrangenciaAlema = VerificarAbrangenciaDaImobiliariaAlemaDAO.getInstance().abrangenciaAlema();
            
            for(int i=0;i<bairro.size();i++){
                for(int j=0;j<abrangenciaOnline.size();j++){
                    if(bairro.get(i).equals(abrangenciaOnline.get(j))){
                        StringBuilder sqlCompreende = new StringBuilder();
                        sqlCompreende.append("INSERT INTO DADOS (DAD_CDP_ID, DAD_NOME_BAIRRO, DAD_CNPJ_IMOBILIARIA) VALUES (");
                        sqlCompreende.append(cdp_id).append(", '").append(bairro.get(i)).append("', (select IMB_CNPJ from IMOBILIARIA where IMB_NOME='Imobiliaria Rio de Janeiro'));");
                        stmt.executeUpdate(sqlCompreende.toString());
                    }
                }
                for(int j=0;j<abrangenciaInglesa.size();j++){
                    if(bairro.get(i).equals(abrangenciaInglesa.get(j))){
                        StringBuilder sqlCompreende = new StringBuilder();
                        sqlCompreende.append("INSERT INTO DADOS (DAD_CDP_ID, DAD_NOME_BAIRRO, DAD_CNPJ_IMOBILIARIA) VALUES (");
                        sqlCompreende.append(cdp_id).append(", '").append(bairro.get(i)).append("', (select IMB_CNPJ from IMOBILIARIA where IMB_NOME='Imobiliaria Londres'));");
                        stmt.executeUpdate(sqlCompreende.toString());
                    }
                }
                for(int j=0;j<abrangenciaAlema.size();j++){
                    if(bairro.get(i).equals(abrangenciaAlema.get(j))){
                        StringBuilder sqlCompreende = new StringBuilder();
                        sqlCompreende.append("INSERT INTO DADOS (DAD_CDP_ID, DAD_NOME_BAIRRO, DAD_CNPJ_IMOBILIARIA) VALUES (");
                        sqlCompreende.append(cdp_id).append(", '").append(bairro.get(i)).append("', (select IMB_CNPJ from IMOBILIARIA where IMB_NOME='Imobiliaria Berlim'));");
                        stmt.executeUpdate(sqlCompreende.toString());
                    }
                }
            }
                                    
        }finally{
	        try {
	            if (!conexao.isClosed()){
	                ConexaoImbOnline.fecharConexao();
	            }
	            return true;
	        } catch (SQLException ex) {
	            Logger.getLogger(RegistrarCriteriosDePesquisaDAO.class.getName()).log(Level.SEVERE, null, ex);
	            return false;
	        }
        }
    }
    
}
