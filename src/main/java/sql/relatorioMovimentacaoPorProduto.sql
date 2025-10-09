SELECT p.Nome AS Produto,
       SUM(m.Valor) AS TotalMovimentado
FROM movimentacao m
JOIN produto p ON p.IdProduto = m.IdProduto
GROUP BY p.Nome;
