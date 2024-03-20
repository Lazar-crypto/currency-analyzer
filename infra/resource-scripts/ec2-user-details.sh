#!/bin/bash
# Update YUM
yum update -y

# Install Docker
yum install -y docker

# Start Docker and enable it to start on boot
service docker start
chkconfig docker on

# Add the ec2-user to the docker group so you can execute Docker commands without using sudo
usermod -a -G docker ec2-user

# Install docker-compose
curl -L "https://github.com/docker/compose/releases/download/1.29.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose

sudo dnf install libxcrypt-compat

yum install -y aws-cli
aws ecr get-login-password --region eu-north-1 | docker login --username AWS --password-stdin 310105139694.dkr.ecr.eu-north-1.amazonaws.com
