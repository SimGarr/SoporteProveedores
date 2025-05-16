FROM eclipse-temurin:17-jre as compilado
WORKDIR /APP
COPY . .

CMD ["./mvnw", "clean", "package"]


FROM eclipse-temurin:17-jre as prod
WORKDIR /APP
COPY --from=compilado /APP/target/*.jar app.jar

CMD [ "java", "-jar", "app.jar" ]