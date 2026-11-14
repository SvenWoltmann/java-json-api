import jdk.incubator.json.*;

void main() {
  // Thread IDs in JSON thread dumps: a string up to JDK 26, a number since JDK 27
  JsonValue jdk26 = Json.parse("""
      { "tid": "3", "name": "main" }
      """);
  JsonValue jdk28 = Json.parse("""
      { "tid": 3, "name": "main" }
      """);

  IO.println(threadId(jdk26));
  IO.println(threadId(jdk28));
}

long threadId(JsonValue thread) {
  return switch (thread.get("tid")) {
    case JsonNumber number -> number.asLong();
    case JsonString string -> Long.parseLong(string.asString());
    default -> throw new JsonValueException("Unexpected type for \"tid\"");
  };
}
