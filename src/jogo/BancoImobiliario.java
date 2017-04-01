package jogo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BancoImobiliario {
	private static final String nomeTabuleiro = "tabuleiro.txt";
	private static final String nomeJogadas = "jogadas.txt";
	private static final String nomeSaida = "estatistica.txt";
	
	private static PosicaoTabuleiro[] tabuleiro;
	private static int numPosicoes;
	
	private static int numJogadores;
	private static double saldoInicialJogadores;
	private static ArrayList<Jogador> jogadores;
	
	private static int rodadas;
	
	public static PosicaoTabuleiro[] getTabuleiro() { return tabuleiro; }
	public static int getNumPosicoes() { return numPosicoes; }
	
	/**
	 * Constroi o tabuleiro de jogo a partir do arquivo de entrada do
	 * tabuleiro.
	 */
	public static void constroiTabuleiro(Scanner leitor) {
		String[] linha; // Corresponde a uma linha do arquivo tabuleiros.txt
		int posicao; // Posição a ser inserida no tabuleiro.
		int tipoPosicao; // Tipo da posição a ser inserida no tabuleiro.
		int tipoImovel; // Tipo do imóvel, caso a posição represente um.
		double valorImovel; // Valor do imóvel, caso a posição represente um.
		double taxaAluguel; // Aluguel, caso a posição represente um imóvel. 
		
		numPosicoes = Integer.parseInt(leitor.nextLine());
		
		tabuleiro = new PosicaoTabuleiro[numPosicoes];
		
		for(int i = 0; i < numPosicoes; i++) {
			linha = leitor.nextLine().split(";");
			
			posicao = Integer.parseInt(linha[1]);
			tipoPosicao = Integer.parseInt(linha[2]);
			
			PosicaoTabuleiro novaPosicao = null;
			
			// Analisa o tipo da posição
			switch(tipoPosicao) {
			case PosicaoTabuleiro.INICIO:
			case PosicaoTabuleiro.PASSA:
				novaPosicao = new PosicaoTabuleiro(tipoPosicao, null);
				break;
				
			case PosicaoTabuleiro.IMOVEL:
				tipoImovel = Integer.parseInt(linha[3]);
				valorImovel = Double.parseDouble(linha[4]);
				taxaAluguel = Double.parseDouble(linha[5]);
				
				Imovel novoImovel = new Imovel(tipoImovel, valorImovel,
						taxaAluguel);
				
				novaPosicao = new PosicaoTabuleiro(tipoPosicao, novoImovel);
				break;
			}
			
			// Posições vão de 1 até n, mas posições em array de 0 a (n-1)
			tabuleiro[posicao - 1] = novaPosicao;
		}
	}
	
	//TODO
	private static void processaJogo() {
		
	}
	
	//TODO
	private static void imprimeEstatisticas() {
		
	}
	
	public static final void main(String args[]) {
		File arquivoTabuleiro = new File(nomeTabuleiro);
		Scanner leitor = null;
		
		try {
			leitor = new Scanner(arquivoTabuleiro);
		} catch (FileNotFoundException fnfe) {
			System.out.println("Erro: arquivo \"" + nomeTabuleiro +
					"\" não encontrado.");
			return;
		}
		
		constroiTabuleiro(leitor);
		leitor.close();
		
		processaJogo();
		imprimeEstatisticas();
	}
}
