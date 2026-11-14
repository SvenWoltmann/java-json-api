import jdk.incubator.json.*;

void main() throws IOException {
  JsonValue release = Json.parse(Files.readString(Path.of("release.json")));
  JsonValue ga = release.get("ga");

  IO.println(ga.asString());
  IO.println(ga.toString());
  IO.println(ga);

  IO.println(release.get("lts").asBoolean());
  IO.println(release.get("version").asLong());
  IO.println(release.get("version").asDouble());

  Map<String, JsonValue> successor = release.get("successor").asMap();
  IO.println(successor.keySet());
}
