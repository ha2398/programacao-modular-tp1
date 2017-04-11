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

public class BancoImobiliario {
	private static final String nomeTabuleiro = "tabuleiro.txt";
	private static final String nomeJogadas = "jogadas.txt";
	private static final String nomeSaida = "estatistica.txt";
	
	private static PosicaoTabuleiro[] tabuleiro;
	private static int numPosicoes;
	private static int pInicialJogadores = 1;
	
	private static int numJogadores;
	private static int jogadoresAtivos;
	private static double saldoInicialJogadores;
	private static JogadorHumano[] jogadores;
	
	private static JogadorBanco banco;
	
	private static int numJogadas;
	private static int rodadas = 0;
	
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
		numJogadas = Integer.parseInt(linha[0]);
		numJogadores = Integer.parseInt(linha[1]);
		saldoInicialJogadores = Integer.parseInt(linha[2]);
		
		jogadoresAtivos = numJogadores;
		
		jogadores = new JogadorHumano[numJogadores];
		banco = new JogadorBanco();
		
		for (int i = 0; i < numJogadas; i++) {
			// Termina partida caso só exista um jogador com saldo positivo.
			if (jogadoresAtivos <= 1) break;
			
			linha = leitor.nextLine().split(";");
			
			// FInaliza o jogo caso a instrução seja DUMP.
			if (linha[0].equals("DUMP")) break;
			
			idJogador = Integer.parseInt(linha[1]);
			
			// Checa se uma nova rodada está começando.
			if (idJogador == 1) rodadas++;
			
			JogadorHumano jogadorAtual = jogadores[idJogador - 1];
			
			// Checa se o jogador atual ainda não realizou nenhuma jogada.
			if (jogadorAtual == null) {
				jogadorAtual =
						new JogadorHumano(idJogador, saldoInicialJogadores,
								pInicialJogadores);
			}
			
			// Jogadores com saldo negativo estão fora da partida.
			if (jogadorAtual.getSaldo() < 0) continue;
			
			// Jogador joga dado e anda no tabuleiro.
			
			/**
			 * Indica se o jogador passou pela posição inicial nessa rodada
			 */
			boolean passouInicio = false;
			
			valorDado = Integer.parseInt(linha[2]);
			passouInicio =
					jogadorAtual.andaNoTabuleiro(pInicialJogadores,
							numPosicoes, valorDado);
			
			/**
			 * Toda vez que um jogador passa na posição inicial, recebe uma
			 * taxa do Banco.
			 */
			if (passouInicio) {
				banco.pagaRodada(jogadorAtual);
				jogadorAtual.incVoltasTabuleiro();
			}
			
			PosicaoTabuleiro posicaoAtual =
					tabuleiro[jogadorAtual.getPosicao()-1];
			
			int tipoNovaPosicao = posicaoAtual.getTipo();
			
			// Analisa posição em que o jogador caiu.
			switch (tipoNovaPosicao) {
			case PosicaoTabuleiro.INICIO:
				break;
			case PosicaoTabuleiro.PASSA: // Jogador não paga nada.
				jogadorAtual.incNumPasseVez();
				break;
			case PosicaoTabuleiro.IMOVEL:
				/**
				 * Jogador compra imóvel, se for do banco e
				 * jogador paga aluguel do imóvel, se for de outro jogador.
				 */
				Imovel imovelAtual = posicaoAtual.getImovel();
				int idDonoImovel = imovelAtual.getDono();
				
				if (idDonoImovel == Imovel.BANCO) {
					jogadorAtual.compraImovel(imovelAtual);
				} else if (idDonoImovel != idJogador) {
					JogadorHumano donoImovel = jogadores[idDonoImovel-1];
					jogadorAtual.pagaAluguel(imovelAtual, donoImovel);
				}
				
				/**
				 * Checa se o jogador atual ficou com saldo negativo e portanto
				 * deve ser eliminado do jogo.
				 */
				if (jogadorAtual.getSaldo() <= 0) jogadoresAtivos--;
				
				break;
			}
			
			jogadores[idJogador - 1] = jogadorAtual;
		}
	}
	
	//TODO
	private static String imprimeEstatisticas() {
		String estatisticas = "";
		
		// Imprime o número de rodadas
		estatisticas += "1:" + rodadas + "\n";
		
		// Imprime número de voltas de cada jogador
		estatisticas += "2:";
		for(int i = 0; i < numJogadores; i++) {
			estatisticas += (i+1) + "-" + jogadores[i].getVoltasTabuleiro();
			if(i + 1 < numJogadores) {
				estatisticas += ";";
			}
		}
		estatisticas += "\n";
		
		// Imprime saldo final de cada jogador
		estatisticas += "3:";
		for(int i = 0; i < numJogadores; i++) {
			estatisticas += (i+1) + "-" + jogadores[i].getSaldo();
			if(i + 1 < numJogadores) {
				estatisticas += ";";
			}
		}
		estatisticas += "\n";
		
		// Imprime o valor recebido em aluguéis por cada jogador
		estatisticas += "4:";
		for(int i = 0; i < numJogadores; i++) {
			estatisticas += (i+1) + "-" + jogadores[i].getAluguelRecebido();
			if(i + 1 < numJogadores) {
				estatisticas += ";";
			}
		}
		estatisticas += "\n";
		
		// Imprime o valor pago em aluguéis por cada jogador
		estatisticas += "5:";
		for(int i = 0; i < numJogadores; i++) {
			estatisticas += (i+1) + "-" + jogadores[i].getAluguelPago();
			if(i + 1 < numJogadores) {
				estatisticas += ";";
			}
		}
		estatisticas += "\n";
		
		// Imprime o valor gasto na compra de imóveis por cada jogador
		estatisticas += "6:";
		for(int i = 0; i < numJogadores; i++) {
			estatisticas += (i+1) + "-" + jogadores[i].getCompraImoveis();
			if(i + 1 < numJogadores) {
				estatisticas += ";";
			}
		}
		estatisticas += "\n";
		
		// Imprime o valor gasto na compra de imóveis por cada jogador
		estatisticas += "7:";
		for(int i = 0; i < numJogadores; i++) {
			estatisticas += (i+1) + "-" + jogadores[i].getNumPasseVez();
			if(i + 1 < numJogadores) {
				estatisticas += ";";
			}
		}
		estatisticas += "\n";
		
		return estatisticas;
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
		leitor.close();
		
		// Impressão de estatísticas.
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(nomeSaida), "utf-8"))) {
			writer.write(imprimeEstatisticas());
		} catch (UnsupportedEncodingException e) {
			System.out.println("UTF-8 não suportado.");
		} catch (FileNotFoundException e) {
			System.out.println("Erro: arquivo \"" + nomeSaida +
					"\" não encontrado.");
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
