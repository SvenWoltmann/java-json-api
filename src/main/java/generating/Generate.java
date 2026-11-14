import jdk.incubator.json.*;

record Jep(int number, String title) {}

void main() {
  List<Jep> jeps = List.of(
      new Jep(401, "Value Objects"),
      new Jep(540, "Simple JSON API"),
      new Jep(543, "Structured Concurrency"));

  var members = new LinkedHashMap<String, JsonValue>();
  members.put("version", JsonNumber.of(28));
  members.put("jeps", JsonArray.of(jeps.stream().map(this::toJson).toList()));
  JsonObject release = JsonObject.of(members);

  IO.println(release);
  IO.println(Json.toDisplayString(release, "  "));
}

JsonObject toJson(Jep jep) {
  var members = new LinkedHashMap<String, JsonValue>();
  members.put("number", JsonNumber.of(jep.number()));
  members.put("title", JsonString.of(jep.title()));
  return JsonObject.of(members);
}
