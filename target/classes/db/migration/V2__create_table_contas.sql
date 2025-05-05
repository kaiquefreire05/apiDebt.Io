CREATE TABLE contas (
    id BIGINT PRIMARY KEY,
    cpf_usuario VARCHAR(11) NOT NULL,
    nome_compra VARCHAR(150) NOT NULL,
    valor NUMERIC(10,2) NOT NULL,
    tipo_pagamento VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    data_criacao TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP WITHOUT TIME ZONE,
    usuario_id UUID NOT NULL,

    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);