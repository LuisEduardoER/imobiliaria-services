package br.com.imobiliariaservices.dao;

import br.com.imobiliariaservices.model.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Ricardo
 */
public class RegistrarPropostaDeCompraDAO {
    
    private static enum situacao {Aguardando_analise, Em_analise, Negada, Cancelada, Aprovada};
    private static RegistrarPropostaDeCompraDAO instancia;
    private static Conexao conexaoInstanciasOnline;
    private static Conexao conexaoInstanciasAlema;
    private static Conexao conexaoInstanciasInglesa;
    protected ConexaoImbOnline daoOnline = new ConexaoImbOnline();
    protected ConexaoImbAlema daoAlema = new ConexaoImbAlema();
    protected ConexaoImbInglesa daoInglesa = new ConexaoImbInglesa();
    Connection conexao;
    
    public static RegistrarPropostaDeCompraDAO getInstance(){
        if(instancia == null){
            instancia = new RegistrarPropostaDeCompraDAO();
            conexaoInstanciasOnline = new Conexao("jdbc:postgresql://localhost:5432/BD_Online","postgres","12345678");
            conexaoInstanciasAlema = new Conexao("jdbc:postgresql://localhost:5432/BD_Alema","postgres","12345678");
            conexaoInstanciasInglesa = new Conexao("jdbc:postgresql://localhost:5432/BD_Inglesa","postgres","12345678");
        }
        return instancia;
    }
    
    public boolean registrarProposta(String nome, String cpf, String cnpj, int idOrigem, double valor, String descricao) throws Exception{
        
        //Verifica se nao tem proposta aporovada
        String temAprovado =  VerificaAprovacao(idOrigem, cnpj);
        int id = 0;
                    
        //Se não tem aprovada    
        if (temAprovado.equals("f")){
            
            //Obtem CNPJ da ImbOnline
            String cnpjOnline = CNPJImbOnline();
          
            //Se CNPJ nao eh igual da Online
            if(!cnpj.equals(cnpjOnline)){
             
                //Obtem CNPJ da ImbAlema
                String cnpjAlema = CNPJImbAlema();
                
                //Obtem CNPJ da ImbInglesa
                String cnpjInglesa = CNPJImbInglesa();
                
                //Se o cnpj é da ImbAlema cadatra dados do imovel na ImbOnline 
                if(cnpj.equals(cnpjAlema)) id = CadastraImovelDeAlema(idOrigem);
                                    
                //Se o cnpj é da ImbInglesa cadatra dados do imovel na ImbOnline 
                else if(cnpj.equals(cnpjInglesa))id = CadastraImovelDeInglesa(idOrigem);
                
                //Se o CNPJ nao eh conhecido
                else return false;
            }
            else id=idOrigem;
       
            //Cadastra proposta na ImbOnline
            try{
                conexao = daoOnline.abrirConexao(true, conexaoInstanciasOnline);
                Statement stmt = conexao.createStatement();

                StringBuilder sqlProposta = new StringBuilder();
                sqlProposta.append("INSERT INTO PROPOSTA_DE_COMPRA (PDC_VALOR, PDC_DATA, PDC_SITUACAO, PDC_DECRICAO, PDC_IMB_CNPJ, PDC_ID_ORIGEM, PDC_CLI_CPF, PDC_IMV_ID) VALUES ('");
                sqlProposta.append(valor).append("', ");
                sqlProposta.append("CURRENT_DATE, '").append(situacao.Aguardando_analise).append("', '");
                sqlProposta.append(descricao).append("', '");
                sqlProposta.append(cnpj).append("', ");
                sqlProposta.append(idOrigem).append(", '");
                sqlProposta.append(cpf).append("', ");
                sqlProposta.append(id).append("); ");
                stmt.executeUpdate(sqlProposta.toString());                      
            }finally{
                if (!conexao.isClosed()){
                    ConexaoImbOnline.fecharConexao();
                }
            }
            return true;
        }
        
        //Se tem aprovada
        else return false;
        
    }
    
