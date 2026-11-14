import jdk.incubator.json.*;

void main() {
  tryToParse("""
      { "version": 28, }""");
  tryToParse("""
      { "version": 28, "version": 29 }""");
  tryToParse("""
      { 'version': 28 }""");
  tryToParse("""
      {
        // Java 28
        "version": 28
      }""");
}

void tryToParse(String json) {
  try {
    Json.parse(json);
  } catch (JsonParseException e) {
    IO.println(e.getMessage());
    IO.println("  line " + e.getErrorLine() + ", position " + e.getErrorPosition());
  }
}
