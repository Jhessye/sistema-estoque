// Script de inicialização para MongoDB (db: estocar)
// Insere dados de exemplo e inicializa a coleção `counters` para as sequences

const db = db.getSiblingDB('estocar');

// Limpa coleções caso já existam (útil em reexecuções manuais)
db.categorias.drop();
db.produtos.drop();
db.movimentacoes.drop();
db.counters.drop();

// CATEGORIAS
db.categorias.insertMany([
  { id_categoria: 1, nome: 'Som e Tecnologia', descricao: 'Produtos de áudio e conectividade para automóveis' },
  { id_categoria: 2, nome: 'Interior e Conforto', descricao: 'Acessórios voltados ao conforto interno do veículo' },
  { id_categoria: 3, nome: 'Iluminação', descricao: 'Produtos de faróis, lâmpadas e LEDs automotivos' }
]);

// PRODUTOS
db.produtos.insertMany([
  { id_produto: 1, nome: 'Rádio Automotivo Bluetooth', descricao: 'Rádio com conexão Bluetooth e entrada USB', marca: 'Radiomix', quantidade: 15, preco: 350.0, id_categoria: 1 },
  { id_produto: 2, nome: 'Tapete Automotivo Antiderrapante', descricao: 'Conjunto de tapetes de PVC resistente à água', marca: 'AutoLux', quantidade: 20, preco: 120.0, id_categoria: 2 },
  { id_produto: 3, nome: 'Lâmpada LED H7', descricao: 'Lâmpada LED branca de alta luminosidade', marca: 'BrightCar', quantidade: 30, preco: 85.5, id_categoria: 3 }
]);

// MOVIMENTAÇÕES (nota: o campo `data` é armazenado como string, compatível com DAOs atuais)
db.movimentacoes.insertMany([
  { id_movimentacoes: 1, data: '2025-08-01', valor: 5250.0, id_produto: 1 },
  { id_movimentacoes: 2, data: '2025-08-05', valor: 2400.0, id_produto: 2 },
  { id_movimentacoes: 3, data: '2025-08-10', valor: 2565.0, id_produto: 3 }
]);

// Inicializa sequência (counters)
db.counters.insertMany([
  { _id: 'categorias', seq: 3 },
  { _id: 'produtos', seq: 3 },
  { _id: 'movimentacoes', seq: 3 }
]);

print('Init script executado: coleções populadas e counters inicializados.');
