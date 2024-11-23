docker-compose down

# build backend image
docker build -t backend-sinaleasy:latest ./sinaleasy-back

# build frontend image
docker build -t frontend-sinaleasy:latest ./sinaleasy-front

# start environment - removendo caches e subindo os dois de uma vez
docker-compose up --build --force-recreate --remove-orphans