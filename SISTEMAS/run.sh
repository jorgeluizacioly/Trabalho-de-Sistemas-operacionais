#!/bin/bash

# Compila e executa o projeto
mvn clean compile exec:java

# Executa o programa
JAVAFX_PATH="$HOME/.m2/repository/org/openjfx"
JAVAFX_VERSION="21.0.2"
JAVAFX_PLATFORM="mac-aarch64"

java \
  --module-path "$JAVAFX_PATH/javafx-controls/$JAVAFX_VERSION/javafx-controls-$JAVAFX_VERSION-$JAVAFX_PLATFORM.jar:$JAVAFX_PATH/javafx-graphics/$JAVAFX_VERSION/javafx-graphics-$JAVAFX_VERSION-$JAVAFX_PLATFORM.jar:$JAVAFX_PATH/javafx-base/$JAVAFX_VERSION/javafx-base-$JAVAFX_VERSION-$JAVAFX_PLATFORM.jar:$JAVAFX_PATH/javafx-fxml/$JAVAFX_VERSION/javafx-fxml-$JAVAFX_VERSION-$JAVAFX_PLATFORM.jar" \
  --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base \
  --add-opens javafx.graphics/com.sun.glass.ui=ALL-UNNAMED \
  --add-opens javafx.graphics/com.sun.glass.ui.mac=ALL-UNNAMED \
  -cp target/process-scheduler-1.0-SNAPSHOT.jar \
  com.scheduler.ui.SchedulerApp 