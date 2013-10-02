
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
public class ListarImoveisDaImobiliariaAlemaDAO {
    
    private static ListarImoveisDaImobiliariaAlemaDAO instancia;
    private static Conexao conexaoInstancias;
    protected ConexaoImbAlema dao = new ConexaoImbAlema();
    Connection conexao;
    
    public static ListarImoveisDaImobiliariaAlemaDAO getInstance(){
        if(instancia == null){
            instancia = new ListarImoveisDaImobiliariaAlemaDAO();
            conexaoInstancias = new Conexao("jdbc:postgresql://localhost:5432/BD_Alema","postgres","12345678");
        }
        return instancia;
    }
    
    public List<Imovel> listarAlema(List<String> bairro, double valMin, double valMax, int numQuartos, boolean posVagGaragem, String horSol) throws Exception{
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
            sqlImv.append("select * from IMMOBILIE inner join STADTTEIL on IMM_STL_ID=STL_ID inner join STADT on STL_STT_ID=STT_ID inner join BUNDESLAND on BUN_ID=STT_BUN_ID where (STL_NAME = '");
            for(int i=0; i<numBairros; i++){
                if (tam>1) {
                    sqlImv.append(bairro.get(i)).append("' or STL_NAME = '");
                    tam--;
                }
                else sqlImv.append(bairro.get(i)).append("') and IMM_PREIS >= ");
            }
            sqlImv.append(valMin).append(" and IMM_PREIS <= ");
            sqlImv.append(valMax).append(" and IMM_ZIMMERQUANTITAET = ");
            sqlImv.append(numQuartos).append(" and IMM_EGR_PARKPLATZ = ");
            sqlImv.append(posVagGaragem).append(" and IMM_SONNENSTUNDEN = '");
            sqlImv.append(horSol).append("'");
            rs = stmt.executeQuery(sqlImv.toString());
            
            while(rs.next()){   
                imovel = new Imovel();
                imovel.setImvId(rs.getInt("IMM_ID"));
                imovel.setImvPreco(rs.getDouble("IMM_PREIS"));
                imovel.setImvArea(rs.getString("IMM_FLAECHE"));
                imovel.setImvNumQuartos(rs.getInt("IMM_ZIMMERQUANTITAET"));
                imovel.setImvPosVagGaragem(rs.getBoolean("IMM_EGR_PARKPLATZ"));
                imovel.setImvHorSol(rs.getString("IMM_SONNENSTUNDEN"));
                imovel.setImvOtrDetalhes(rs.getString("IMM_ADE_EINZELHEITEN"));
                imovel.setImvLogradouro(rs.getString("IMM_STRASSE"));
                imovel.setImvNumero(rs.getInt("IMM_ANZAHL"));
                imovel.setImvComplemento(rs.getString("IMM_ERGARNZUNG"));
                imovel.setImvBairro(rs.getString("STL_NAME"));
                imovel.setImvCidade(rs.getString("STT_NAME"));
                imovel.setImvEstado(rs.getString("BUN_NAME"));
                listImovel.add(imovel);
                index++;
            }
            
            if(index!=-1){
                for(int i=0;i<=index;i++){
                    StringBuilder sqlImb = new StringBuilder();
                    sqlImb.append("select * from MAKLERBUERO inner join IMMOBILIE on IMM_MAK_CNPJ=MAK_CNPJ inner join STADTTEIL on MAK_STL_ID=STL_ID inner join STADT on STL_STT_ID=STT_ID inner join BUNDESLAND on BUN_ID=STT_BUN_ID where IMM_ID = ");
                    sqlImb.append(listImovel.get(i).getImvId());
                    rs = stmt.executeQuery(sqlImb.toString());

                    while(rs.next()){   
                        listImovel.get(i).setImbCnpj(rs.getString("MAK_CNPJ"));
                        listImovel.get(i).setImbNome(rs.getString("MAK_NAME"));
                        listImovel.get(i).setImbTel1(rs.getString("MAK_TELEFONNUMMER1"));
                        listImovel.get(i).setImbTel2(rs.getString("MAK_TELEFONNUMMER2"));
                        listImovel.get(i).setImbTel3(rs.getString("MAK_TELEFONNUMMER3"));
                        listImovel.get(i).setImbLogradouro(rs.getString("MAK_STRASSE"));
                        listImovel.get(i).setImbNumero(rs.getString("MAK_ANZAHL"));
                        listImovel.get(i).setImbComplemento(rs.getString("MAK_ERGARNZUNG"));
                        listImovel.get(i).setImbBairro(rs.getString("STL_NAME"));
                        listImovel.get(i).setImbCidade(rs.getString("STT_NAME"));
                        listImovel.get(i).setImbEstado(rs.getString("BUN_NAME"));
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
