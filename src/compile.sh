#!/bin/sh
javac *.java
mkdir snake
mv *.class snake/
cp api.txt snake/
cp -r com snake/
