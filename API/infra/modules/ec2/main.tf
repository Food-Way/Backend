resource "aws_key_pair" "generated_key" {
  key_name   = var.key_pair_name
  public_key = file("${path.module}/tf_key.pem.pub")
}

resource "aws_instance" "private_ec2_01" {
  ami                         = var.ami
  availability_zone           = var.az
  instance_type               = var.inst_type
  ebs_block_device {
    device_name = "/dev/sda1"
    volume_size = 30
    volume_type = "standard"
  }
  key_name                    = aws_key_pair.generated_key.key_name
  subnet_id                   = var.subnet_id
  associate_public_ip_address = false
  vpc_security_group_ids      = [var.sg_id]
  tags = {
    Name = "private-ec2-01"
  }
}

resource "aws_instance" "private_ec2_02" {
  ami                         = var.ami
  availability_zone           = var.az
  instance_type               = var.inst_type
  ebs_block_device {
    device_name = "/dev/sda1"
    volume_size = 30
    volume_type = "standard"
  }
  key_name                    = aws_key_pair.generated_key.key_name
  subnet_id                   = var.subnet_id
  associate_public_ip_address = false
  vpc_security_group_ids      = [var.sg_id]
  tags = {
    Name = "private-ec2-02"
  }
}
