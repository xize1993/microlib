FROM gradle:6.6.1-jdk11

COPY --chown=gradle:gradle . /app/code/src
WORKDIR /app/code/src
USER gradle
RUN gradle build

COPY --from=build /app/code/src/build/libs/*-all.jar /app/microlib.jar

EXPOSE 8081

CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "/app/microlib.jar"]