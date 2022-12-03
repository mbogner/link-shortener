# link-shortener

This app is a small and simple link shortener that requires an `X-Api-Key` header with a token in the `account` table.

So it is very easy to use. A very easy example how to shorten an url would be

```shell
curl --location --request POST 'http://localhost:8080/l' \
--header 'X-Api-Key: token1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "url": "http://test"
}'
```

The response will then look like this:

```json
{
  "url": "http://test",
  "code": "3ee1698"
}
```

To retrieve the link you can simply run

```shell
curl -i --location --request GET 'http://localhost:8080/l/32a8818'
# HTTP/1.1 308 Permanent Redirect
# Location: http://test
# content-length: 0
```

The body is empty but the answer is a permanent redirect to the shortened url.

If the code is unknown it will look like this:

```shell
curl -i --location --request GET 'http://localhost:8080/l/unknown'
# HTTP/1.1 404 Not Found
# content-length: 0
```

## Build

The application contains 2 different builds. One simply adds the bootJar to a docker container and the second one
compiles a native image and creates a very small container based on alpine linux to run the app.

The build uses the project name and version taken from gradle config to set the image tag. The
namespace `docker.io/mbopm/` can be configured by adapting `IMAGE_NS` in the scripts.

### openjdk:17-slim based image

You can run `./docker-build.sh` to create images like these:

```text
docker.io/mbopm/link-shortener  0.0.1-SNAPSHOT         81954c644dd7  3 hours ago     446 MB
docker.io/mbopm/link-shortener  latest                 81954c644dd7  3 hours ago     446 MB
```

The bootable jar file is created directly on your machine and copied into the container. You can start the app including
a database by running the `docker-compose-app.yml` file.

### alpine:latest native image

By running `docker-build-native.sh` a two phase build is started. First the application is copied into a container
with latest GraalVM and built using gradle.

The result from the build container is the native executable named `link-shortener`. This file is copied into an alpine
container. To make the executable compatible with alpine the package `gcompat` had to be installed which is a
compatibility layer for glibc.

You can start the app based on the native image including a database by running the `docker-compose-app-native.yml`
file.

This 2 phase build also helps to get around the issue that my local native executable is not compatible with the alpine
container (`exec format error`).