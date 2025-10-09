SELECT p.Nome AS produto,
       SUM(m.Valor) AS TotalMovimentado
FROM movimentacao m
JOIN produto p ON p.id_produto = m.id_produto
GROUP BY p.nome;
