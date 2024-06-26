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
  default     = "ami-0cc52c2dfd2d4e0cc"
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

variable "snapshot_id" {
  default = "snap-0d20b4350b63ed2ee"
  description = "Snapshot ID Backend"
  type        = string
}
