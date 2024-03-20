## Overview
Currency Analyzer allows users to check prices for different crypto coins and generate reports by calculating the standard deviation of selected coins.

<img width="604" alt="image" src="https://github.com/Lazar-crypto/currency-analyzer/assets/56827343/6b6b74bb-f80c-4fb9-b7b6-d1ba8baaf423">

### Deployment Requirements
Before deployment, ensure the following requirements are met:

1. **AWS CLI**: Ensure AWS CLI is installed.
2. **ECR with Docker**: Must be logged in to Amazon ECR with Docker to push images.
3. **EC2 Instance**: A simple EC2 instance should be created. User details can be found in: `./infra/resource-scripts/ec2-user-details.sh`.

### Deployment Instructions
Once the above requirements are met, deployment can be initiated by running the following script: `./infra/deployment-scripts/backend-deployment.sh`
This script will utilize Docker's multi-stage build to package the project with Maven and create an executable JAR file. Then, it will push the image to Amazon ECR.
SSH into ec2 and copy docker-compose file along with your .env file to spin up backend server.

### Environemnt variables which need to be set are:
REPOSITORY_URI=?
IMAGE_TAG=?
REGION=?

DB_HOST=?
DB_PORT=?
DB_PASS=?
DB_USER=?
DB_SCHEMA=?
