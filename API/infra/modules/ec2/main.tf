# resource "aws_key_pair" "generated_key" {
#   key_name   = var.key_pair_name
#   public_key = file("${path.module}/tf_key.pem.pub")
# }

resource "aws_instance" "private_ec2_01" {
  ami               = var.ami
  availability_zone = var.az
  instance_type     = var.inst_type
  ebs_block_device {
    device_name = "/dev/sda1"
    volume_size = 8
    volume_type = "gp3"
  }
  key_name                    = "shh_key"
  subnet_id                   = var.subnet_id
  associate_public_ip_address = false
  vpc_security_group_ids      = [var.sg_id]
  tags = {
    Name = "private-ec2-01"
  }
  user_data = base64encode(<<-EOF
    #!/bin/bash
    exec > /var/log/user_data.log 2>&1
    set -x

    export DOCKERHUB_USERNAME=${var.dockerhub_username}

    # Atualizar pacotes e instalar Java
    sudo apt-get update
    sudo apt-get install -y default-jdk

    # Instalar Docker
    sudo apt-get install -y docker.io

    # Iniciar e habilitar Docker
    sudo systemctl start docker
    sudo systemctl enable docker

    # Executar comandos Docker
    sudo docker pull $DOCKERHUB_USERNAME/foodway-api
    sudo docker-compose -f /home/ubuntu/AWS/docker-compose.yml up -d
    EOF
  )
}

resource "aws_instance" "private_ec2_02" {
  ami               = var.ami
  availability_zone = var.az
  instance_type     = var.inst_type
  ebs_block_device {
    device_name = "/dev/sda1"
    volume_size = 8
    volume_type = "gp3"
  }
  key_name                    = "shh_key"
  subnet_id                   = var.subnet_id
  associate_public_ip_address = false
  vpc_security_group_ids      = [var.sg_id]
  tags = {
    Name = "private-ec2-02"
  }
  user_data = base64encode(<<-EOF
    #!/bin/bash
    exec > /var/log/user_data.log 2>&1
    set -x

    export DOCKERHUB_USERNAME=${var.dockerhub_username}

    # Atualizar pacotes e instalar Java
    sudo apt-get update
    sudo apt-get install -y default-jdk

    # Instalar Docker
    sudo apt-get install -y docker.io

    # Iniciar e habilitar Docker
    sudo systemctl start docker
    sudo systemctl enable docker

    # Executar comandos Docker
    sudo docker pull $DOCKERHUB_USERNAME/foodway-api
    sudo docker-compose -f /home/ubuntu/AWS/docker-compose.yml up -d
    EOF
  )
}