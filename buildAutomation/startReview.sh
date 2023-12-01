docker container stop reel-rating-reverse-proxy
docker container remove reel-rating-reverse-proxy

docker container stop reel-rating-auth-service
docker container remove reel-rating-auth-service

docker container stop reel-rating-review-data-service
docker container remove reel-rating-review-data-service

docker container stop reel-rating-mongo-movie
docker container remove reel-rating-mongo-movie

docker container stop reel-rating-movie-data-service
docker container remove reel-rating-movie-data-service

cd ..

docker compose -f "docker-compose-review.yaml" up -d --build