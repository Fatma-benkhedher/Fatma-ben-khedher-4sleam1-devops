# Stage 1: Build de l'application avec Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Installation de git
RUN apt-get update && apt-get install -y git && apt-get clean

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier pom.xml et télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copier tout le code source
COPY . .

# Compiler et packager l'application (sans exécuter les tests)
RUN mvn clean package -DskipTests


# Stage 2: Image finale légère avec seulement le JAR
FROM eclipse-temurin:17-jdk-alpine

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR depuis le stage de build
COPY --from=build /app/target/*.jar app.jar

# Exposer le port de l'application
EXPOSE 8080

# Commande pour lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
