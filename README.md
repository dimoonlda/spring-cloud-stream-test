# spring-cloud-stream-test
spring-cloud-stream + sleuth + zipkin + kafka

Based on project from this article: https://dzone.com/articles/event-driven-microservices-using-spring-cloud-stre

Zipkin UI: http://127.0.0.1:9411
To start Kafka in Docker use this image: https://github.com/spotify/docker-kafka

I use this command to run: 
docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=`127.0.0.1` --env ADVERTISED_PORT=9092 spotify/kafka
