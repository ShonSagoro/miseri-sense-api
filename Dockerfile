FROM openjdk:17

ADD target/miseri-sense.jar miseri-sense.jar

COPY /etc/letsencrypt/live/miseri-services.serveirc.com/fullchain.pem /certificado.pem
COPY /etc/letsencrypt/live/miseri-services.serveirc.com/fullchain.pem /claveprivada.pem

ENTRYPOINT ["java", "-jar","miseri-sense.jar"]
