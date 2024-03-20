#!/bin/bash

source ../../.env

#build
docker build -t $IMAGE_TAG ../../backend/ca-back

#tag
docker tag $IMAGE_TAG:latest $REPOSITORY_URI:$IMAGE_TAG

#push
docker push $REPOSITORY_URI:$IMAGE_TAG

echo "Image $IMAGE_TAG sent to ECR."
