package com.github.tandcode.helloworld;

import com.github.tandcode.HelloReply;
import com.github.tandcode.HelloRequest;
import com.github.tandcode.MyGreeterGrpc;
import io.grpc.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static com.github.tandcode.helloworld.Args.parseArguments;

@Slf4j
public class HelloWorldClient {

  private final MyGreeterGrpc.MyGreeterBlockingStub blockingStub;

  public HelloWorldClient(Channel channel) {
    // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
    // shut it down.

    // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
    blockingStub = MyGreeterGrpc.newBlockingStub(channel);
  }

  public static void main(String[] args) throws Exception {
    Args arguments = parseArguments(args);
    int i = 0;
    //noinspection InfiniteLoopStatement
    while (true) {
      ping(arguments, i += 1);
      //noinspection BusyWait
      Thread.sleep(10000L);
    }
  }

  @SneakyThrows
  public void greet(String name, int i) {
    log.info("Will try to greet " + name + " for the " + number(i) + " time ...");
    HelloRequest request = HelloRequest.newBuilder().setName(name).build();

    HelloReply response = blockingStub.sayHello(request);

    log.info("Greeting: " + response.getMessage());
  }

  private String number(int i) {
    int tenth = i < 20 ? i : i % 10;
    String suff = switch (tenth) {
      case 1 -> "st";
      case 2 -> "nd";
      case 3 -> "rd";
      default -> "th";
    };
    return i + suff;
  }

  private static void ping(Args arguments, int i) throws InterruptedException {
    ManagedChannel channel = Grpc.newChannelBuilder(arguments.serverTarget(), InsecureChannelCredentials.create())
      .build();
    try {
      HelloWorldClient client = new HelloWorldClient(channel);
      client.greet(arguments.user(), i);
    } finally {
      channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
  }

}
