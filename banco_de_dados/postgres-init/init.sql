// Script de inicialização para MongoDB (estocar)

use estocar;

// limpar coleções existentes
db.categorias.drop();
db.produtos.drop();
db.movimentacoes.drop();

// CATEGORIASd
db.categorias.insertMany([
  { _id: 1, nome: "Som e Tecnologia", descricao: "Produtos de áudio e conectividade para automóveis" },
  { _id: 2, nome: "Interior e Conforto", descricao: "Acessórios voltados ao conforto interno do veículo" },
  { _id: 3, nome: "Iluminação", descricao: "Produtos de faróis, lâmpadas e LEDs automotivos" }
]);

// PRODUTOS
db.produtos.insertMany([
  { _id: 1, id_produto: 1, nome: "Rádio Automotivo Bluetooth", descricao: "Rádio com conexão Bluetooth e entrada USB", marca: "Radiomix", quantidade: 15, preco: 350.0, id_categoria: 1 },
  { _id: 2, id_produto: 2, nome: "Tapete Automotivo Antiderrapante", descricao: "Conjunto de tapetes de PVC resistente à água", marca: "AutoLux", quantidade: 20, preco: 120.0, id_categoria: 2 },
  { _id: 3, id_produto: 3, nome: "Lâmpada LED H7", descricao: "Lâmpada LED branca de alta luminosidade", marca: "BrightCar", quantidade: 30, preco: 85.5, id_categoria: 3 }
]);

// MOVIMENTAÇÕES
// Adicionei campo "quantidade" para representar o delta (número de unidades movimentadas)
db.movimentacoes.insertMany([
  { _id: 1, id_movimentacoes: 1, data: new Date("2025-08-01"), valor: 5250.00, id_produto: 1, quantidade: 15 },
  { _id: 2, id_movimentacoes: 2, data: new Date("2025-08-05"), valor: 2400.00, id_produto: 2, quantidade: 20 },
  { _id: 3, id_movimentacoes: 3, data: new Date("2025-08-10"), valor: 2565.00, id_produto: 3, quantidade: 30 }
]);

// Índices úteis
db.produtos.createIndex({ id_produto: 1 }, { unique: true });
db.categorias.createIndex({ _id: 1 });
db.movimentacoes.createIndex({ id_movimentacoes: 1 }, { unique: true });
db.movimentacoes.createIndex({ id_produto: 1 });