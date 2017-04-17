all:
	javac -d bin ./src/jogo/*.java

.PHONY: run
run: all
	java -classpath bin/ jogo.BancoImobiliarioCliente

.PHONY: clean
clean:
	rm ./bin/jogo/*.class
	rm estatistica.txt