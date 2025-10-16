
DROP TABLE IF EXISTS movimentacoes CASCADE;
DROP TABLE IF EXISTS produto CASCADE;
DROP TABLE IF EXISTS categoria CASCADE;


CREATE TABLE categoria (
    id_categoria SERIAL NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    descricao VARCHAR(150) NOT NULL
);

CREATE TABLE produto (
    id_produto SERIAL NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    marca VARCHAR(255) NOT NULL,
    quantidade NUMERIC NOT NULL,
    preco NUMERIC(10,6) NOT NULL,
    id_categoria INT NOT NULL REFERENCES categoria(id_categoria)
);

CREATE TABLE movimentacoes (
    id_movimentacoes SERIAL NOT NULL PRIMARY KEY,
    data DATE NOT NULL,
    valor NUMERIC(15,5) NOT NULL,
    id_produto INT NOT NULL REFERENCES produto(id_produto)
);

-- CATEGORIAS
INSERT INTO categoria (nome, descricao) VALUES
('Som e Tecnologia', 'Produtos de áudio e conectividade para automóveis'),
('Interior e Conforto', 'Acessórios voltados ao conforto interno do veículo'),
('Iluminação', 'Produtos de faróis, lâmpadas e LEDs automotivos');

-- PRODUTOS
INSERT INTO produto (nome, descricao, marca, quantidade, preco, id_categoria) VALUES
('Rádio Automotivo Bluetooth', 'Rádio com conexão Bluetooth e entrada USB', 'Radiomix', 15, 350.000000, 1),
('Tapete Automotivo Antiderrapante', 'Conjunto de tapetes de PVC resistente à água', 'AutoLux', 20, 120.000000, 2),
('Lâmpada LED H7', 'Lâmpada LED branca de alta luminosidade', 'BrightCar', 30, 85.500000, 3);

-- MOVIMENTAÇÕES
INSERT INTO movimentacoes (data, valor, id_produto) VALUES
('2025-08-01', 5250.00000, 1),
('2025-08-05', 2400.00000, 2),
('2025-08-10', 2565.00000, 3);