    private String VerificaAprovacao(int idOrigem, String cnpj) throws SQLException{
        String temAprovado = null;
        try{
            conexao = daoOnline.abrirConexao(false, conexaoInstanciasOnline);
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            String sql = "select coalesce ((select distinct 1=1 as situacao from PROPOSTA_DE_COMPRA where PDC_ID_ORIGEM=? and PDC_IMB_CNPJ=? and PDC_SITUACAO='Aprovada'),'f')='t' as situacao";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idOrigem);
            stmt.setString(2, cnpj);
            rs = stmt.executeQuery();
            while(rs.next()){
                temAprovado = rs.getString("situacao");
            }
        }finally{
            if (!conexao.isClosed()){
                ConexaoImbOnline.fecharConexao();
            }
        }
        return temAprovado;
    }
    
    private String CNPJImbOnline() throws SQLException{
        String cnpj = null;
        try{
            conexao = daoOnline.abrirConexao(false, conexaoInstanciasOnline);          
            Statement stmt = conexao.createStatement();
            ResultSet rs = null;

            StringBuilder sql = new StringBuilder();
            sql.append("select imb_cnpj from imobiliaria where imb_nome='Imobiliaria Rio de Janeiro'");
            rs = stmt.executeQuery(sql.toString());
            while(rs.next()){
                cnpj = rs.getString("imb_cnpj");
            }
        }finally{
            if (!conexao.isClosed()){
                ConexaoImbOnline.fecharConexao();
            }
        }
        return cnpj;
    }
    
    private String CNPJImbAlema() throws SQLException{
        String cnpj = null;
        try{
            conexao = daoAlema.abrirConexao(false, conexaoInstanciasAlema);          
            Statement stmt = conexao.createStatement();
            ResultSet rs = null;

            StringBuilder sql = new StringBuilder();
            sql.append("select MAK_CNPJ from MAKLERBUERO where MAK_NAME='Imobiliaria Berlim'");
            rs = stmt.executeQuery(sql.toString());
            while(rs.next()){
                cnpj = rs.getString("MAK_CNPJ");
            }
        }finally{
            if (!conexao.isClosed()){
                ConexaoImbOnline.fecharConexao();
            }
        }
        return cnpj;
    }
    
    private String CNPJImbInglesa() throws SQLException{
        String cnpj = null;
        try{
            conexao = daoInglesa.abrirConexao(false, conexaoInstanciasInglesa);          
            Statement stmt = conexao.createStatement();
            ResultSet rs = null;

            StringBuilder sql = new StringBuilder();
            sql.append("select AGE_CNPJ from AGENCY where AGE_NAME='Imobiliaria Londres'");
            rs = stmt.executeQuery(sql.toString());
            while(rs.next()){
                cnpj = rs.getString("AGE_CNPJ");
            }
        }finally{
            if (!conexao.isClosed()){
                ConexaoImbOnline.fecharConexao();
            }
        }
        return cnpj;
    }
    
    private int CadastraImovelDeAlema(int idOrigem) throws SQLException{
        Imovel imovel = null;
        //Obtem dados do imovel
        try{
            conexao = daoAlema.abrirConexao(false, conexaoInstanciasAlema);
            PreparedStatement stmt = null;
            ResultSet rs = null;

            String sql = "select * from IMMOBILIE where IMM_ID=?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idOrigem);
            rs = stmt.executeQuery();
            while(rs.next()){
                imovel = new Imovel();
                imovel.setImvPreco(rs.getDouble("IMM_PREIS"));
                imovel.setImvArea(rs.getString("IMM_FLAECHE"));
                imovel.setImvNumQuartos(rs.getInt("IMM_ZIMMERQUANTITAET"));
                imovel.setImvPosVagGaragem(rs.getBoolean("IMM_EGR_PARKPLATZ"));
                imovel.setImvHorSol(rs.getString("IMM_SONNENSTUNDEN"));
                imovel.setImvOtrDetalhes(rs.getString("IMM_ADE_EINZELHEITEN"));
                imovel.setImvLogradouro(rs.getString("IMM_STRASSE"));
                imovel.setImvNumero(rs.getInt("IMM_ANZAHL"));
                imovel.setImvComplemento(rs.getString("IMM_ERGARNZUNG"));
            }
        }finally{
            if (!conexao.isClosed()){
                ConexaoImbOnline.fecharConexao();
            }
        }
        //Cadastro do imovel na ImbOnline
        int id = CadastraImovelNaOnline(imovel);
        //Cadastro de correspondencia do imovel a ImbAlema na ImbOnline
        CadastraCNPJ(id, CNPJImbAlema());
        return id;
    }
    
    private int CadastraImovelDeInglesa(int idOrigem) throws SQLException{
        Imovel imovel = null;
        //Obtem dados do imovel
        try{
            conexao = daoInglesa.abrirConexao(false, conexaoInstanciasInglesa);
            PreparedStatement stmt = null;
            ResultSet rs = null;

            String sql = "select * from IMMOBILE where IMM_ID=?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idOrigem);
            rs = stmt.executeQuery();
            while(rs.next()){
                imovel = new Imovel();
                imovel.setImvPreco(rs.getDouble("IMM_PRICE"));
                imovel.setImvArea(rs.getString("IMM_AREA"));
                imovel.setImvNumQuartos(rs.getInt("IMM_NUM_ROOMS"));
                imovel.setImvPosVagGaragem(rs.getBoolean("IMM_PRK_SPACE"));
                imovel.setImvHorSol(rs.getString("IMM_SUN_HOUR"));
                imovel.setImvOtrDetalhes(rs.getString("IMM_OTR_DETAILS"));
                imovel.setImvLogradouro(rs.getString("IMM_STREET"));
                imovel.setImvNumero(rs.getInt("IMM_NUMBER"));
                imovel.setImvComplemento(rs.getString("IMM_COMPLEMENT"));
            }
        }finally{
            if (!conexao.isClosed()){
                ConexaoImbOnline.fecharConexao();
            }
        }
        //Cadastro do imovel na ImbOnline
        int id = CadastraImovelNaOnline(imovel);
        //Cadastro de correspondencia do imovel a ImbInlgesa na ImbOnline
        CadastraCNPJ(id, CNPJImbInglesa());
        return id;
    }
    
    private int CadastraImovelNaOnline (Imovel imovel) throws SQLException{
        int id = 0;
        try{
            conexao = daoOnline.abrirConexao(true, conexaoInstanciasOnline);
            PreparedStatement stmt = null;

            String sql = "INSERT INTO IMOVEL (IMV_PRECO, IMV_AREA, IMV_NUM_QUARTOS, IMV_POS_VAG_GARAGEM, IMV_HOR_SOL, IMV_OTR_DETALHES, IMV_LOGRADOURO, IMV_NUMERO, IMV_COMPLEMENTO) VALUES (?,?,?,?,?,?,?,?,?)";
            stmt = conexao.prepareStatement(sql);
            stmt.setDouble(1, imovel.getImvPreco());
            stmt.setString(2, imovel.getImvArea());
            stmt.setInt(3, imovel.getImvNumQuartos());
            stmt.setBoolean(4, imovel.isImvPosVagGaragem());
            stmt.setString(5, imovel.getImvHorSol());
            stmt.setString(6, imovel.getImvOtrDetalhes());
            stmt.setString(7, imovel.getImvLogradouro());
            stmt.setInt(8, imovel.getImvNumero());
            stmt.setString(9, imovel.getImvComplemento());
            stmt.executeUpdate();
                            
            ResultSet rs = null;
            String imv_id = "select max (imv_id) as imv_id from imovel";
            stmt = conexao.prepareStatement(imv_id);
            rs = stmt.executeQuery();
            while(rs.next()){
                id = rs.getInt("imv_id");
            }
        }finally{
            if (!conexao.isClosed()){
                ConexaoImbOnline.fecharConexao();
            }
        }
        return id;
    }
    
    private void CadastraCNPJ(int id, String cnpj) throws SQLException{
        try{
            conexao = daoOnline.abrirConexao(true, conexaoInstanciasOnline);
            PreparedStatement stmt = null;

            String sql = "INSERT INTO POSSUI (POS_IMV_ID, POS_IMB_CNPJ) VALUES (?,?)";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, cnpj);
            stmt.executeUpdate();
        }finally{
            if (!conexao.isClosed()){
                ConexaoImbOnline.fecharConexao();
            }
        }
    }
}