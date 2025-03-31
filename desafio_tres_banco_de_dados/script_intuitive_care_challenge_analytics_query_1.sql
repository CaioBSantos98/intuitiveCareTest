SELECT 
	dc.reg_ans,
	op.razao_social, 
    	op.cnpj, 
    	op.cidade, 
    	op.uf, 
    	SUM(dc.vl_saldo_final - dc.vl_saldo_inicial) AS total_despesa
FROM 
	demonstracoes_contabeis dc
JOIN
	operadoras_de_plano_de_saude_ativas op
	ON dc.reg_ans = op.registro_ans
WHERE 
	dc.descricao ILIKE '%EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR %'
	AND dc.data BETWEEN '2024-10-01' AND '2024-12-31'
GROUP BY 
	dc.reg_ans, op.razao_social, op.cnpj, op.cidade, op.uf
ORDER BY 
	total_despesa DESC
LIMIT 10;