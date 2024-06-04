variable "region" {
  description = "The AWS region to deploy in"
  type        = string
  default     = "us-east-1"
}

variable "dockerhub_username" {
  description = "Docker Hub username"
  type        = string
}