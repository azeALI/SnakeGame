#!/bin/sh
mkdir snake
javac *.java
mv *.class snake/
cp -r com/ snake/
cp ip.txt snake/
cp manifest.txt snake/
