#!/bin/bash
# Vai para a pasta do script
cd "$(dirname "$0")"
echo "Iniciando Sistema de Estoque..."
java -jar "projetoEstoque-1.0.0-jar-with-dependencies.jar"
