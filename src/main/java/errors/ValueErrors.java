import jdk.incubator.json.*;

void main() throws IOException {
  JsonValue release = Json.parse(Files.readString(Path.of("release.json")));

  tryToRead(() -> release.get("jeps").get(2).get("preview"));
  tryToRead(() -> release.get("jeps").get(3));
  tryToRead(() -> release.get("version").asString());
  tryToRead(() -> release.get("jeps").get("title"));
  tryToRead(() -> release.get("successor").get("ga").asString());
}

void tryToRead(Supplier<Object> access) {
  try {
    access.get();
  } catch (JsonValueException e) {
    IO.println(e.getMessage());
  }
}
