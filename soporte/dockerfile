FROM eclipse-temurin:17-jre AS compilado
WORKDIR /APP
COPY . .

CMD ["./mvnw", "clean", "package"]


FROM eclipse-temurin:17-jre AS prod
WORKDIR /APP
COPY --from=compilado /APP/target/*.jar app.jar

CMD [ "java", "-jar", "app.jar" ]