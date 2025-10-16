
DROP TABLE IF EXISTS movimentacoes CASCADE;
DROP TABLE IF EXISTS produtos CASCADE;
DROP TABLE IF EXISTS categorias CASCADE;


CREATE TABLE categorias (
    id_categoria SERIAL NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    descricao VARCHAR(150) NOT NULL
);

CREATE TABLE produtos (
    id_produto SERIAL NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    marca VARCHAR(255) NOT NULL,
    quantidade NUMERIC NOT NULL,
    preco NUMERIC(10,6) NOT NULL,
    id_categoria INT NOT NULL REFERENCES categorias(id_categoria)
);

CREATE TABLE movimentacoes (
    id_movimentacoes SERIAL NOT NULL PRIMARY KEY,
    data DATE NOT NULL,
    valor NUMERIC(15,5) NOT NULL,
    id_produto INT NOT NULL REFERENCES produtos(id_produto)
);
