package com.github.tandcode.grpc.helloworld;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.cli.*;

@RequiredArgsConstructor
public class Args {

  private final CommandLine commandLine;

  @SneakyThrows
  public int port() {
    return Integer.parseInt(commandLine.getParsedOptionValue('p', "50051"));
  }

  @SneakyThrows
  public String user() {
    return commandLine.getParsedOptionValue('u', "world");
  }

  @SneakyThrows
  public String serverAddress() {
    return commandLine.getParsedOptionValue('s', "localhost");
  }

  @SneakyThrows
  public String serverTarget() {
    return serverAddress() + ":" + port();
  }

  @SneakyThrows
  public static Args parseArguments(String[] args) {
    Options opts = new Options();
    opts
      .addOption("p", "port", true, "Port of server")
      .addOption("u", "user", true, "User of request")
      .addOption("s", "server-address", true, "Ip address of server");

    CommandLineParser parser = new DefaultParser();
    CommandLine parsed = parser.parse(opts, args);

    return new Args(parsed);
  }
}
