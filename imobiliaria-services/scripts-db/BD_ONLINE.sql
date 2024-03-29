--Remo��o das tabelas
DROP TABLE IMOBILIARIA CASCADE;
DROP TABLE PARCEIRA CASCADE;
DROP TABLE IMOVEL CASCADE;
DROP TABLE FOTO CASCADE;
DROP TABLE CRITERIOS_DE_PESQUISA CASCADE;
DROP TABLE CLIENTE CASCADE;
DROP TABLE PROPOSTA_DE_COMPRA CASCADE;
DROP TABLE PERTENCE CASCADE;
DROP TABLE POSSUI CASCADE;
DROP TABLE BAIRRO CASCADE;
DROP TABLE CIDADE CASCADE;
DROP TABLE ESTADO CASCADE;
DROP TABLE DADOS CASCADE;

--Cria��o das tabelas
CREATE TABLE IMOBILIARIA 
    ( 
     IMB_CNPJ CHARACTER VARYING (50) NOT NULL,
     IMB_NOME CHARACTER VARYING (50) NOT NULL, 
     IMB_TEL1 CHARACTER VARYING (50),
     IMB_TEL2 CHARACTER VARYING (50),
     IMB_TEL3 CHARACTER VARYING (50),
     IMB_LOGRADOURO CHARACTER VARYING (100),
     IMB_NUMERO INTEGER,
     IMB_COMPLEMENTO CHARACTER VARYING (100),
     IMB_BRO_ID INTEGER
    ) 
;

ALTER TABLE IMOBILIARIA 
    ADD CONSTRAINT IMOBILIARIA_PK PRIMARY KEY ( IMB_CNPJ ) ;

CREATE TABLE PARCEIRA
    (  
     PAR_IMB_CNPJ1 CHARACTER VARYING (50) NOT NULL,
     PAR_IMB_CNPJ2 CHARACTER VARYING (50) NOT NULL
    ) 
;

ALTER TABLE PARCEIRA 
    ADD CONSTRAINT PAR_IMOBILIARIA1_FK FOREIGN KEY 
    ( 
     PAR_IMB_CNPJ1
    ) 
    REFERENCES IMOBILIARIA  
    ( 
     IMB_CNPJ
    ) 
;

ALTER TABLE PARCEIRA 
    ADD CONSTRAINT PAR_IMOBILIARIA2_FK FOREIGN KEY 
    ( 
     PAR_IMB_CNPJ2
    ) 
    REFERENCES IMOBILIARIA  
    ( 
     IMB_CNPJ
    ) 
;

CREATE TABLE IMOVEL 
    ( 
     IMV_ID SERIAL, 
     IMV_PRECO DOUBLE PRECISION  NOT NULL,
     IMV_AREA CHARACTER VARYING (50) NOT NULL, 
     IMV_NUM_QUARTOS INTEGER NOT NULL,
     IMV_POS_VAG_GARAGEM BOOLEAN NOT NULL,
     IMV_HOR_SOL CHARACTER VARYING (20) NOT NULL,
     IMV_OTR_DETALHES CHARACTER VARYING (100),
     IMV_LOGRADOURO CHARACTER VARYING (100) NOT NULL,
     IMV_NUMERO INTEGER NOT NULL,
     IMV_COMPLEMENTO CHARACTER VARYING (100),
     IMV_CLI_CPF CHARACTER VARYING (14),
     IMV_BRO_ID INTEGER
    ) 
;

ALTER TABLE IMOVEL 
    ADD CONSTRAINT IMOVEL_PK PRIMARY KEY ( IMV_ID ) ;

CREATE TABLE FOTO
    ( 
     FTO_IMAGEM CHARACTER VARYING (50) NOT NULL,
     FTO_NOME CHARACTER VARYING (100) NOT NULL,
     FTO_IMV_ID INTEGER NOT NULL
    ) 
;

ALTER TABLE FOTO 
    ADD CONSTRAINT FTO_IMOVEL_FK FOREIGN KEY 
    ( 
     FTO_IMV_ID
    ) 
    REFERENCES IMOVEL  
    ( 
     IMV_ID
    ) 
;

