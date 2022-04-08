## Musify Project
This application consists of 3 microservices: three microservices are developed using Spring Boot in Java (usermanager, catalogmanager, paymanager).

## Local execution
```
docker-compose up
```  

## Kubernetes

Bind minikube ip
```
minikube ip
```
Add in /etc/hosts the bind musify - ip retrieved with the last command

Start minikube 
```
minikube start
eval $(minikube docker-env)
minikube addons enable ingress
```   
Apply YAML files
```  
kubectl apply -f k8s
kubectl create namespace monitoring
kubectl apply -f prometheus
```  
Images are uploaded in DockerHub
Using deploy files all images will be downloaded


Launch prometheus angrafana dashboard
```  
minikube service --namespace=monitoring grafana
minikube service --namespace=monitoring prometheus
```  
### Demo

```
# Add a user
curl musify/user/addUser -X POST -H "Content-Type: application/json" -d '{"email" : "ale2@gmail.com", "username":"ale2", "psw":"aaa"}'

# Add a CD
curl musify/catalog/addCD -X POST -H "Content-Type: application/json" -d '{"nameCD" : "Disumano", "authorCD":"Fedez", "nbavailableCD":"6", "priceCD":"18"}'

# Add a payment
curl musify/payment/addPayment/2/2/1 -X POST

# Get all payments
curl musify/payment/getPayment
