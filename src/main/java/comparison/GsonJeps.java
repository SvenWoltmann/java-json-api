import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

void main() throws IOException {
  JsonObject release = JsonParser.parseString(Files.readString(Path.of("release.json")))
      .getAsJsonObject();

  for (JsonElement element : release.getAsJsonArray("jeps")) {
    JsonObject jep = element.getAsJsonObject();
    boolean preview = jep.has("preview") && jep.get("preview").getAsBoolean();
    IO.println(jep.get("number").getAsInt() + " " + jep.get("title").getAsString()
        + (preview ? " (Preview)" : ""));
  }
}
