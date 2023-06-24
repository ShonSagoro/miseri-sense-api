FROM openjdk:17

ADD target/miseri-sense.jar miseri-sense.jar

ENTRYPOINT ["java", "-jar","miseri-sense.jar"]
