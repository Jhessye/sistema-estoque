SELECT 
    m.id_movimentacoes AS idMovimentacao,
    m.data,
    p.id_produto AS idProduto,
    p.nome AS produto,
    p.quantidade,
    p.preco AS valor,
    c.id_categoria AS idCategoria,
    c.nome AS categoria
FROM movimentacoes m
JOIN produto p ON p.id_produto = m.id_produto
JOIN categoria c ON c.id_categoria = p.id_categoria;
