# Этап 1: Сборка проекта с помощью Maven и Java 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Копируем pom.xml и исходники
COPY pom.xml .
COPY src ./src
# Собираем jar-файл, пропуская тесты, чтобы сборка прошла быстрее
RUN mvn clean package -DskipTests

# Этап 2: Запуск приложения
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Копируем готовый jar-файл из первого этапа
COPY --from=build /app/target/*.jar app.jar
# Открываем порт (укажи тот, на котором работает твой Tomcat, обычно 8080)
EXPOSE 8080
# Команда для запуска
ENTRYPOINT ["java", "-jar", "app.jar"]