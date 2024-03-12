FROM eclipse-temurin:21-jdk as build
COPY . /app
WORKDIR /app
RUN ./mvnw package -DskipTests
RUN mv -f target/*.jar app.jar

FROM eclipse-temurin:21-jre
ARG DW2_DB_URL
ARG DW2_USER
ARG DW2_PASS
ARG PORT
ENV PORT=${PORT}
ENV DW2_DB_URL=${DW2_DB_URL}
ENV DW2_USER=${DW2_USER}
ENV DW2_PASS=${DW2_PASS}
COPY --from=build /app/app.jar .
RUN useradd runtime
USER runtime
ENTRYPOINT [ "java", "-Dserver.port=${PORT}", "-jar", "app.jar" ]