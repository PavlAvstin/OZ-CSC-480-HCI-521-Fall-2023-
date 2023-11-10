docker container stop reel-rating-reverse-proxy
docker container remove reel-rating-reverse-proxy

docker container stop reel-rating-auth-service
docker container remove reel-rating-auth-service

docker container stop reel-rating-movie-data-service
docker container remove reel-rating-movie-data-service

docker container stop reel-rating-mongo-movie
docker container remove reel-rating-mongo-movie

docker container stop reel-rating-search-service
docker container remove reel-rating-search-service

cd ..

docker compose -f "docker-compose-search.yaml" up -d --build