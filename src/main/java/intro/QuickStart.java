import jdk.incubator.json.*;

void main() throws IOException {
  JsonValue release = Json.parse(Files.readString(Path.of("release.json")));

  int version = release.get("version").asInt();
  String ga = release.get("ga").asString();
  String firstJep = release.get("jeps").get(0).get("title").asString();

  IO.println("Java " + version + " (GA " + ga + "), first JEP: " + firstJep);
}
