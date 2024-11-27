FROM amazoncorretto:21

WORKDIR ./app

ARG name

ENV JAR_NAME "${name}.jar"

COPY ./build/libs/${JAR_NAME} ./app/${JAR_NAME}

ENTRYPOINT ["java", "-jar", "*.jar"]