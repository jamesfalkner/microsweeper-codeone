Red Hat Microsweeper demo
=========================

This demo uses a number of cloud technologies to implement a simple game from the earlier days of computing: Minesweeper!

![Service Catalog](docs/microsweeper.png)

Technologies include:

* JQuery-based Minesweeper written by [Nick Arocho](http://www.nickarocho.com/) and [available on GitHub](https://github.com/nickarocho/minesweeper).
* Backend baed on [MicroProfile](https://microprofile.io) and [Quarkus](https://quarkus.io) to persist scoreboard
* Deployed using [Red Hat OpenShift](https://openshift.com) and the [Open Service Broker](https://www.openservicebrokerapi.org/) to deploy [Cosmos DB](https://azure.microsoft.com/en-us/services/cosmos-db/) on [Microsoft Azure](https://azure.microsoft.com/).

This demo can be deployed to any Kubernetes or OpenShift cluster.

To run demo locally (requires `docker`):
-----------
```
# run mongodb
docker run -ti --rm -p 27017:27017 mongo:4.0

# run this a different terminal
mvn clean compile quarkus:dev

# access at http://localhost:8080
```

To run demo on Azure
-----------
These steps are necessarily high level and encompass many smaller steps that are documented in detail in [this blog post](https://developers.redhat.com/blog/2018/10/17/microprofile-apps-azure-open-service-broker/).

* Get an [Azure Account](https://azure.microsoft.com/en-us/free/) (e.g. using an Azure Pass)
* Head over to [OpenShift 4.x Install](https://cloud.redhat.com/openshift/install) to deploy OpenShift to Azure which takes about 20 minutes once you have an Azure account and configured it [see here for detailed install instructions for Azure](https://cloud.redhat.com/openshift/install/azure/installer-provisioned).
* Install the [Open Service Broker for Azure](https://github.com/Azure/open-service-broker-azure) version 1.8.0 or later
* Using the Service Broker, provision an instance of [CosmosDB](https://docs.microsoft.com/en-us/azure/cosmos-db/introduction) to a new OpenShift project using the Service Broker
* Deploy this sample app to that project via:
    ```
    # build native Quarkus image
    mvn clean package -Pnative -Dnative-image.docker-build=true

    # deploy image
    oc new-project microsweeper
    oc new-build --binary --name=microsweeper
    oc start-build microsweeper --from-dir target/openshift --follow
    oc new-app microsweeper
    oc expose svc/microsweeper
    oc set probe dc/microsweeper --liveness --readiness --get-url=http://:8080/health --initial-delay-seconds=10 --period-seconds=5 --timeout-seconds=5 --failure-threshold=5
    ```
* _Bind_ the CosmosDB Service Broker instance to the app, using environment variable option with a prefix of `COSMOSDB_`. This will cause environment variables to be available to the app, e.g. `COSMOSDB_uri`

Once deployed access the UI using the route exposed via OpenShift. Discover the route:

```
echo http://$(oc get route microsweeper -o=go-template --template='{{ .spec.host }}')
```
