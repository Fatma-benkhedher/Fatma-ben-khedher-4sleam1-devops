# Dockerfile pour projet statique
FROM nginx:alpine

# Copier tous les fichiers statiques dans le dossier web d'Nginx
COPY . /usr/share/nginx/html/

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
