FROM adoptopenjdk/openjdk13-openj9:jdk-13.0.2_8_openj9-0.18.0-alpine-slim
COPY build/libs/touchbase-*-all.jar touchbase.jar
EXPOSE $PORT
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-XX:+IdleTuningGcOnIdle", "-Xtune:virtualized", "-Dmicronaut.environments=default,prod", "-jar", "touchbase.jar"]
