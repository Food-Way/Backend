output "private_ec2_01_private_ip" {
  value = aws_instance.private_ec2_01.private_ip
}

output "private_ec2_02_private_ip" {
  value = aws_instance.private_ec2_02.private_ip
}