import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

void main() throws IOException {
  JsonNode release = new ObjectMapper().readTree(Files.readString(Path.of("release.json")));

  for (JsonNode jep : release.get("jeps")) {
    boolean preview = jep.path("preview").asBoolean(false);
    IO.println(jep.get("number").asInt() + " " + jep.get("title").asString()
        + (preview ? " (Preview)" : ""));
  }
}
