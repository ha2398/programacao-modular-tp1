package jogo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Scanner;

public class BancoImobiliarioCliente {
	private static BancoImobiliario novoJogo;
	
	public static final void main(String args[]) {
		novoJogo = new BancoImobiliario();
		
		File arquivoTabuleiro = new File(BancoImobiliario.nomeTabuleiro);
		Scanner leitor = null;
		
		// Construção do tabuleiro de jogo.
		try {
			leitor = new Scanner(arquivoTabuleiro);
		} catch (FileNotFoundException fnfe) {
			System.out.println("Erro: arquivo \"" +
					BancoImobiliario.nomeTabuleiro +
					"\" não encontrado.");
			return;
		}
		
		novoJogo.constroiTabuleiro(leitor);
		leitor.close();
		
		// Processamento de jogadas.
		File arquivoJogadas = new File(BancoImobiliario.nomeJogadas);
		
		try {
			leitor = new Scanner(arquivoJogadas);
		} catch (FileNotFoundException fnfe) {
			System.out.println("Erro: arquivo \"" +
					BancoImobiliario.nomeJogadas +
					"\" não encontrado.");
			return;
		}
		
		novoJogo.processaJogo(leitor);
		leitor.close();
		
		// Impressão de estatísticas.
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(BancoImobiliario.nomeSaida), "utf-8"))) {
			writer.write(novoJogo.imprimeEstatisticas());
		} catch (UnsupportedEncodingException e) {
			System.out.println("UTF-8 não suportado.");
		} catch (FileNotFoundException e) {
			System.out.println("Erro: arquivo \"" +
					BancoImobiliario.nomeSaida +
					"\" não encontrado.");
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
