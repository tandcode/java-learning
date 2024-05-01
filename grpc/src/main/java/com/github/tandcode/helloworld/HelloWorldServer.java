/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.tandcode.helloworld;

import com.github.tandcode.HelloReply;
import com.github.tandcode.HelloRequest;
import com.github.tandcode.MyGreeterGrpc;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static com.github.tandcode.helloworld.Args.parseArguments;

@Slf4j
@RequiredArgsConstructor
public class HelloWorldServer {

  private Server server;
  private final int port;

  @SneakyThrows
  public static void main(String[] args) {
    Args arguments = parseArguments(args);

    new HelloWorldServer(arguments.port())
      .start();
  }

  @SneakyThrows
  private void start() {
    server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
        .addService(new MyGreeterImpl())
        .build()
        .start();
    log.info("Server started, listening on " + port);
    server.awaitTermination();
  }

  static class MyGreeterImpl extends MyGreeterGrpc.MyGreeterImplBase {

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
      HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName() + " stranger!").build();
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
    }

  }
}
