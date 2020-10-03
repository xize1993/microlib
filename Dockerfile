FROM openjdk:11-jdk-slim

COPY jar /app/
WORKDIR /app
RUN chmod +x wait-for-it.sh

EXPOSE 8081

CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "microlib-0.1-all.jar"]