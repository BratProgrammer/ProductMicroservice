# Первый этап: сборка приложения
FROM maven AS build

# Установите рабочую директорию
WORKDIR /app

# Копируйте файлы Maven
COPY pom.xml ./
COPY src ./src

# Сборка проекта
RUN mvn clean package -DskipTests

# Второй этап: создание минимального образа для запуска
FROM openjdk:21-jdk-slim

# Копируем собранный JAR-файл из первого этапа
COPY --from=build /app/target/*.jar /app/

# Открываем порт, который будет использовать приложение
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app/products_service.jar"]