terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.16"
    }
  }
  required_version = ">= 1.2.0"
}

provider "aws" {
  region     = var.region
}

module "ec2" {
  source = "./modules/ec2"
}

output "private_ec2_01_private_ip" {
  value = module.ec2.private_ec2_01_private_ip
}

output "private_ec2_02_private_ip" {
  value = module.ec2.private_ec2_02_private_ip
}