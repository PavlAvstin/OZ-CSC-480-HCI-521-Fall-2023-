FROM maven:3.8.3-openjdk-17
WORKDIR .

COPY . .

RUN mvn package
CMD ["mvn", "liberty:run"] 