CREATE TABLE CRITERIOS_DE_PESQUISA 
    ( 
     CDP_ID SERIAL, 
     CDP_NUM_QUARTOS INTEGER NOT NULL,
     CDP_POS_VAG_GARAGEM BOOLEAN NOT NULL,
     CDP_DTA_PESQUISA DATE NOT NULL,
     CDP_HOR_SOL CHARACTER VARYING (20) NOT NULL,
     CDP_VAL_MAX DOUBLE PRECISION  NOT NULL,
     CDP_VAL_MIN DOUBLE PRECISION  NOT NULL,
     CDP_CLI_CPF CHARACTER VARYING (14) NOT NULL
    ) 
;

ALTER TABLE CRITERIOS_DE_PESQUISA 
    ADD CONSTRAINT CRITERIOS_DE_PESQUISA_PK PRIMARY KEY ( CDP_ID ) ;

CREATE TABLE CLIENTE 
    ( 
     CLI_CPF CHARACTER VARYING (14) NOT NULL,
     CLI_NOME CHARACTER VARYING (100) NOT NULL,
     CLI_TEL_RES CHARACTER VARYING (50),
     CLI_TEL_CEL CHARACTER VARYING (50),
     CLI_TEL_COM CHARACTER VARYING (50),
     CLI_EMAIL CHARACTER VARYING (50) NOT NULL,
     CLI_LOGRADOURO CHARACTER VARYING (100) NOT NULL,
     CLI_NUMERO INTEGER NOT NULL,
     CLI_COMPLEMENTO CHARACTER VARYING (100),
     CLI_BRO_ID INTEGER NOT NULL
    ) 
;

ALTER TABLE CLIENTE 
    ADD CONSTRAINT CLIENTE_PK PRIMARY KEY ( CLI_CPF ) ;

ALTER TABLE CRITERIOS_DE_PESQUISA 
    ADD CONSTRAINT CLI_CLIETNE_FK FOREIGN KEY 
    ( 
     CDP_CLI_CPF
    ) 
    REFERENCES CLIENTE  
    ( 
     CLI_CPF
    ) 
;

ALTER TABLE IMOVEL
    ADD CONSTRAINT IMV_CLIENTE_FK FOREIGN KEY 
    ( 
     IMV_CLI_CPF
    ) 
    REFERENCES CLIENTE  
    ( 
     CLI_CPF
    ) 
;

CREATE TABLE PROPOSTA_DE_COMPRA 
    ( 
     PDC_VALOR DOUBLE PRECISION  NOT NULL,
     PDC_DATA DATE NOT NULL,
     PDC_SITUACAO CHARACTER VARYING (50) NOT NULL,
     PDC_DECRICAO CHARACTER VARYING (100),
     PDC_IMB_CNPJ CHARACTER VARYING (50) NOT NULL,
     PDC_ID_ORIGEM INTEGER NOT NULL,
     PDC_CLI_CPF CHARACTER VARYING (14) NOT NULL,
     PDC_IMV_ID INTEGER NOT NULL
    ) 
;

ALTER TABLE PROPOSTA_DE_COMPRA 
    ADD CONSTRAINT PDC_IMOBILIARIA_FK FOREIGN KEY 
    ( 
     PDC_IMB_CNPJ
    ) 
    REFERENCES IMOBILIARIA  
    ( 
     IMB_CNPJ
    ) 
;

ALTER TABLE PROPOSTA_DE_COMPRA 
    ADD CONSTRAINT PDC_CLIENTE_FK FOREIGN KEY 
    ( 
     PDC_CLI_CPF
    ) 
    REFERENCES CLIENTE  
    ( 
     CLI_CPF
    ) 
;

ALTER TABLE PROPOSTA_DE_COMPRA 
    ADD CONSTRAINT PDC_IMOVEL_FK FOREIGN KEY 
    ( 
     PDC_IMV_ID
    ) 
    REFERENCES IMOVEL  
    ( 
     IMV_ID
    ) 
;

CREATE TABLE PERTENCE 
    ( 
     PRT_CLI_CPF CHARACTER VARYING (14) NOT NULL,
     PRT_IMB_CNPJ CHARACTER VARYING (50) NOT NULL
    ) 
;

ALTER TABLE PERTENCE 
    ADD CONSTRAINT PRT_CLIENTE_FK FOREIGN KEY 
    ( 
     PRT_CLI_CPF
    ) 
    REFERENCES CLIENTE  
    ( 
     CLI_CPF
    ) 
;

