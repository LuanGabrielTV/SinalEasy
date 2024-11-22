# Build do Angular
FROM node:23.3-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install 
COPY . .
RUN npm run build:production

# Servindo os arquivos com Nginx
FROM nginx:stable-alpine
COPY --from=build /app/dist/sinaleasy-front /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
