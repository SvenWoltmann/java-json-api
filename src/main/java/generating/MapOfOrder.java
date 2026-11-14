import jdk.incubator.json.*;

// Map.of() has no defined iteration order: run this a few times
void main() {
  JsonObject release = JsonObject.of(Map.of(
      "version", JsonNumber.of(28),
      "ga", JsonString.of("2027-03-09")));
  IO.println(release);
}
