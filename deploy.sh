#!/bin/bash
./gradlew clean build -x test
docker buildx build --platform linux/amd64 --load --tag jeongheumchoi/spot-server:0.0.1 .
docker push jeongheumchoi/spot-server:0.0.1

