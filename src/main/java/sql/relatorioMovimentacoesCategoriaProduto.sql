SELECT m.IdMovimentacao,
       m.Tipo,
       m.Quantidade,
       m.Data,
       m.Valor,
       p.Nome AS Produto,
       c.Nome AS Categoria
FROM movimentacao m
JOIN produto p ON p.IdProduto = m.IdProduto
JOIN categoria c ON c.IdCategoria = p.IdCategoria;
