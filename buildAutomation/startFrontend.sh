docker container stop reel-rating-reverse-proxy
docker container remove reel-rating-reverse-proxy

docker container stop reel-rating-display-service
docker container remove reel-rating-display-service

cd ..

docker compose -f "docker-compose-frontend.yaml" up -d --build