import jdk.incubator.json.*;

void main(String[] args) throws IOException {
  JsonValue dump = Json.parse(Files.readString(Path.of(args[0]))).get("threadDump");
  IO.println("Runtime " + dump.get("runtimeVersion").asString());

  Map<String, Integer> threadsPerState = new TreeMap<>();

  for (JsonValue container : dump.get("threadContainers").asList()) {
    List<JsonValue> threads = container.get("threads").asList();
    if (threads.isEmpty()) continue;

    String name = container.get("container").asString();
    String parent = container.get("parent").tryValue()
        .map(JsonValue::asString).orElse("none");
    IO.println(name + " (parent: " + parent + ")");

    for (JsonValue thread : threads) {
      String state = thread.get("state").asString();
      String waitingOn = thread.tryGet("waitingOn")
          .map(lock -> ", waiting on " + lock.asString()).orElse("");
      IO.println("  #" + threadId(thread) + " " + thread.get("name").asString()
          + ": " + state + waitingOn);
      threadsPerState.merge(state, 1, Integer::sum);
    }
  }

  IO.println(threadsPerState);
}

long threadId(JsonValue thread) {
  return switch (thread.get("tid")) {
    case JsonNumber number -> number.asLong();
    case JsonString string -> Long.parseLong(string.asString());
    default -> throw new JsonValueException("Unexpected type for \"tid\"");
  };
}
