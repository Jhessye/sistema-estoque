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

-- Inserir dados nas tabelas
INSERT INTO categorias (id_categoria, nome, descricao) VALUES
(1, 'Som e Tecnologia', 'Produtos voltados para áudio e conectividade do carro'),
(2, 'Interior e Conforto', 'Acessórios para melhorar o conforto interno'),
(3, 'Iluminação', 'Lâmpadas e itens de iluminação automotiva'),
(4, 'Acessórios', 'Itens complementares para o carro'),
(5, 'Segurança e Assistência', 'Equipamentos para segurança e auxílio ao motorista'),
(6, 'Manutenção', 'Produtos para manutenção do veículo');

INSERT INTO produtos (id_produto, nome, descricao, marca, preco, id_categoria) VALUES
(1, 'Rádio Digital', 'Rádio com adaptador USB e Bluetooth para carros Ford', 'Radiomix', 75.80, 1),
(2, 'Tapete Automotivo Antiderrapante', 'Conjunto de tapetes antiderrapantes em PVC, resistente à água, ideal para carros Chevrolet', 'AutoLux', 129.90, 2),
(3, 'Kit de Lâmpadas LED H7', 'Lâmpadas LED de alta luminosidade, baixo consumo e fácil instalação para carros Volkswagen', 'BrightCar', 89.50, 3),
(4, 'Capa de Volante em Couro', 'Capa de volante em couro sintético, antiderrapante, design esportivo para carros Honda', 'GripMax', 55.00, 2),
(5, 'Suporte Magnético para Celular', 'Suporte magnético de painel, rotação 360º, compatível com todas as marcas de carros', 'HoldFast', 39.90, 4),
(6, 'Sensor de Estacionamento Traseiro', 'Kit de sensores com aviso sonoro e display digital para carros Toyota', 'SafePark', 210.00, 5),
(7, 'Óleo de Motor 5W30 Sintético 1L', 'Óleo sintético de alta performance, recomendado para carros Hyundai', 'LubriTech', 46.70, 6);

INSERT INTO movimentacoes (id_movimentacoes, id_produto, tipo, quantidade, data, valor) VALUES

-- Entradas
(1, 1, 'entrada', 10, '2025-08-01', 758.00),
(2, 2, 'entrada', 5, '2025-08-03', 649.50),
(3, 3, 'entrada', 8, '2025-08-05', 716.00),
(4, 4, 'entrada', 12, '2025-08-07', 660.00),
(5, 5, 'entrada', 15, '2025-08-10', 598.50),
(6, 6, 'entrada', 3, '2025-08-14', 630.00),
(7, 7, 'entrada', 20, '2025-08-17', 934.00),

-- Saídas
(8, 1, 'saida', -2, '2025-08-18', -151.60),
(9, 2, 'saida', -1, '2025-08-19', -129.90),
(10, 3, 'saida', -3, '2025-08-19', -268.50),
(11, 4, 'saida', -5, '2025-08-20', -275.00),
(12, 5, 'saida', -2, '2025-08-21', -79.80),
(13, 6, 'saida', -1, '2025-08-21', -210.00),
(14, 7, 'saida', -4, '2025-08-22', -186.80);
