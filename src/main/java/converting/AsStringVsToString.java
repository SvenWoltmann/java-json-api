import jdk.incubator.json.*;

void main() throws IOException {
  JsonValue release = Json.parse(Files.readString(Path.of("release.json")));
  JsonValue ga = release.get("ga");

  IO.println(ga.asString());
  IO.println(ga.toString());
  IO.println(ga);
}
