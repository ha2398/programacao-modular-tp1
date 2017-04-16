package jogo;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Scanner;
import java.util.Vector;

public class BancoImobiliario {
	/* Variáveis estáticas públicas do jogo */
	public static final String nomeTabuleiro = "tabuleiro.txt";
	public static final String nomeJogadas = "jogadas.txt";
	public static final String nomeSaida = "estatistica.txt";
	public static final double valorRodada = 500.0;

	/* Variáveis referentes ao tabuleiro */
	private Tabuleiro tabuleiro;
	private int pInicialJogadores;

	/* Variáveis referentes aos jogadores */
	private int numJogadores;
	private int jogadoresAtivos;
	private Vector<JogadorHumano> jogadores;
	private JogadorBanco banco;

	/* Variáveis referentes ao jogo */
	private int rodadas;

	/**
	 * Construtor sem argumentos para BancoImobiliario.
	 */
	public BancoImobiliario() {
		this.pInicialJogadores = 1;
		this.rodadas = 0;
		this.tabuleiro = new Tabuleiro();
		this.banco = new JogadorBanco();
	}

	/**
	 * Construtor para BancoImobiliario.
	 * 
	 * @param numJogadores
	 *            Número de jogadores
	 * @param posicaoInicial
	 *            Posição inicial dos jogadores no tabuleiro
	 * @param tabuleiro
	 *            Tabuleiro do jogo
	 * @param jogadores
	 *            Vector de JogadoresHumanos
	 * @param banco
	 *            Jogador banco
	 */
	public BancoImobiliario(int numJogadores, int posicaoInicial, Tabuleiro tabuleiro, Vector<JogadorHumano> jogadores,
			JogadorBanco banco) {
		this.pInicialJogadores = posicaoInicial;
		this.numJogadores = numJogadores;
		this.rodadas = 0;
		this.tabuleiro = tabuleiro;
		this.banco = banco;
	}

	/**
	 * Verifica o tipo de posição que está sendo lida da entrada e cria um
	 * objeto TipoPosicao.
	 * 
	 * @param linha
	 *            Linha que está sendo lida
	 * @param tipoPosicao
	 *            Valor inteiro de 1 a 3 que determina o tipo de posição (start,
	 *            passa a vez ou imóvel, respectivamente)
	 * @param posicao
	 *            Valor da posição no tabuleiro que está sendo analisada
	 * @return uma PosicaoTabuleiro
	 */
	public PosicaoTabuleiro criaTipoPosicao(String[] linha, int tipoPosicao, int posicao) {
		PosicaoTabuleiro novaPosicao = null;

		switch (tipoPosicao) {
		case PosicaoTabuleiro.INICIO:
			this.pInicialJogadores = posicao;
			novaPosicao = new PosicaoTabuleiro(tipoPosicao, null);
			break;

		case PosicaoTabuleiro.PASSA:
			novaPosicao = new PosicaoTabuleiro(tipoPosicao, null);
			break;

		case PosicaoTabuleiro.IMOVEL:
			int tipoImovel = Integer.parseInt(linha[3]);
			double valorImovel = Double.parseDouble(linha[4]);
			double taxaAluguel = Double.parseDouble(linha[5]);

			Imovel novoImovel = new Imovel(tipoImovel, valorImovel, taxaAluguel);

			novaPosicao = new PosicaoTabuleiro(tipoPosicao, novoImovel);
			break;
		}
		return novaPosicao;
	}

	/**
	 * Constrói o tabuleiro de jogo a partir do arquivo de entrada do tabuleiro.
	 * 
	 * @param leitor
	 *            objeto para permitir a leitura do tabuleiro
	 */
	public void constroiTabuleiro(Scanner leitor) {
		String[] linha; // Corresponde a uma linha do arquivo de entrada.
		int posicao; // Posição a ser inserida no tabuleiro.
		int tipoPosicao; // Tipo da posição a ser inserida no tabuleiro
		Vector<PosicaoTabuleiro> posicoes = new Vector<>();

		int numPosicoes = Integer.parseInt(leitor.nextLine());
		tabuleiro.setNumPosicoes(numPosicoes);
		posicoes.setSize(numPosicoes);

		for (int i = 0; i < numPosicoes; i++) {
			linha = leitor.nextLine().split(";");

			posicao = Integer.parseInt(linha[1]);
			tipoPosicao = Integer.parseInt(linha[2]);

			PosicaoTabuleiro novaPosicao = criaTipoPosicao(linha, tipoPosicao, posicao);

			// Posições vão de 1 até n, mas posições em Vector de 0 a (n-1)
			posicoes.set(posicao - 1, novaPosicao);
		}
		tabuleiro.setTabuleiro(posicoes);
	}

