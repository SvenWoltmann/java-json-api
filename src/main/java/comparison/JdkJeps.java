import jdk.incubator.json.*;

void main() throws IOException {
  String json = Files.readString(Path.of("release.json"));
  JsonValue release = Json.parse(json);

  for (JsonValue jep : release.get("jeps").asList()) {
    boolean preview = jep.tryGet("preview").map(JsonValue::asBoolean).orElse(false);
    IO.println(jep.get("number").asInt() + " " + jep.get("title").asString()
        + (preview ? " (Preview)" : ""));
  }
}
