#!/bin/bash

# Check if the first argument is "s" or "c"
if [ "$1" = "s" ]; then
    # If "s", run the Server.java class
    java -cp app.jar com.github.tandcode.helloworld.HelloWorldServer "${@:2}"
elif [ "$1" = "c" ]; then
    # If "c", run the Client.java class
    java -cp app.jar com.github.tandcode.helloworld.HelloWorldClient "${@:2}"
else
    # If neither "s" nor "c" provided, print usage instructions
    echo "Usage: docker run <image_name> <s/c> <args>"
    echo "s: Run Server.java"
    echo "c: Run Client.java"
    exit 1
fi