	/**
	 * Analisa a posição em que um jogador caiu durante o jogo e realiza as
	 * ações necessárias.
	 * 
	 * @param posicao
	 *            posição em que o jogador caiu
	 * @param jogador
	 *            jogador atual
	 */
	private void analisaPosicao(PosicaoTabuleiro posicao, JogadorHumano jogador) {
		int tipo = posicao.getTipo();

		switch (tipo) {
		case PosicaoTabuleiro.INICIO:
			break;
		case PosicaoTabuleiro.PASSA:
			jogador.incNumPasseVez();
			break;
		case PosicaoTabuleiro.IMOVEL:
			Imovel imovelAtual = posicao.getImovel();
			int idDonoImovel = imovelAtual.getDono();

			if (idDonoImovel == Imovel.BANCO) {
				if (!jogador.compraImovel(imovelAtual)) {
					jogador.setAtivo(false);
					this.jogadoresAtivos--;
				}
			} else if (idDonoImovel != jogador.getId()) {
				JogadorHumano donoImovel = jogadores.get(idDonoImovel - 1);
				if (!jogador.pagaAluguel(imovelAtual, donoImovel)) {
					jogador.setAtivo(false);
					this.jogadoresAtivos--;
				}
			}

			break;
		}
	}

	/**
	 * Processa as jogadas do jogo como descritas no arquivo de entrada de
	 * jogadas.
	 * 
	 * @param leitor
	 *            Entrada com os dados para processar o jogo
	 */
	public void processaJogo(Scanner leitor) {
		String[] linha; // Corresponde a uma linha do arquivo de entrada.
		int idJogador; // Identifica o jogador em cada rodada do jogo.
		int valorDado; // Valor do dado de seis faces tirado na rodada.
		JogadorHumano jogadorAtual; // armazena jogador atual
		boolean passouInicio = false; // indica se jogador terminou uma volta do
										// tabuleiro

		linha = leitor.nextLine().split("%");
		int numJogadas = Integer.parseInt(linha[0]);
		this.numJogadores = Integer.parseInt(linha[1]);
		double saldoInicialJogadores = Integer.parseInt(linha[2]);

		this.jogadoresAtivos = numJogadores;
		this.criaJogadores(numJogadores, saldoInicialJogadores, pInicialJogadores);

		for (int i = 0; i < numJogadas; i++) {

			linha = leitor.nextLine().split(";");

			// Verifica condições de término
			if (this.jogoTerminou(linha[0]) || this.jogadoresAtivos <= 1) {
				break;
			}

			/*
			 * Recebe id do jogador atual e verifica se uma nova rodada está
			 * começando.
			 */
			idJogador = Integer.parseInt(linha[1]);
			if (idJogador == 1) {
				rodadas++;
			}
			jogadorAtual = jogadores.get(idJogador - 1);

			// Verifica se jogador está ativo
			if (!jogadorAtual.isAtivo())
				continue;
			
			// Indica se o jogador passou pela posição inicial nessa rodada
			valorDado = Integer.parseInt(linha[2]);
			passouInicio = jogadorAtual.andaNoTabuleiro(tabuleiro.getNumPosicoes(), valorDado);

			/*
			 * Toda vez que um jogador passa na posição inicial, recebe uma taxa
			 * do Banco.
			 */
			if (passouInicio) {
				banco.pagaRodada(jogadorAtual, BancoImobiliario.valorRodada);
			}

			PosicaoTabuleiro posicaoAtual = tabuleiro.getTabuleiro().get(jogadorAtual.getPosicaoNoTabuleiro() - 1);

			this.analisaPosicao(posicaoAtual, jogadorAtual);
		}
	}

	/**
	 * Verifica condição de término do jogo.
	 * 
	 * @param string
	 *            Instrução recebida
	 * @return Retorna true se instrução for DUMP, false caso contrário
	 */
	public boolean jogoTerminou(String string) {
		if (string.equals("DUMP"))
			return true;
		return false;
	}

