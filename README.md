# 755_IIoT


## Commands to run

```
cd MainSystem
mvn package
java -cp target/MainSystem-1.0-SNAPSHOT-jar-with-dependencies.jar ProcessInput
# jar tvf target/MainSystem-1.0-SNAPSHOT-jar-with-dependencies.jar

cd ../GhostSystem
mvn package

# copy the DataProcessor.class from the ghost path in the DumpPatchedFile file

cd ../src

javac DumpPatchedFile.java
echo Agent-Class: DumpPatchedFile> manifest.mf
echo Can-Redefine-Classes: true>> manifest.mf
echo Permissions: all-permissions>> manifest.mf
jar cvfm patch.jar manifest.mf DumpPatchedFile.class
javac -classpath $JAVA_HOME/lib/tools.jar Attach.java
jps | grep 'ProcessInput' > tmp.txt

java -cp $JAVA_HOME/lib/tools.jar:. Attach tmp.txt /Users/raj.g/Documents/Courses/755SArch/patchability/755_IIoT/src/patch.jar

```


## Refs:

 - https://medium.com/pharos-production/apache-kafka-macos-installation-guide-a5a3754f09c
 - https://github.com/PharosProduction/tutorial-kafka-java/tree/master/src/main/java
 -  https://medium.com/@jhansireddy007/how-to-parallelise-kafka-consumers-59c8b0bbc37a
 - http://baeldung.com/java-instrumentation
 - http://www.fasterj.com/articles/hotpatch1.shtml
 
