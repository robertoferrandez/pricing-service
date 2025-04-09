# Usa una imagen oficial de OpenJDK 17
FROM eclipse-temurin:17-jdk-alpine

# Crea un directorio para la app
WORKDIR /app

# Copia el JAR construido a la imagen (ajusta el nombre si es diferente)
COPY target/pricing-services-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto (ajústalo si tu app usa otro)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]