	/**
	 * Cria um Vector de jogadores definindo o saldo inicial e a posição
	 * inicial.
	 * 
	 * @param numJogadores
	 *            Quantidade de jogadores
	 * @param saldoInicialJogadores
	 *            Saldo inicial disponível para cada jogador
	 * @param pInicialJogadores
	 *            Posição inicial em que os jogadores se encontram antes do jogo
	 *            começar
	 */
	private void criaJogadores(int numJogadores, double saldoInicialJogadores, int pInicialJogadores) {
		this.jogadores = new Vector<>();
		this.jogadores.setSize(numJogadores);

		for (int i = 0; i < numJogadores; i++) {
			this.jogadores.set(i, new JogadorHumano(i + 1, saldoInicialJogadores, pInicialJogadores));
		}
	}

	/**
	 * Coleta informações estatísticas sobre o jogo.
	 * 
	 * @return String que representa as estatísticas do jogo.
	 */
	public String imprimeEstatisticas() {
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
		sym.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(sym);

		String aux;
		String estatisticas = "";

		// Imprime o número de rodadas
		estatisticas += "1:" + rodadas + "\n";

		// Imprime número de voltas de cada jogador
		estatisticas += "2:";
		for (int i = 0; i < numJogadores; i++) {
			int voltas = jogadores.get(i).getVoltasTabuleiro();
			estatisticas += (i + 1) + "-" + voltas;
			if (i + 1 < numJogadores) {
				estatisticas += ";";
			}
		}
		estatisticas += "\n";

		// Imprime saldo final de cada jogador
		estatisticas += "3:";
		for (int i = 0; i < numJogadores; i++) {
			double saldo = jogadores.get(i).getSaldo();
			aux = df.format(saldo);

			estatisticas += (i + 1) + "-" + aux;
			if (i + 1 < numJogadores) {
				estatisticas += ";";
			}
		}
		estatisticas += "\n";

		// Imprime o valor recebido em aluguéis por cada jogador
		estatisticas += "4:";
		for (int i = 0; i < numJogadores; i++) {
			double aluguelRecebido = jogadores.get(i).getAluguelRecebido();
			aux = df.format(aluguelRecebido);

			estatisticas += (i + 1) + "-" + aux;
			if (i + 1 < numJogadores) {
				estatisticas += ";";
			}
		}
		estatisticas += "\n";

		// Imprime o valor pago em aluguéis por cada jogador
		estatisticas += "5:";
		for (int i = 0; i < numJogadores; i++) {
			double aluguelPago = jogadores.get(i).getAluguelPago();
			aux = df.format(aluguelPago);

			estatisticas += (i + 1) + "-" + aux;
			if (i + 1 < numJogadores) {
				estatisticas += ";";
			}
		}
		estatisticas += "\n";

		// Imprime o valor gasto na compra de imóveis por cada jogador
		estatisticas += "6:";
		for (int i = 0; i < numJogadores; i++) {
			double valorCompra = jogadores.get(i).getCompraImoveis();
			aux = df.format(valorCompra);

			estatisticas += (i + 1) + "-" + aux;
			if (i + 1 < numJogadores) {
				estatisticas += ";";
			}
		}
		estatisticas += "\n";

		// Imprime o número de "passe a vez" de cada jogador.
		estatisticas += "7:";
		for (int i = 0; i < numJogadores; i++) {
			int passaVez = jogadores.get(i).getNumPasseVez();
			estatisticas += (i + 1) + "-" + passaVez;
			if (i + 1 < numJogadores) {
				estatisticas += ";";
			}
		}
		estatisticas += "\n";

		return estatisticas;
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public void setTabuleiro(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public int getpInicialJogadores() {
		return pInicialJogadores;
	}

	public void setpInicialJogadores(int pInicialJogadores) {
		this.pInicialJogadores = pInicialJogadores;
	}

	public int getNumJogadores() {
		return numJogadores;
	}

	public void setNumJogadores(int numJogadores) {
		this.numJogadores = numJogadores;
	}

	public int getJogadoresAtivos() {
		return jogadoresAtivos;
	}

	public void setJogadoresAtivos(int jogadoresAtivos) {
		this.jogadoresAtivos = jogadoresAtivos;
	}

	public Vector<JogadorHumano> getJogadores() {
		return jogadores;
	}

	public void setJogadores(Vector<JogadorHumano> jogadores) {
		this.jogadores = jogadores;
	}

	public JogadorBanco getBanco() {
		return banco;
	}

	public void setBanco(JogadorBanco banco) {
		this.banco = banco;
	}

	public int getRodadas() {
		return rodadas;
	}

	public void setRodadas(int rodadas) {
		this.rodadas = rodadas;
	}

}
