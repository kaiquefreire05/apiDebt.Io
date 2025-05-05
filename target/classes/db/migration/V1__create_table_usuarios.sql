CREATE TABLE usuarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Gera UUID automático (precisa da extensão pgcrypto)
    nome VARCHAR(50) NOT NULL,
    sobrenome VARCHAR(150) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    telefone VARCHAR(15) NOT NULL,
    data_nascimento DATE NOT NULL,
    renda_mensal NUMERIC(10,2) NOT NULL,
    data_cadastro TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP WITHOUT TIME ZONE
);