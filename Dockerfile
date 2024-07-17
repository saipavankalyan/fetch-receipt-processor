FROM maven
RUN mkdir /app
WORKDIR /app
COPY . /app
RUN mvn clean install
CMD "mvn" "exec:java"