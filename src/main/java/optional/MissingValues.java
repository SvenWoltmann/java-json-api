import jdk.incubator.json.*;

void main() throws IOException {
  JsonValue release = Json.parse(Files.readString(Path.of("release.json")));

  // Optional members: tryGet()
  for (JsonValue jep : release.get("jeps").asList()) {
    boolean preview = jep.tryGet("preview").map(JsonValue::asBoolean).orElse(false);
    IO.println(jep.get("title").asString() + (preview ? " (Preview)" : ""));
  }

  // null values: tryValue()
  JsonValue successor = release.get("successor");
  String ga = successor.get("ga").tryValue().map(JsonValue::asString).orElse("not set");
  IO.println("Java " + successor.get("version").asInt() + ", GA: " + ga);

  // tryGet() finds a member whose value is null
  IO.println(successor.tryGet("ga"));
  IO.println(successor.tryGet("rampdown"));

  // Missing or null
  IO.println(successor.tryGet("ga").flatMap(JsonValue::tryValue));
}
