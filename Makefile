OUTDIR = compiled
MAIN = WinAssistKodtest.kt
JAR = $(OUTDIR)/app.jar

all: run

$(JAR): $(MAIN)
	mkdir -p $(OUTDIR)
	kotlinc $(MAIN) -include-runtime -d $(JAR)


run: $(JAR)
	java -jar $(JAR)


clean:
	rm -rf $(OUTDIR)
