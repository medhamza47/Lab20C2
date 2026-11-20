# TP 20 – Architecture microservices avec RestTemplate

| Application | Port | Rôle |
| --- | ---: | --- |
| Eureka Server | 8761 | Registre des services |
| Client | 8081 | API REST et base MySQL `clientservicedb` |
| Gateway | 8888 | Routes dynamiques selon le registre Eureka |
| Voiture | 8082 | API REST et base MySQL `carservicedb` |

Le service Voiture lit la voiture dans sa base, puis utilise `RestTemplate` pour
appeler `/SERVICE-CLIENT/api/client/{id}` via la Gateway. La réponse contient
les détails du client. Si l'appel échoue, la voiture reste accessible avec
`client: null`.

## Démarrage

Prérequis : Java 17+, Maven et MySQL sur le port 3306. Les services utilisent
par défaut le compte local `root` avec un mot de passe vide, comme dans le TP.
Définir `DB_USER` et `DB_PASSWORD` pour utiliser un autre compte. Ce compte
doit avoir accès aux deux bases, que l'on peut créer au préalable si le
compte applicatif n'a pas le droit de les créer.

```bash
mvn clean package
```

Ouvrir quatre terminaux à la racine du projet et lancer une commande par
terminal, dans cet ordre :

```bash
java -jar eureka-server/target/eureka-server-1.0.0.jar
java -jar client-service/target/client-service-1.0.0.jar
java -jar gateway/target/gateway-1.0.0.jar
java -jar car-service/target/car-service-1.0.0.jar
```

Vérifier que `SERVICE-CLIENT`, `GATEWAY` et `SERVICE-CAR` sont visibles
sur <http://localhost:8761>. Attendre l'actualisation du registre côté Gateway
avant de tester les routes. En local, les applications déclarent leur adresse
127.0.0.1 à Eureka. Sur plusieurs machines, définir `EUREKA_INSTANCE_IP` pour
chaque application et adapter `EUREKA_URL` et `GATEWAY_URL`.
Les routes actives sont visibles à
`http://localhost:8888/actuator/gateway/routes`.

## Vérification

Après création des tables par Hibernate, charger les données de démonstration
dans les deux bases avec `mysql -u root -p < demo-data.sql`.

```bash
curl -i http://localhost:8888/SERVICE-CLIENT/api/client
curl -i http://localhost:8081/api/client/1
curl -i -H 'Content-Type: application/json' -d '{"nom":"Sara","age":24}' http://localhost:8081/api/client
curl -i http://localhost:8888/SERVICE-CAR/api/car
curl -i http://localhost:8082/api/car/1
curl -i http://localhost:8082/api/car/999999
```

Sur des bases neuves avec les données de démonstration, la voiture 1 contient
`"client":{"id":1,"name":"Amine","age":27.5}`. La création d'un client renvoie
`201 Created` et la consultation d'un identifiant absent renvoie `404 Not Found`.
