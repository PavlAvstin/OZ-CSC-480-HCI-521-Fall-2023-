docker container stop reel-rating-mongo-user-credentials
docker container remove reel-rating-mongo-user-credentials

docker container stop reel-rating-auth-service
docker container remove reel-rating-auth-service

docker container stop reel-rating-display-service
docker container remove reel-rating-display-service

docker container stop reel-rating-movie-data-service
docker container remove reel-rating-movie-data-service

docker container stop reel-rating-mongo-movie
docker container remove reel-rating-mongo-movie

docker container stop reel-rating-reverse-proxy
docker container remove reel-rating-reverse-proxy

docker container stop reel-rating-review-data-service
docker container remove reel-rating-review-data-service

docker container stop reel-rating-rating-data-service
docker container remove reel-rating-rating-data-service

docker container stop reel-rating-actor-data-service
docker container remove reel-rating-actor-data-service

docker container stop reel-rating-search-service
docker container remove reel-rating-search-service

cd ..

docker compose up -d --build