ALTER TABLE PERTENCE 
    ADD CONSTRAINT PRT_IMOBILIARIA_FK FOREIGN KEY 
    ( 
     PRT_IMB_CNPJ
    ) 
    REFERENCES IMOBILIARIA  
    ( 
     IMB_CNPJ
    ) 
;

CREATE TABLE POSSUI 
    ( 
     POS_IMV_ID INTEGER NOT NULL,
     POS_IMB_CNPJ CHARACTER VARYING (50) NOT NULL
    ) 
;

CREATE TABLE BAIRRO 
    ( 
     BRO_ID SERIAL,
     BRO_NOME CHARACTER VARYING (100) NOT NULL, 
     BRO_CID_ID INTEGER NOT NULL
    ) 
;

ALTER TABLE BAIRRO 
    ADD CONSTRAINT BAIRRO_PK PRIMARY KEY ( BRO_ID ) ;

CREATE TABLE CIDADE 
    ( 
     CID_ID SERIAL,
     CID_NOME CHARACTER VARYING (100) NOT NULL, 
     CID_EST_ID INTEGER NOT NULL
    ) 
;

ALTER TABLE CIDADE 
    ADD CONSTRAINT CIDADE_PK PRIMARY KEY ( CID_ID ) ;

CREATE TABLE ESTADO 
    ( 
     EST_ID SERIAL,
     EST_NOME CHARACTER VARYING (100) NOT NULL
    ) 
;

ALTER TABLE ESTADO 
    ADD CONSTRAINT ESTADO_PK PRIMARY KEY ( EST_ID ) ;

CREATE TABLE DADOS
    ( 
     DAD_CDP_ID SERIAL,
     DAD_NOME_BAIRRO CHARACTER VARYING (100) NOT NULL, 
     DAD_CNPJ_IMOBILIARIA CHARACTER VARYING (50) NOT NULL
    ) 
;

ALTER TABLE DADOS 
    ADD CONSTRAINT DAD_IMOBILIARIA_FK FOREIGN KEY 
    ( 
     DAD_CNPJ_IMOBILIARIA
    ) 
    REFERENCES IMOBILIARIA  
    ( 
     IMB_CNPJ
    ) 
;

ALTER TABLE DADOS 
    ADD CONSTRAINT DAD_CRITERIOS_DE_PESQUISA_FK FOREIGN KEY 
    ( 
     DAD_CDP_ID
    ) 
    REFERENCES CRITERIOS_DE_PESQUISA  
    ( 
     CDP_ID
    ) 
;

ALTER TABLE IMOVEL 
    ADD CONSTRAINT IMV_BAIRRO_FK FOREIGN KEY 
    ( 
     IMV_BRO_ID
    ) 
    REFERENCES BAIRRO  
    ( 
     BRO_ID
    ) 
;

ALTER TABLE IMOBILIARIA 
    ADD CONSTRAINT IMB_BAIRRO_FK FOREIGN KEY 
    ( 
     IMB_BRO_ID
    ) 
    REFERENCES BAIRRO  
    ( 
     BRO_ID
    ) 
;

ALTER TABLE CLIENTE 
    ADD CONSTRAINT CLI_BAIRRO_FK FOREIGN KEY 
    ( 
     CLI_BRO_ID
    ) 
    REFERENCES BAIRRO  
    ( 
     BRO_ID
    ) 
;

ALTER TABLE POSSUI 
    ADD CONSTRAINT POS_IMOVEL_FK FOREIGN KEY 
    ( 
     POS_IMV_ID
    ) 
    REFERENCES IMOVEL  
    ( 
     IMV_ID
    ) 
;

ALTER TABLE POSSUI 
    ADD CONSTRAINT POS_IMOBILIARIA_FK FOREIGN KEY 
    ( 
     POS_IMB_CNPJ
    ) 
    REFERENCES IMOBILIARIA  
    ( 
     IMB_CNPJ
    ) 
;


--Inserts nas tabelas
INSERT INTO ESTADO (EST_NOME) VALUES ('Rio de Janeiro');

INSERT INTO CIDADE (CID_NOME, CID_EST_ID) VALUES ('Rio de Janeiro', (select EST_ID from ESTADO where EST_NOME='Rio de Janeiro'));
INSERT INTO CIDADE (CID_NOME, CID_EST_ID) VALUES ('Cabo Frio', (select EST_ID from ESTADO where EST_NOME='Rio de Janeiro'));

