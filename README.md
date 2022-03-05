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

## k3d setup

1. Setup a new k3d cluster on Raspberry Pi 4

    ```sh
    # set some variables
    raspberry_ip=192.168.178.41
    cluster_name=pi4-cluster
    registry_name=pi4-registry

    # create a new k3d cluster
    k3d cluster create $cluster_name --api-port $raspberry_ip:6550 -p "8081:80@loadbalancer" --agents 2 --registry-create $registry_name:0.0.0.0:5000

    # get the kubeconfig - copy to the local machine
    k3d kubeconfig get -a
    ```

1. Create DNS entries for the local machine

    | Domain       | IP address     |
    | ------------ | -------------- |
    | pi4-registry | 192.168.178.41 |
    | ubuntu-pi4   | 192.168.178.41 |

1. Set docker insecure registry on the local machine

    ```sh
    "insecure-registries" : ["pi4-registry:5000"]
    ```
