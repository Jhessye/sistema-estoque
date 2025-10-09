SELECT p.nome AS Produto,
       SUM(m.valor) AS TotalMovimentado
FROM movimentacao m
JOIN produto p ON p.id_produto = m.id_produto
GROUP BY p.nome;