INSERT INTO BAIRRO (BRO_NOME, BRO_CID_ID) VALUES ('Ipanema', (select CID_ID from CIDADE where CID_NOME='Rio de Janeiro'));
INSERT INTO BAIRRO (BRO_NOME, BRO_CID_ID) VALUES ('Copacabana', (select CID_ID from CIDADE where CID_NOME='Rio de Janeiro'));
INSERT INTO BAIRRO (BRO_NOME, BRO_CID_ID) VALUES ('Urca', (select CID_ID from CIDADE where CID_NOME='Rio de Janeiro'));
INSERT INTO BAIRRO (BRO_NOME, BRO_CID_ID) VALUES ('Tijuca', (select CID_ID from CIDADE where CID_NOME='Rio de Janeiro'));
INSERT INTO BAIRRO (BRO_NOME, BRO_CID_ID) VALUES ('Barra da Tijuca', (select CID_ID from CIDADE where CID_NOME='Rio de Janeiro'));
INSERT INTO BAIRRO (BRO_NOME, BRO_CID_ID) VALUES ('Centro', (select CID_ID from CIDADE where CID_NOME='Cabo Frio'));

INSERT INTO IMOBILIARIA (IMB_CNPJ, IMB_NOME, IMB_TEL1, IMB_LOGRADOURO, IMB_NUMERO, IMB_COMPLEMENTO, IMB_BRO_ID) VALUES ('14.218.935/0001-27', 'Imobiliaria Rio de Janeiro', '(21) 2440-7766', 'Av Nossa Senhora de Copacabana', 900, 'Loja 2', (select BRO_ID from BAIRRO where BRO_NOME='Copacabana'));
INSERT INTO IMOBILIARIA (IMB_CNPJ, IMB_NOME, IMB_TEL1) VALUES ('14.218.835/0001-27', 'Imobiliaria Londres', '(11) 3033-4878');
INSERT INTO IMOBILIARIA (IMB_CNPJ, IMB_NOME, IMB_TEL1) VALUES ('03.847.655/0001-98', 'Imobiliaria Berlim', '(85) 2273-0135');

INSERT INTO PARCEIRA (PAR_IMB_CNPJ1, PAR_IMB_CNPJ2) VALUES ('14.218.935/0001-27', '14.218.835/0001-27');
INSERT INTO PARCEIRA (PAR_IMB_CNPJ1, PAR_IMB_CNPJ2) VALUES ('14.218.935/0001-27', '03.847.655/0001-98');

INSERT INTO IMOVEL (IMV_PRECO, IMV_AREA, IMV_NUM_QUARTOS, IMV_POS_VAG_GARAGEM, IMV_HOR_SOL, IMV_LOGRADOURO, IMV_NUMERO, IMV_COMPLEMENTO, IMV_BRO_ID) VALUES (1800000.00, '150m2', 2, true, 'Manha', 'Av Vieira Souto', 396, 'AP101', (select BRO_ID from BAIRRO inner join CIDADE on BRO_CID_ID=CID_ID where BRO_NOME='Ipanema' and CID_NOME='Rio de Janeiro'));
INSERT INTO IMOVEL (IMV_PRECO, IMV_AREA, IMV_NUM_QUARTOS, IMV_POS_VAG_GARAGEM, IMV_HOR_SOL, IMV_LOGRADOURO, IMV_NUMERO, IMV_COMPLEMENTO, IMV_BRO_ID) VALUES (1500000.00, '180m2', 2, true, 'Manha', 'Av Atlantica', 800, 'AP104', (select BRO_ID from BAIRRO inner join CIDADE on BRO_CID_ID=CID_ID where BRO_NOME='Copacabana' and CID_NOME='Rio de Janeiro'));
INSERT INTO IMOVEL (IMV_PRECO, IMV_AREA, IMV_NUM_QUARTOS, IMV_POS_VAG_GARAGEM, IMV_HOR_SOL, IMV_LOGRADOURO, IMV_NUMERO, IMV_COMPLEMENTO, IMV_BRO_ID) VALUES (650000.00, '110m2', 2, true, 'Manha', 'Rua Haddock Lobo', 400, 'AP707', (select BRO_ID from BAIRRO inner join CIDADE on BRO_CID_ID=CID_ID where BRO_NOME='Tijuca' and CID_NOME='Rio de Janeiro'));
INSERT INTO IMOVEL (IMV_PRECO, IMV_AREA, IMV_NUM_QUARTOS, IMV_POS_VAG_GARAGEM, IMV_HOR_SOL, IMV_LOGRADOURO, IMV_NUMERO, IMV_COMPLEMENTO, IMV_BRO_ID) VALUES (810000.00, '115m2', 3, true, 'Tarde', 'Avenida Erico Verissimo', 656, 'AP303', (select BRO_ID from BAIRRO inner join CIDADE on BRO_CID_ID=CID_ID where BRO_NOME='Barra da Tijuca' and CID_NOME='Rio de Janeiro'));
INSERT INTO IMOVEL (IMV_PRECO, IMV_AREA, IMV_NUM_QUARTOS, IMV_POS_VAG_GARAGEM, IMV_HOR_SOL, IMV_LOGRADOURO, IMV_NUMERO, IMV_COMPLEMENTO, IMV_BRO_ID) VALUES (480000.00, '65m2', 1, false, 'Manha', 'Avenida Francisco Mendes', 285, 'Casa', (select BRO_ID from BAIRRO inner join CIDADE on BRO_CID_ID=CID_ID where BRO_NOME='Centro' and CID_NOME='Cabo Frio'));

