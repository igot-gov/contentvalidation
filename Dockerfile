FROM openjdk:8
COPY content-validation-service-0.0.1-SNAPSHOT.jar /opt/
EXPOSE 9050
CMD ["java", "-XX:+PrintFlagsFinal", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/opt/content-validation-service-0.0.1-SNAPSHOT.jar"]

