#!/usr/bin/env bash
# Runs every example program with the given JDK and writes the outputs to
# results/<build>/. Run `mvn dependency:copy-dependencies` first; the
# comparison programs need Jackson and Gson from target/dependency.
# Usage: scripts/run-examples.sh <path-to-jdk-28>
#
# Exits with 1 if a program fails. The programs that show exceptions catch
# them and print the message, so every program should end normally.
set -uo pipefail

JDK=${1:?usage: scripts/run-examples.sh <path-to-jdk-28>}
JAVA="$JDK/bin/java"
ROOT=$(cd "$(dirname "$0")/.." && pwd)
BUILD=$("$JAVA" -version 2>&1 | sed -n 's/.*(build \([^,)]*\).*/\1/p' | head -1)
OUT="$ROOT/results/$BUILD"
rm -rf "$OUT"
mkdir -p "$OUT"
echo "JDK build: $BUILD" > "$OUT/environment.txt"
"$JAVA" -version >> "$OUT/environment.txt" 2>&1
FAILED=()

run() { # <dir> <program> <output-name> [program arguments...]
  local dir=$1 program=$2 name=$3
  shift 3
  mkdir -p "$OUT/$dir"
  # Run from the repository root, where the programs find release.json
  if ! (cd "$ROOT" && "$JAVA" --add-modules jdk.incubator.json \
      -cp "target/dependency/*" "src/main/java/$dir/$program.java" "$@") \
      > "$OUT/$dir/$name.txt" 2>&1; then
    FAILED+=("$dir/$name")
  fi
}

run intro QuickStart QuickStart
run parsing ValueTypes ValueTypes
run navigating Navigate Navigate
for p in Conversions Numbers; do run converting $p $p; done
for p in MissingValues ChangingTypes; do run optional $p $p; done
for p in ParseErrors ValueErrors; do run errors $p $p; done
for p in Generate Modify; do run generating $p $p; done
for v in 26 27 28; do
  run threaddump ThreadStates "ThreadStates-jdk-$v" "threaddumps/jdk-$v.json"
done
for p in JdkJeps JacksonJeps GsonJeps JacksonBinding EdgeCases; do run comparison $p $p; done

if [[ ${#FAILED[@]} -gt 0 ]]; then
  printf 'Failed: %s\n' "${FAILED[@]}" >&2
  exit 1
fi
echo "All outputs in results/$BUILD"
