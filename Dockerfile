# Utiliser l'image Alpine officielle
FROM alpine:latest

# Installer Java 11
RUN apk add --no-cache openjdk11

# Définir JAVA_HOME
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk

# Vérification de la version Java
CMD ["java", "-version"]
