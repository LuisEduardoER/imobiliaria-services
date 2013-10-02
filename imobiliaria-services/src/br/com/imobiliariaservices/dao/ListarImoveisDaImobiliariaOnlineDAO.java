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
public class ListarImoveisDaImobiliariaOnlineDAO {
    
    private static ListarImoveisDaImobiliariaOnlineDAO instancia;
    private static Conexao conexaoInstancias;
    protected ConexaoImbOnline dao = new ConexaoImbOnline();
    Connection conexao;
    
    public static ListarImoveisDaImobiliariaOnlineDAO getInstance(){
        if(instancia == null){
            instancia = new ListarImoveisDaImobiliariaOnlineDAO();
            conexaoInstancias = new Conexao("jdbc:postgresql://localhost:5432/BD_Online","postgres","12345678");
        }
        return instancia;
    }
    
    public List<Imovel> listarOnline(List<String> bairro, double valMin, double valMax, int numQuartos, boolean posVagGaragem, String horSol) throws Exception{
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
            sqlImv.append("select * from imovel inner join bairro on imv_bro_id=bro_id inner join cidade on bro_cid_id=cid_id inner join estado on est_id=cid_est_id inner join possui on pos_imv_id=imv_id where (bro_nome = '");
            for(int i=0; i<numBairros; i++){
                if (tam>1) {
                    sqlImv.append(bairro.get(i)).append("' or bro_nome = '");
                    tam--;
                }
                else sqlImv.append(bairro.get(i)).append("') and imv_preco >= ");
            }
            sqlImv.append(valMin).append(" and imv_preco <= ");
            sqlImv.append(valMax).append(" and imv_num_quartos = ");
            sqlImv.append(numQuartos).append(" and imv_pos_vag_garagem = ");
            sqlImv.append(posVagGaragem).append(" and imv_hor_sol = '");
            sqlImv.append(horSol).append("'");
            rs = stmt.executeQuery(sqlImv.toString());
            
            while(rs.next()){
                imovel = new Imovel();
                imovel.setImvId(rs.getInt("imv_id"));
                imovel.setImvPreco(rs.getDouble("imv_preco"));
                imovel.setImvArea(rs.getString("imv_area"));
                imovel.setImvNumQuartos(rs.getInt("imv_num_quartos"));
                imovel.setImvPosVagGaragem(rs.getBoolean("imv_pos_vag_garagem"));
                imovel.setImvHorSol(rs.getString("imv_hor_sol"));
                imovel.setImvOtrDetalhes(rs.getString("imv_otr_detalhes"));
                imovel.setImvLogradouro(rs.getString("imv_logradouro"));
                imovel.setImvNumero(rs.getInt("imv_numero"));
                imovel.setImvComplemento(rs.getString("imv_complemento"));
                imovel.setImvBairro(rs.getString("bro_nome"));
                imovel.setImvCidade(rs.getString("cid_nome"));
                imovel.setImvEstado(rs.getString("est_nome"));
                listImovel.add(imovel);
                index++;
            }
            
            if(index!=-1){
                for(int i=0;i<=index;i++){
                    StringBuilder sqlImb = new StringBuilder();
                    sqlImb.append("select * from IMOBILIARIA inner join POSSUI on POS_IMB_CNPJ=IMB_CNPJ inner join BAIRRO on IMB_BRO_ID=BRO_ID inner join CIDADE on BRO_CID_ID=CID_ID inner join ESTADO on EST_ID=CID_EST_ID where POS_IMV_ID = ");
                    sqlImb.append(listImovel.get(i).getImvId());
                    rs = stmt.executeQuery(sqlImb.toString());

                    while(rs.next()){   
                        listImovel.get(i).setImbCnpj(rs.getString("imb_cnpj"));
                        listImovel.get(i).setImbNome(rs.getString("imb_nome"));
                        listImovel.get(i).setImbTel1(rs.getString("imb_tel1"));
                        listImovel.get(i).setImbTel2(rs.getString("imb_tel2"));
                        listImovel.get(i).setImbTel3(rs.getString("imb_tel3"));
                        listImovel.get(i).setImbLogradouro(rs.getString("imb_logradouro"));
                        listImovel.get(i).setImbNumero(rs.getString("imb_numero"));
                        listImovel.get(i).setImbComplemento(rs.getString("imb_complemento"));
                        listImovel.get(i).setImbBairro(rs.getString("bro_nome"));
                        listImovel.get(i).setImbCidade(rs.getString("cid_nome"));
                        listImovel.get(i).setImbEstado(rs.getString("est_nome")); 
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
