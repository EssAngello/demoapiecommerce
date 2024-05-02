ARG BASE_REPOSITORY=artifactory.demo.onebox.es/docker-shared/
ARG BASE_IMAGENAME=container-openjdk-onebox
ARG BASE_TAGVERSION=8u181-jdk-alpine
FROM ${BASE_REPOSITORY}${BASE_IMAGENAME}:${BASE_TAGVERSION}
LABEL authors="aange"

VOLUME /tmp
COPY target/onebox*.jar onebox-ecommerce.jar
ENTRYPOINT ["java","-jar","./onebox-ecommerce.jar.jar"]