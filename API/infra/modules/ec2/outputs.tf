output "private_ec2_01" {
  value = aws_instance.private_ec2_01.private_ip
}

output "private_ec2_02" {
  value = aws_instance.private_ec2_02.private_ip
}