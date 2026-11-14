import com.google.gson.JsonParser;
import jdk.incubator.json.Json;
import tools.jackson.databind.ObjectMapper;

// The same edge cases with the JDK API, Jackson 3 and Gson
void main() {
  var jackson = new ObjectMapper();
  List<String> documents = List.of(
      "{ \"version\": 28, \"version\": 29 }",
      "{\n  // Java 28\n  \"version\": 28\n}",
      "{ \"version\": 345.6 }",
      "{ \"version\": \"28\" }",
      "{ \"version\": 2147483648 }",
      "{ }");

  for (String document : documents) {
    IO.println(document.replace("\n", " ").replaceAll(" +", " "));
    IO.println("  JDK:     " + result(() -> Json.parse(document).get("version").asInt()));
    IO.println("  Jackson: " + result(() -> jackson.readTree(document).get("version").asInt()));
    IO.println("  Gson:    " + result(() -> JsonParser.parseString(document)
        .getAsJsonObject().get("version").getAsInt()));
  }
}

String result(Supplier<Object> read) {
  try {
    return String.valueOf(read.get());
  } catch (RuntimeException e) {
    return e.getClass().getSimpleName();
  }
}
