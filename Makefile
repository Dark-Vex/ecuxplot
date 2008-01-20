MP_SOURCES=HexValue.java Map.java Parser.java Parse.java \
	ParserException.java Project.java

SOURCES=$(addprefix MapPack/,$(MP_SOURCES))

CLASSES=$(SOURCES:%.java=%.class)
TARGET=mapdump.class
REFERENCE=data/4Z7907551R.kp

all: $(TARGET)
clean:
	rm *.class

%.csv: %.kp mapdump
	./mapdump -r $(REFERENCE) $< > $@

mapdump.class: mapdump.java $(CLASSES)

%.class: %.java
	javac -cp MapPack $<
