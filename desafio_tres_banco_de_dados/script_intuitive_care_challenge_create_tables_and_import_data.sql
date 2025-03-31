CREATE TEMP TABLE temp_demonstracoes (
    data TEXT,
    reg_ans TEXT,
    cd_conta_contabil TEXT,
    descricao TEXT,
    vl_saldo_inicial TEXT,
    vl_saldo_final TEXT
);

CREATE TABLE demonstracoes_contabeis (
    id SERIAL PRIMARY KEY,
    data DATE NOT NULL,
    reg_ans VARCHAR(10) NOT NULL,
    cd_conta_contabil VARCHAR(20) NOT NULL,
    descricao TEXT NOT NULL,
    vl_saldo_inicial NUMERIC(15,2) NOT NULL,
    vl_saldo_final NUMERIC(15,2) NOT NULL
);

CREATE TEMP TABLE temp_operadoras (
    registro_ans TEXT,
    cnpj TEXT,
    razao_social TEXT,
    nome_fantasia TEXT,
    modalidade TEXT,
    logradouro TEXT,
    numero VARCHAR(55),
    complemento TEXT,
    bairro TEXT,
    cidade TEXT,
    uf CHAR(2),
    cep VARCHAR(8),
    ddd VARCHAR(2),
    telefone TEXT,
    fax TEXT,
    endereco_eletronico TEXT,
    representante TEXT,
    cargo_representante TEXT,
    regiao_de_comercializacao INT,
    data_registro_ans TEXT
);

CREATE TABLE operadoras_de_plano_de_saude_ativas (
    id SERIAL PRIMARY KEY,
    registro_ans VARCHAR(10) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    razao_social TEXT NOT NULL,
    nome_fantasia TEXT,
    modalidade TEXT NOT NULL,
    logradouro TEXT NOT NULL,
    numero VARCHAR(55),
    complemento TEXT,
    bairro TEXT,
    cidade TEXT NOT NULL,
    uf CHAR(2) NOT NULL,
    cep VARCHAR(8),
    ddd VARCHAR(2),
    telefone VARCHAR(55),
    fax VARCHAR(55),
    endereco_eletronico TEXT,
    representante TEXT,
    cargo_representante TEXT,
    regiao_de_comercializacao INT,
    data_registro_ans DATE NOT NULL
);

COPY temp_demonstracoes(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final)
FROM 'C:\\Users\\Pichau\\Documents\\intuitivecare\\desafio_tres_banco_de_dados\\1T2023.csv'
DELIMITER ';'
CSV HEADER
ENCODING 'UTF8';

COPY temp_demonstracoes(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final)
FROM 'C:\\Users\\Pichau\\Documents\\intuitivecare\\desafio_tres_banco_de_dados\\2t2023.csv'
DELIMITER ';'
CSV HEADER
ENCODING 'UTF8';

COPY temp_demonstracoes(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final)
FROM 'C:\\Users\\Pichau\\Documents\\intuitivecare\\desafio_tres_banco_de_dados\\3T2023.csv'
DELIMITER ';'
CSV HEADER
ENCODING 'UTF8';

COPY temp_demonstracoes(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final)
FROM 'C:\\Users\\Pichau\\Documents\\intuitivecare\\desafio_tres_banco_de_dados\\4T2023.csv'
DELIMITER ';'
CSV HEADER
ENCODING 'UTF8';

COPY temp_demonstracoes(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final)
FROM 'C:\\Users\\Pichau\\Documents\\intuitivecare\\desafio_tres_banco_de_dados\\1T2024.csv'
DELIMITER ';'
CSV HEADER
ENCODING 'UTF8';

COPY temp_demonstracoes(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final)
FROM 'C:\\Users\\Pichau\\Documents\\intuitivecare\\desafio_tres_banco_de_dados\\2T2024.csv'
DELIMITER ';'
CSV HEADER
ENCODING 'UTF8';

COPY temp_demonstracoes(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final)
FROM 'C:\\Users\\Pichau\\Documents\\intuitivecare\\desafio_tres_banco_de_dados\\3T2024.csv'
DELIMITER ';'
CSV HEADER
ENCODING 'UTF8';

COPY temp_demonstracoes(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final)
FROM 'C:\\Users\\Pichau\\Documents\\intuitivecare\\desafio_tres_banco_de_dados\\4T2024.csv'
DELIMITER ';'
CSV HEADER
ENCODING 'UTF8';

INSERT INTO demonstracoes_contabeis (data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final)
SELECT 
    CASE
        WHEN data LIKE '%/%/%' THEN TO_DATE(data, 'DD/MM/YYYY')  -- Caso o formato seja DD/MM/YYYY
        ELSE TO_DATE(data, 'YYYY-MM-DD')  -- Caso o formato seja YYYY-MM-DD
    END,
    reg_ans::VARCHAR(10),
    cd_conta_contabil::TEXT,
    descricao,
    REPLACE(vl_saldo_inicial, ',', '.')::NUMERIC,
    REPLACE(vl_saldo_final, ',', '.')::NUMERIC
FROM temp_demonstracoes;

COPY temp_operadoras (
    registro_ans, cnpj, razao_social, nome_fantasia, modalidade, logradouro, numero, 
    complemento, bairro, cidade, uf, cep, ddd, telefone, fax, endereco_eletronico, 
    representante, cargo_representante, regiao_de_comercializacao, data_registro_ans
)
FROM 'C:\\Users\\Pichau\\Documents\\intuitivecare\\desafio_tres_banco_de_dados\\Relatorio_cadop.csv'
DELIMITER ';'
CSV HEADER
ENCODING 'UTF8';

INSERT INTO operadoras_de_plano_de_saude_ativas (
    registro_ans, cnpj, razao_social, nome_fantasia, modalidade, logradouro, numero, 
    complemento, bairro, cidade, uf, cep, ddd, telefone, fax, endereco_eletronico, 
    representante, cargo_representante, regiao_de_comercializacao, data_registro_ans
)
SELECT 
    registro_ans,
    cnpj,
    razao_social,
    nome_fantasia,
    modalidade,
    logradouro,
    numero,
    complemento,
    bairro,
    cidade,
    uf,
    cep,
    ddd,
    telefone,
    fax,
    endereco_eletronico,
    representante,
    cargo_representante,
    regiao_de_comercializacao,
    TO_DATE(data_registro_ans, 'YYYY-MM-DD')  
FROM temp_operadoras;



