import tools.jackson.databind.ObjectMapper;

record Jep(int number, String title, boolean preview, boolean incubator) {}
record Successor(int version, String ga) {}
record Release(int version, String ga, boolean lts, List<Jep> jeps, Successor successor) {}

void main() throws IOException {
  Release release = new ObjectMapper()
      .readValue(Files.readString(Path.of("release.json")), Release.class);

  IO.println(release.version() + ", GA " + release.ga());
  release.jeps().forEach(IO::println);
  IO.println(release.successor());
}
