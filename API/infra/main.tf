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
  dockerhub_username = var.dockerhub_username
}