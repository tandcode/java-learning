#!/usr/bin/env bash

SERVER_PORT=50051
IMAGE_TAG=grpc-demo
docker image build -t $IMAGE_TAG -f grpc/grpc.Dockerfile .

kind delete cluster
kind create cluster --config=k8s/kind.yml
# load built image to cluster
kind load docker-image $IMAGE_TAG
# apply k8s infrastructure
kubectl apply -f k8s/grpc-server.yml

echo "wait for server cluster to start up"; sleep 20
kubectl apply -f k8s/grpc-clint.yml
