# Filter-Event Service

This project contains a Quarkus REST service for the **filter-events** of my **osmosis water-filter**. The microcontroller of the water-filter will send all flushing and filtering events as HTTP REST calls to the service. The events are stored in a PostgreSQL database.

Both the REST service and the PostgreSQL database are hosted on a **k3d Kubernetes cluster** which runs on my **Raspberry Pi4**. The k3s cluster comes with its own container registry.

## Used Quarkus Extensions

* RestEasy Reactive
* Hibernate Reactive with Panache
* Reactive Postgres Client
* Docker Container Image

## Commands

```sh
# Java Build, Docker Build & Push to k3d registry
mvn package

# Deploy PostgreSQL database on kubernetes
kubectl apply -f k8s-database.yaml

# Deploy Quarkus service on kubernetes
kubectl apply -f k8s-service.yaml
```
