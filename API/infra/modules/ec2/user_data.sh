#!/bin/bash
export DOCKERHUB_USERNAME=${dockerhub_username}

# Atualizar pacotes e instalar Java
sudo apt-get update
sudo apt-get install -y default-jdk

# Instalar Docker
sudo apt-get install -y docker.io

# Iniciar e habilitar Docker
sudo systemctl start docker
sudo systemctl enable docker

# Instalar Docker Compose
sudo apt-get install -y docker-compose

# Executar comandos Docker
sudo docker-compose -f /home/ubuntu/AWS/docker-compose.yml down
sudo docker pull ${DOCKERHUB_USERNAME}/foodway-api
sudo docker-compose -f /home/ubuntu/AWS/docker-compose.yml up -d
