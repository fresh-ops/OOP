#!/bin/bash

javac -d bin src/main/java/ru/nsu/g/solovev5/m/task111/Main.java

javadoc -d doc -sourcepath src/main/java -subpackages ru.nsu.g.solovev5.m.task111 

jar -cmf manifest.mf heapsort.jar -C bin .

java -jar heapsort.jar
