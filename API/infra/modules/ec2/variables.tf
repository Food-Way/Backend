variable "az" {
  description = "Availability Zone"
  type        = string
  default     = "us-east-1a"
}

variable "key_pair_name" {
  description = "Key Pair Name"
  type        = string
  default     = "tf_key"
}

variable "ami" {
  description = "AMI ID"
  type        = string
  default     = "ami-0e001c9271cf7f3b9"
}

variable "inst_type" {
  description = "Instance Type"
  type        = string
  default     = "t2.micro"
}

variable "subnet_id" {
  description = "Subnet ID"
  type        = string
  default     = "subnet-064ef516d7ad312b0"
}

variable "sg_id" {
  description = "Security Group ID"
  type        = string
  default     = "sg-0a69c3d9f7fc1fed6"
}

variable "dockerhub_username" {
  description = "Docker Hub username"
  type        = string
}

variable "var_snapshot_id" {
  default = "vol-002367949f74f27aa"
  description = "Snapshot ID Backend"
  type        = string
}