INSERT INTO POSSUI (POS_IMV_ID, POS_IMB_CNPJ) VALUES ((select IMV_ID from IMOVEL where IMV_PRECO=1800000.00 and IMV_AREA='150m2' and IMV_BRO_ID=(select BRO_ID from BAIRRO inner join CIDADE on BRO_CID_ID=CID_ID where BRO_NOME='Ipanema' and CID_NOME='Rio de Janeiro')), (select IMB_CNPJ from IMOBILIARIA where IMB_CNPJ='14.218.935/0001-27'));
INSERT INTO POSSUI (POS_IMV_ID, POS_IMB_CNPJ) VALUES ((select IMV_ID from IMOVEL where IMV_PRECO=1500000.00 and IMV_AREA='180m2' and IMV_BRO_ID=(select BRO_ID from BAIRRO inner join CIDADE on BRO_CID_ID=CID_ID where BRO_NOME='Copacabana' and CID_NOME='Rio de Janeiro')), (select IMB_CNPJ from IMOBILIARIA where IMB_CNPJ='14.218.935/0001-27'));
INSERT INTO POSSUI (POS_IMV_ID, POS_IMB_CNPJ) VALUES ((select IMV_ID from IMOVEL where IMV_PRECO=650000.00 and IMV_AREA='110m2' and IMV_BRO_ID=(select BRO_ID from BAIRRO inner join CIDADE on BRO_CID_ID=CID_ID where BRO_NOME='Tijuca' and CID_NOME='Rio de Janeiro')), (select IMB_CNPJ from IMOBILIARIA where IMB_CNPJ='14.218.935/0001-27'));
INSERT INTO POSSUI (POS_IMV_ID, POS_IMB_CNPJ) VALUES ((select IMV_ID from IMOVEL where IMV_PRECO=810000.00 and IMV_AREA='115m2' and IMV_BRO_ID=(select BRO_ID from BAIRRO inner join CIDADE on BRO_CID_ID=CID_ID where BRO_NOME='Barra da Tijuca' and CID_NOME='Rio de Janeiro')), (select IMB_CNPJ from IMOBILIARIA where IMB_CNPJ='14.218.935/0001-27'));
INSERT INTO POSSUI (POS_IMV_ID, POS_IMB_CNPJ) VALUES ((select IMV_ID from IMOVEL where IMV_PRECO=480000.00 and IMV_AREA='65m2' and IMV_BRO_ID=(select BRO_ID from BAIRRO inner join CIDADE on BRO_CID_ID=CID_ID where BRO_NOME='Centro' and CID_NOME='Cabo Frio')), (select IMB_CNPJ from IMOBILIARIA where IMB_CNPJ='14.218.935/0001-27'));

INSERT INTO CLIENTE (CLI_CPF, CLI_NOME, CLI_TEL_RES, CLI_EMAIL, CLI_LOGRADOURO, CLI_NUMERO, CLI_COMPLEMENTO, CLI_BRO_ID) VALUES ('111.222.333-45', 'Joao', '4040-7077', 'joao@joao.com.br', 'Av Pasteur', 459, 'BL1 AP15000', (select BRO_ID from BAIRRO where BRO_NOME='Urca'));