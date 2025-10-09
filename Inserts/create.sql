-- Remove tabelas existentes (se houver)

DROP TABLE IF EXISTS movimentacoes CASCADE;
DROP TABLE IF EXISTS produtos CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;

CREATE TABLE IF NOT EXISTS categorias (
    id_categoria SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    descricao VARCHAR(150) NOT NULL DEFAULT 'none'
);

CREATE TABLE IF NOT EXISTS produtos (
    id_produto SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    marca VARCHAR(255) NOT NULL,
    preco NUMERIC(14,2) NOT NULL DEFAULT 0,
    id_categoria BIGINT NOT NULL REFERENCES categorias(id_categoria)
);

CREATE TABLE IF NOT EXISTS movimentacoes (
    id_movimentacoes SERIAL PRIMARY KEY,
    id_produto BIGINT NOT NULL REFERENCES produtos(id_produto),
    tipo VARCHAR(255) NOT NULL,
    quantidade BIGINT NOT NULL DEFAULT 0,
    data DATE NOT NULL,
    valor NUMERIC(14,2) NOT NULL
);
