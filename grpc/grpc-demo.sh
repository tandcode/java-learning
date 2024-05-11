#!/bin/bash

JAR_FILES=(target/grpc*.jar)
JAR_NAME=${JAR_FILES[0]}
java -cp $JAR_NAME com.github.tandcode.grpc.helloworld.HelloWorldServer -p 50052 &
SERVER_PID=$!
sleep 3

java -cp $JAR_NAME com.github.tandcode.grpc.helloworld.HelloWorldClient -p 50052 -u SomeName &
CLIENT_PID=$!

cat << EOF

Press enter to terminate demo...

EOF
read -r
kill $CLIENT_PID
kill $SERVER_PID
