#!/bin/sh
javac  *.java
mkdir build
jar -cfmv snakeGame.jar manifest.txt  *.class com api.txt
rm *.class
mv snakeGame.jar build/
