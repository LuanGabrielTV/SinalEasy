# Build do Angular
FROM node:23.3-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install 
COPY . .
RUN npm run build:production

# Servindo os arquivos com Nginx
FROM nginx:stable-alpine
<<<<<<< HEAD
=======
COPY nginx.conf /etc/nginx/nginx.conf
>>>>>>> 35e5d62e7e204e86c1aca03ada4fa7669466992a
COPY --from=build /app/dist/sinaleasy-front/browser /usr/share/nginx/html
# Porta 80 eh para o ngix (prod). Em dev, o ng deve ser utilizado, que eh lido na porta 4200
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
