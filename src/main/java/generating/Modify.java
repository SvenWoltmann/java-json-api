import jdk.incubator.json.*;

void main() throws IOException {
  JsonValue release = Json.parse(Files.readString(Path.of("release.json")));

  // JsonValue is immutable: copy the members, add one, build a new object
  var members = new LinkedHashMap<>(release.asMap());
  members.remove("jeps");
  members.put("featureFreeze", JsonString.of("2026-12-03"));
  IO.println(JsonObject.of(members));

  // No equals() contract: compare Java values instead
  JsonValue a = Json.parse("{ \"version\": 28 }");
  JsonValue b = Json.parse("{ \"version\": 28 }");
  IO.println(a.equals(b));
  IO.println(a.get("version").asInt() == b.get("version").asInt());
}
