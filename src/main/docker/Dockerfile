####
# Before building the docker image run:
#
# mvn package -Pnative -Dnative-image.docker-build=true
#
# Then, build the image with:
#
# docker build -f src/main/docker/Dockerfile -t quarkus/getting-started .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 quarkus/getting-started
#
###
FROM quay.io/quarkus/ubi-quarkus-native-image:19.1.1 as nativebuilder
RUN mkdir -p /tmp/ssl-libs \
  && cp /opt/graalvm/jre/lib/security/cacerts /tmp/ssl-libs \
  && cp /opt/graalvm/jre/lib/amd64/libsunec.so /tmp/ssl-libs

FROM registry.access.redhat.com/ubi8/ubi-minimal
WORKDIR /work/
COPY *-runner /work/application
COPY --from=nativebuilder /tmp/ssl-libs /work
RUN chmod 775 /work
RUN chmod a+x /work/application
EXPOSE 8080
CMD ["./application", "-Dquarkus.http.host=0.0.0.0", "-Djavax.net.ssl.trustStore=/work/cacerts"]
