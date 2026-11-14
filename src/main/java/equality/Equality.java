import jdk.incubator.json.*;

void main() {
  // No equals() contract: compare Java values instead
  JsonValue a = Json.parse("{ \"version\": 28 }");
  JsonValue b = Json.parse("{ \"version\": 28 }");
  IO.println(a.equals(b));
  IO.println(a.get("version").asInt() == b.get("version").asInt());
}
