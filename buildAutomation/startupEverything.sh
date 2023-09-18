docker container stop reel-rating-mongo-user-credentials
docker container remove reel-rating-mongo-user-credentials

docker container stop reel-rating-auth-service
docker container remove reel-rating-auth-service

docker container stop reel-rating-display-service
docker container remove reel-rating-display-service

cd ..

docker compose up -d --build