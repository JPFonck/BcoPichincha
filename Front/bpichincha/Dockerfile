# Etapa 1: Build
FROM node:20.19.0 AS build
WORKDIR /app
COPY . .
RUN npm install -g @angular/cli@17
RUN npm install
RUN ng build --configuration=production

# Etapa 2: NGINX
FROM nginx:alpine
COPY --from=build /app/dist/bpichincha/browser /usr/share/nginx/html
EXPOSE 80
