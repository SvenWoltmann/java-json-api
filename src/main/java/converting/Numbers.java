import jdk.incubator.json.*;

void main() {
  IO.println(Json.parse("123.0").asInt());
  IO.println(Json.parse("234.56E2").asInt());
  tryToConvert(() -> Json.parse("345.6").asInt());
  tryToConvert(() -> Json.parse("2147483648").asInt());
  IO.println(Json.parse("2147483648").asLong());

  IO.println(Json.parse("3.141592653589793238462643383279").asDouble());
  tryToConvert(() -> Json.parse("1.8E309").asDouble());

  JsonValue price = Json.parse("19.990");
  IO.println(price);
  IO.println(new BigDecimal(price.toString()));
}

void tryToConvert(Supplier<Object> conversion) {
  try {
    IO.println(conversion.get());
  } catch (JsonValueException e) {
    IO.println(e.getMessage());
  }
}
