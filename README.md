# alyssa

save your links to favorites web and easy way to remember how to go back it again
mvn package -DskipTests

scp target\Alyssa-0.0.1-SNAPSHOT.jar dopf:

start_alyssa.sh
#!/bin/sh
export spring_profiles_active=dopf
nohup java -jar Alyssa-0.0.1-SNAPSHOT.jar &

