# review-service-h2

to create microservices faster, you need a backbone. This boilerplate prepared to practise core microservice concepts like: 

- [x] cloud-native
    - [x] stateless on K8s
    - [ ] state-full on GCP PaaS (not on K8s)
- [x] REST maturity
    - [x] L2 
    - [x] L3 (HATEOS)
- [x] naming conventions
- [x] actuator (/health)
- [x] error-handling
- [x] logging
    - [x] general {Slf4j}
    - [x] message format standardization
    - [ ] distributed-tracing {sleuth}
    - [ ] monitoring
- [ ] api-management
    - [] message-converter (such as text2json and vice versa)
- [ ] security
- [x] profiles (dev, test, prod)
- [ ] logical boundaries (separating dbs)
- [x] caching
    - [x] L2 {JCache | Ehcache}
- [ ] rest-template {Feign}
- [ ] api-gateway {zuul}
- [ ] load-balancing {ribbon}
- [ ] fallbacks {hystrix}
- [ ] config-server {spring}
- [ ] service-discovery {eureka}
- [ ] event-sourcing
- [ ] CQRS (separate read and write queries)


### Technology stack

- Requirements (openjdk 11, docker, GKE)
- spring boot 2.3.x, gradle 6.3, Lombok 1.18, log4j 2.x, Ehcache 3.x


## Naming Conventions

- project name for a real service will take place below!
    - intellij top-level: review-service
    - package name: com.backbone.core
    - gradle's project name: review-service
        - jar name: build/libs/review-service-0.0.1-SNAPSHOT.jar
    - deployment name in k8s: review-service
    - url format: /review/1 or /reviews
    
### OOP Architecture

- Review ... as Entity
- ReviewRepository
    - ReviewService (as JPA cache layer or call APIs)
        - ReviewController (as REST APIs)

## How To Start

**on IDE**, 

1. `mkdir microservice-backbone-boilerplate && cd microservice-backbone-boilerplate` then
    - `git clone https://github.com/tansudasli/review-service-h2.git && cd review-service-h2`
2. to Run the application <br>
   * `./gradlew bootRun` to start,
   * `./gradlew bootJar` to create jar lib, and run w/ `java -jar build/libs/*.jar`
3. to test run `curl localhost:8080`
   * `curl localhost:8080/dummy` or `curl localhost:8080/dummy/name`
   * `curl localhost:8080/reviews` or `curl localhost:8080/reviews/10`
4. to access h2-db check `localhost:8080/h2`  w/ conn. `jdbc:h2:mem:reviews`

<br>**on Kubernetes**,

1. Create GKE cluster 
    - `mkdir microservice-backbone-boilerplate && cd microservice-backbone-boilerplate` then Follow the steps in `Readme.md`.
2. Prepare and deploy docker images to GCP
    - Edit `gradle.properties`
    - Run `./gradlew dockerTag`. Test w/ 
       - `docker images` then `docker run -d -p 8080:8080 IMAGE_NAME:TAG`
    - Run `./gradlew dockerPushGCP`
3. Deploy app. to GKE cluster.
    - Run `./deploy.sh` to deploy w/ .yaml files
    - to test, run `kubectl get services` and get EXTERNAL-IP then `curl EXTERNAL-IP:8080` to test.


## High Level Architecture - 

an abstract representation of a micro service

![Image](doc/microservice-highlevel-architecture.png)

a more realistic scenario w/ data pipelines

![Image](doc/microservice-architecture-in-detail.png)




