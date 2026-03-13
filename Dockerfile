# Этап сборки - используем образ от Oracle, там точно есть 25
FROM container-registry.oracle.com/java/openjdk:25 AS build
WORKDIR /app

# Копируем всё содержимое проекта
COPY . .

# Даем права и собираем
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Этап запуска
FROM container-registry.oracle.com/java/openjdk:25
WORKDIR /app

# Копируем jar из этапа сборки
COPY --from=build /app/target/*.jar app.jar

# Запускаем
ENTRYPOINT ["java", "-jar", "app.jar"]