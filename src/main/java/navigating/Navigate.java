import jdk.incubator.json.*;

void main() throws IOException {
  JsonValue release = Json.parse(Files.readString(Path.of("release.json")));

  // Chaining: object member, array element, object member
  IO.println(release.get("jeps").get(2).get("title").asString());
  IO.println(release.get("successor").get("version").asInt());

  // Iterating over an array
  for (JsonValue jep : release.get("jeps").asList()) {
    IO.println("JEP " + jep.get("number").asInt() + ": " + jep.get("title").asString());
  }

  // Streams over an array
  List<Integer> numbers = release.get("jeps").asList().stream()
      .map(jep -> jep.get("number").asInt())
      .toList();
  IO.println(numbers);

  // Iterating over the members of an object
  release.get("successor").asMap()
      .forEach((name, value) -> IO.println(name + " = " + value));
}
