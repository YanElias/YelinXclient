#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

find_java8() {
  for candidate in "${JAVA8_HOME:-}" "${JAVA_HOME:-}" /usr/lib/jvm/java-8-openjdk-amd64 /usr/lib/jvm/java-8-openjdk /usr/lib/jvm/jdk8 /opt/java/openjdk; do
    if [[ -n "$candidate" && -x "$candidate/bin/java" ]]; then
      if "$candidate/bin/java" -version 2>&1 | grep -q 'version "1\.8'; then
        echo "$candidate"
        return 0
      fi
    fi
  done
  return 1
}

if ! JAVA_HOME="$(find_java8)"; then
  echo "ERRO: Java 8 não encontrado."
  echo "Instale um JDK 8 ou execute com JAVA8_HOME=/caminho/do/jdk8."
  echo "O ForgeGradle 2.1 deste projeto não é compatível com Java 17/21/25."
  exit 1
fi
export JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"

GRADLE_VERSION="2.14.1"
CACHE_DIR="${XDG_CACHE_HOME:-$HOME/.cache}/yelinx"
GRADLE_HOME="$CACHE_DIR/gradle-$GRADLE_VERSION"
GRADLE_BIN="$GRADLE_HOME/bin/gradle"

if [[ ! -x "$GRADLE_BIN" ]]; then
  mkdir -p "$CACHE_DIR"
  ARCHIVE="$CACHE_DIR/gradle-$GRADLE_VERSION-bin.zip"
  echo "Baixando Gradle $GRADLE_VERSION (só na primeira execução)..."
  if command -v curl >/dev/null 2>&1; then
    curl -fL --retry 2 "https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip" -o "$ARCHIVE"
  else
    echo "ERRO: instale curl para baixar o Gradle."
    exit 1
  fi
  unzip -q -o "$ARCHIVE" -d "$CACHE_DIR"
fi

echo "Java usado:"
java -version
echo
echo "Preparando ambiente Forge 1.8.9..."
"$GRADLE_BIN" setupDecompWorkspace
echo
echo "Compilando YelinX Client..."
"$GRADLE_BIN" build
echo
echo "Build concluído. JARs disponíveis em:"
find "$ROOT/build/libs" -maxdepth 1 -type f -name '*.jar' -print
