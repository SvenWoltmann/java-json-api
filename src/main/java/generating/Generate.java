import jdk.incubator.json.*;

record Jep(int number, String title) {}

void main() {
  List<Jep> jeps = List.of(
      new Jep(401, "Value Objects"),
      new Jep(540, "Simple JSON API"),
      new Jep(543, "Structured Concurrency"));

  JsonObject release = JsonObject.of(Map.of(
      "version", JsonNumber.of(28),
      "jeps", JsonArray.of(jeps.stream().map(this::toJson).toList())));

  IO.println(release);
  IO.println(Json.toDisplayString(release, "  "));
}

JsonObject toJson(Jep jep) {
  var members = new LinkedHashMap<String, JsonValue>();
  members.put("number", JsonNumber.of(jep.number()));
  members.put("title", JsonString.of(jep.title()));
  return JsonObject.of(members);
}
