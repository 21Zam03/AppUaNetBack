FROM openjdk:17
ARG JAR_FILE=target/uanet-0.0.1.jar
COPY ${JAR_FILE} ua_net.jar
COPY uanet-83750-firebase-adminsdk-3w4p4-2b5cf44ec6.json uanet-83750-firebase-adminsdk-3w4p4-2b5cf44ec6.json
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/ua_net.jar"]