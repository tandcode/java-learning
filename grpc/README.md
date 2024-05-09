# gRPC Hello World

## Project Overview

This project demonstrates the basic capabilities of gRPC by implementing a simple "Hello World" clint-server communication. The gRPC framework is used to define and implement a service that exchanges greeting messages between a client and a server.

## Prerequisites
- Java JDK 21 or higher
- Maven

## Getting Started

### Step 1: Clone the Repository

Start by cloning the repository to your local machine:

    git clone --depth 1 https://github.com/tandcode/java-learning.git
    cd grpc

### Step 2: Build the Project

Build the project with Maven:

    mvn clean package

This command compiles the project and installs the necessary dependencies.

### Step 3: Run the gRPC Service

Execute the `grpc-demo.sh` script to start the gRPC server and client:

    chmod +x grpc-demo.sh
    ./grpc-demo.sh

Note: alternatively you can start manually `HelloWorldServer` and `HelloWorldClient` in IDE.