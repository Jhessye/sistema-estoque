SELECT m.id_movimentacao,
       m.tipo,
       m.quantidade,
       m.data,
       m.valor,
       p.nome AS Produto,
       c.nome AS Categoria
FROM movimentacao m
JOIN produto p ON p.id_produto = m.id_produto
JOIN categoria c ON c.id_categoria = p.id_categoria;
