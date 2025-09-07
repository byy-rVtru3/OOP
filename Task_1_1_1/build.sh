
javac -d build/classes/java/main src/main/java/ru/nsu/shadrina/HeapSort.java src/main/java/ru/nsu/shadrina/Main.java

echo "Main-Class: ru.nsu.shadrina.Main" > MANIFEST.MF
jar cfm build/libs/app.jar MANIFEST.MF -C build/classes/java/main .

java -jar build/libs/app.jar

