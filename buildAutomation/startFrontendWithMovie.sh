docker container stop reel-rating-reverse-proxy
docker container remove reel-rating-reverse-proxy

docker container stop reel-rating-display-service
docker container remove reel-rating-display-service

docker container stop reel-rating-auth-service
docker container remove reel-rating-auth-service

docker container stop reel-rating-movie-data-service
docker container remove reel-rating-movie-data-service

docker container stop reel-rating-mongo-movie
docker container remove reel-rating-mongo-movie

cd ..

docker compose -f "docker-compose-frontend-with-movie.yaml" up -d --build