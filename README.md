# Serverless Purple Cow API

This application currently is deployed as a Lambda function here: https://sg3f7c4n4m.execute-api.us-east-2.amazonaws.com/purple-cow

There are 2 endpoints for this application:
https://sg3f7c4n4m.execute-api.us-east-2.amazonaws.com/purple-cow/purpleCowTest
This endpoint is a basic HTTP Get Request to make sure our application is up and running.

https://sg3f7c4n4m.execute-api.us-east-2.amazonaws.com/purple-cow/validateCertDate
Using this endpoint, you will be able to test and see if the cert being used to hit this endpoint has expired or not.



## Local Build Pre-requisites
* [AWS CLI](https://aws.amazon.com/cli/)
* [SAM CLI](https://github.com/awslabs/aws-sam-cli)
* [Maven](https://maven.apache.org/)

## Deployment
This can be build locally from the app/ directory us maven.

```
$ mvn clean installl
```

Once the above command is successful you can use the SAM CLI to build a deployable package
```
$ sam build
```
The current deployment was built using the sam deploy --guided commmand, but this can also be built locally as well using the below command

```
$ sam local deploy
```
