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
public class ListarImoveisDaImobiliariaInglesaDAO {
    
    private static ListarImoveisDaImobiliariaInglesaDAO instancia;
    private static Conexao conexaoInstancias;
    protected ConexaoImbInglesa dao = new ConexaoImbInglesa();
    Connection conexao;
    
    public static ListarImoveisDaImobiliariaInglesaDAO getInstance(){
        if(instancia == null){
            instancia = new ListarImoveisDaImobiliariaInglesaDAO();
            conexaoInstancias = new Conexao("jdbc:postgresql://localhost:5432/BD_Inglesa","postgres","12345678");
        }
        return instancia;
    }
    
    public List<Imovel> listarInglesa(List<String> bairro, double valMin, double valMax, int numQuartos, boolean posVagGaragem, String horSol) throws Exception{
        List<Imovel> listImovel = new ArrayList<Imovel>();
        Imovel imovel = null;
        int index = -1;
        try{
            conexao = dao.abrirConexao(false, conexaoInstancias);          
            Statement stmt = conexao.createStatement();
            ResultSet rs = null;
                        
            int numBairros = bairro.size();
            int tam = numBairros;
            
            StringBuilder sqlImv = new StringBuilder();
            sqlImv.append("select * from IMMOBILE inner join DISTRICT on IMM_DIS_ID=DIS_ID inner join CITY on DIS_CIT_ID=CIT_ID inner join STATE on STA_ID=CIT_STA_ID where (DIS_NAME = '");
            for(int i=0; i<numBairros; i++){
                if (tam>1) {
                    sqlImv.append(bairro.get(i)).append("' or DIS_NAME = '");
                    tam--;
                }
                else sqlImv.append(bairro.get(i)).append("') and IMM_PRICE >= ");
            }
            sqlImv.append(valMin).append(" and IMM_PRICE <= ");
            sqlImv.append(valMax).append(" and IMM_NUM_ROOMS = ");
            sqlImv.append(numQuartos).append(" and IMM_PRK_SPACE = ");
            sqlImv.append(posVagGaragem).append(" and IMM_SUN_HOUR = '");
            sqlImv.append(horSol).append("'");
            rs = stmt.executeQuery(sqlImv.toString());
            
            while(rs.next()){   
                imovel = new Imovel();
                imovel.setImvId(rs.getInt("IMM_ID"));
                imovel.setImvPreco(rs.getDouble("IMM_PRICE"));
                imovel.setImvArea(rs.getString("IMM_AREA"));
                imovel.setImvNumQuartos(rs.getInt("IMM_NUM_ROOMS"));
                imovel.setImvPosVagGaragem(rs.getBoolean("IMM_PRK_SPACE"));
                imovel.setImvHorSol(rs.getString("IMM_SUN_HOUR"));
                imovel.setImvOtrDetalhes(rs.getString("IMM_OTR_DETAILS"));
                imovel.setImvLogradouro(rs.getString("IMM_STREET"));
                imovel.setImvNumero(rs.getInt("IMM_NUMBER"));
                imovel.setImvComplemento(rs.getString("IMM_COMPLEMENT"));
                imovel.setImvBairro(rs.getString("DIS_NAME"));
                imovel.setImvCidade(rs.getString("CIT_NAME"));
                imovel.setImvEstado(rs.getString("STA_NAME"));
                listImovel.add(imovel);
                index++;
            }
            
            if(index!=-1){
                for(int i=0;i<=index;i++){
                    StringBuilder sqlImb = new StringBuilder();
                    sqlImb.append("select * from AGENCY inner join IMMOBILE on IMM_AGE_CNPJ=AGE_CNPJ inner join DISTRICT on AGE_DIS_ID=DIS_ID inner join CITY on DIS_CIT_ID=CIT_ID inner join STATE on STA_ID=CIT_STA_ID where IMM_ID = ");
                    sqlImb.append(listImovel.get(i).getImvId());
                    rs = stmt.executeQuery(sqlImb.toString());

                    while(rs.next()){   
                        listImovel.get(i).setImbCnpj(rs.getString("AGE_CNPJ"));
                        listImovel.get(i).setImbNome(rs.getString("AGE_NAME"));
                        listImovel.get(i).setImbTel1(rs.getString("AGE_PHONE1"));
                        listImovel.get(i).setImbTel2(rs.getString("AGE_PHONE2"));
                        listImovel.get(i).setImbTel3(rs.getString("AGE_PHONE3"));
                        listImovel.get(i).setImbLogradouro(rs.getString("AGE_STREET"));
                        listImovel.get(i).setImbNumero(rs.getString("AGE_NUMBER"));
                        listImovel.get(i).setImbComplemento(rs.getString("AGE_COMPLEMENT"));
                        listImovel.get(i).setImbBairro(rs.getString("DIS_NAME"));
                        listImovel.get(i).setImbCidade(rs.getString("CIT_NAME"));
                        listImovel.get(i).setImbEstado(rs.getString("STA_NAME"));
                    }
                }
            }
            
   
        }finally{
            if (!conexao.isClosed()){
                ConexaoImbOnline.fecharConexao();
            }
        }
        return listImovel;
    }
}
