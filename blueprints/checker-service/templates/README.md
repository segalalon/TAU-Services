### Debugging

1. Run {{service.name}}Runner main function

2. From command line (change port):

 curl --insecure -s -X GET -H "Content-Type: application/json" -d '{"inputField1": 1,"inputField2":"TEST"}' http://localhost:81xx/v1/{{service.artifactId}}


### consul check
curl http://xxxxxxxxxxTEW:8500/v1/health/checks/{{service.artifactId}}

### northbound urls
https://odsgs-app-dev/{{service.artifactId}}/v1/{{service.artifactId}}


https://odsgs-app-dev/{{service.artifactId}}/v1/actuator/health

