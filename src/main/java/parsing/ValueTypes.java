import jdk.incubator.json.*;

void main() throws IOException {
  JsonValue release = Json.parse(Files.readString(Path.of("release.json")));

  for (var member : release.asMap().entrySet()) {
    IO.println(member.getKey() + ": " + describe(member.getValue()));
  }
}

String describe(JsonValue value) {
  return switch (value) {
    case JsonObject object -> "object with " + object.asMap().size() + " members";
    case JsonArray array -> "array with " + array.asList().size() + " elements";
    case JsonString string -> "string " + string.asString();
    case JsonNumber number -> "number " + number.asInt();
    case JsonBoolean bool -> "boolean " + bool.asBoolean();
    case JsonNull _ -> "null";
  };
}
