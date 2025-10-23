SELECT 
    m.id_movimentacoes AS idMovimentacoes,
    m.data,
    p.id_produto AS idProduto,
    p.nome AS produtos,
    p.quantidade,
    p.preco AS valor,
    c.id_categoria AS idCategoria,
    c.nome AS categoria
FROM movimentacoes m
JOIN produtos p ON p.id_produto = m.id_produto
JOIN categorias c ON c.id_categoria = p.id_categoria;
