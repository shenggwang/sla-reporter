# A simple server to report SLA

The required Architecture Principles:
- Re-usable Architect.
- Scalable based on incoming load.
- Data security.
- Performance and cost in mind for database access.
- Design with failure in mind.

## Implementation

The application will contain only a single public API that is used to create subscriptions.
Subscription contains: 
- email
- firstName (Optional)
- gender (Optional)
- dateOfBirth
- a flag for consent
- the newsletter ID (corresponding to the campaign)

The application might:
- a thread pool to handle database connection.
- be able to send email: _javax.mail_.
- execute cronjob: _Quartz Scheduler_.

Components (details):
- Nginx: for HTTPS request control and blue-green deploying (by swapping the request to another internal application)
- Elasticsearch: for vertical scale, data availability and index-based search.
- Docker: for microservices.
- Kubernetes: for controlling Jenkins (CI CD).

## How to

Below shows two ways of running the project, either by microservices or an executable jar.

### Microservices

I used docker-compose to bring up the containers.

#### To start, execute this:
```shell
docker-compose up -d --no-deps --build
```

#### For no cache, execute this:
```shell
docker-compose build --no-cache
docker-compose up -d --force-recreate
```

#### Check all running dockers.**
```shell
docker ps -a
```

#### Check all local docker images.**
```shell
docekr images -a
```

#### Kill docker and rm image.**
```shell
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
```

#### Remove dangling.**
```shell
docker rmi $(docker images -q -f dangling=true)
```

#### Remove installed images.**
```shell
docker image rm server cassandra nginx
```

#### Remove all images.**
```shell
docker image prune -a
```

### Run with executable jar

The project uses maven as package management.
Go to folder server.

#### Quick compile

```shell
mvn install -DskipTests -T 1.5C
```

#### Clean compile

```shell
mvn clean install
```

#### Run as standalone

```shell
java -jar <compilled file>
```
