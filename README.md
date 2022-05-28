# Filter-Event Service

This project contains a Quarkus REST service for the **filter-events** of my **osmosis water-filter**. The micro-controller of the water-filter will send all flushing and filtering events as HTTP REST calls to the service. The events are stored in a PostgreSQL database.

Both the REST service and the PostgreSQL database are hosted on a **k3d Kubernetes cluster** which runs on my **Raspberry Pi4**. The k3s cluster comes with its own container registry.

## Used Quarkus Extensions

* RestEasy Reactive
* Hibernate Reactive with Panache
* Reactive Postgres Client
* Docker Container Image

## Build & Deploy to raspberry-pi

```sh
# Java Build, Docker Build & Push to k3d registry
mvn package

# Deploy PostgreSQL database on Kubernetes
kubectl apply -f k8s-database.yaml

# Deploy Quarkus service on Kubernetes
kubectl apply -f k8s-service.yaml
```

## Test

Send the HTTP requests located in the file `src/test/resources/requests.http` using VSCode extension REST Client.

## k3d setup

1. Software Prerequisites

    * Ubuntu 20.04 LTS
    * Docker <https://omar2cloud.github.io/rasp/rpidock/>
    * Patch the `/boot/firmware/cmdline.txt` and add `cgroup_enable=cpuset cgroup_enable=memory cgroup_memory=1` to the end of the file
    * k3d <https://k3d.io/>
    * kubectl - <https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/>

1. Setup a new k3d cluster on Raspberry Pi 4

    ```sh
    # set some variables
    raspberry_ip=192.168.178.41
    cluster_name=pi4-cluster
    registry_name=pi4-registry

    # create the directory on the host where we will persist the data
    mkdir -p /tmp/k3dvol

    # create a new k3d cluster
    k3d cluster create $cluster_name --volume /tmp/k3dvol:/tmp/k3dvol --api-port $raspberry_ip:6550 -p "8081:80@loadbalancer" --agents 2 --registry-create $registry_name:0.0.0.0:5000

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

## Further Links

* Great k3d tutorial: <https://ian-says.com/articles/k3d-k8s-kubernetes/>

## TODOs

* Mount Raspberry Pi volume into k3d to keep DB persistence on cluster restart
