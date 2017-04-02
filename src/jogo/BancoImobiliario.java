package jogo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BancoImobiliario {
	private static final String nomeTabuleiro = "tabuleiro.txt";
	private static final String nomeJogadas = "jogadas.txt";
	private static final String nomeSaida = "estatistica.txt";
	
	private static PosicaoTabuleiro[] tabuleiro;
	private static int numPosicoes;
	private static int pInicialJogadores = 1;
	
	private static int numJogadores;
	private static int jogadoresAtivos = 0;
	private static double saldoInicialJogadores;
	private static JogadorHumano[] jogadores;
	
	private static int rodadas;
	
	public static PosicaoTabuleiro[] getTabuleiro() { return tabuleiro; }
	public static int getNumPosicoes() { return numPosicoes; }
	
	/**
	 * Constroi o tabuleiro de jogo a partir do arquivo de entrada do
	 * tabuleiro.
	 */
	public static void constroiTabuleiro(Scanner leitor) {
		String[] linha; // Corresponde a uma linha do arquivo de entrada.
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
				pInicialJogadores = posicao;
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
	
	/**
	 * Processa as jogadas do jogo como descritas no arquivo de entrada
	 * de jogadas.
	 */
	private static void processaJogo(Scanner leitor) {
		String[] linha; // Corresponde a uma linha do arquivo de entrada.
		int idJogador; // Identifica o jogador em cada rodada do jogo.
		int valorDado; // Valor do dado de seis faces tirado na rodada.
		
		linha = leitor.nextLine().split("%");
		rodadas = Integer.parseInt(linha[0]);
		numJogadores = Integer.parseInt(linha[1]);
		saldoInicialJogadores = Integer.parseInt(linha[2]);
		
		jogadores = new JogadorHumano[numJogadores];
		
		for (int i = 0; i < rodadas; i++) {
			linha = leitor.nextLine().split(";");
			
			// FInaliza o jogo caso a instrução seja DUMP.
			if (linha[0].equals("DUMP")) break;
			
			idJogador = Integer.parseInt(linha[1]);
			
			// Checa se o jogador atual ainda não realizou nenhuma jogada.
			if (jogadores[idJogador - 1] == null) {
				jogadores[idJogador - 1] =
						new JogadorHumano(idJogador, saldoInicialJogadores,
								pInicialJogadores);
				jogadoresAtivos++;
			}
			
			// Jogador joga dado e anda no tabuleiro.
			valorDado = Integer.parseInt(linha[2]);
			jogadores[idJogador - 1].andaNoTabuleiro(numPosicoes, valorDado);
			
			
		}
	}
	
	//TODO
	private static void imprimeEstatisticas() {
		
	}
	
	public static final void main(String args[]) {
		File arquivoTabuleiro = new File(nomeTabuleiro);
		Scanner leitor = null;
		
		// Construção do tabuleiro de jogo.
		
		try {
			leitor = new Scanner(arquivoTabuleiro);
		} catch (FileNotFoundException fnfe) {
			System.out.println("Erro: arquivo \"" + nomeTabuleiro +
					"\" não encontrado.");
			return;
		}
		
		constroiTabuleiro(leitor);
		leitor.close();
		
		// Processamento de jogadas.
		
		File arquivoJogadas = new File(nomeJogadas);
		
		try {
			leitor = new Scanner(arquivoJogadas);
		} catch (FileNotFoundException fnfe) {
			System.out.println("Erro: arquivo \"" + nomeJogadas +
					"\" não encontrado.");
			return;
		}
		
		processaJogo(leitor);
		
		// Impressão de estatísticas.
		
		imprimeEstatisticas();
	}
}
