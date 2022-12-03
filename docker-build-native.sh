#!/bin/bash
IMAGE_NS=docker.io/mbopm/

# switch to podman if exists
echo "#########################################################################"
echo "checking for podman"
CMD=docker

if which podman >>/dev/null 2>&1; then
  echo "using podman instead of docker"
  CMD=podman
  DOCKER_HOST=$(podman machine inspect | jq -r '.[0].ConnectionInfo.PodmanSocket.Path')
  export DOCKER_HOST
  echo "DOCKER_HOST=${DOCKER_HOST}"
fi
echo "CMD=${CMD}"

# get project name from project files
echo "#########################################################################"
echo "getting project information"
PROJECT_NAME=$(grep -e '^rootProject.name' settings.gradle.kts | sed 's/["\n\r]//g' | sed 's/rootProject.name.*=[ ]*//')
echo "PROJECT_NAME=${PROJECT_NAME}"
# get project version from project files
APP_VERSION=$(grep -e '^version=' gradle.properties | sed 's/version=//')
echo "APP_VERSION=${APP_VERSION}"

# build image
echo "#########################################################################"
echo "building image ${PROJECT_NAME}:${APP_VERSION}"
echo "linking image ${PROJECT_NAME}:latest to ${PROJECT_NAME}:${APP_VERSION}"
$CMD build \
  -f Dockerfile-native \
  -t ${IMAGE_NS}${PROJECT_NAME}:${APP_VERSION}-native \
  -t ${IMAGE_NS}${PROJECT_NAME}:latest-native \
  .

# display versions of project images
echo "#########################################################################"
echo "images for ${PROJECT_NAME}"
$CMD images | grep ${PROJECT_NAME}
