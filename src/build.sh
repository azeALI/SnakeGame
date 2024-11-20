#!/bin/sh
javac  *.java
mkdir build
jar -cfmv snakeGame.jar manifest.txt  *.class com ip.txt
rm *.class
mv snakeGame.jar build/
