package jogo;

import java.util.Vector;

public class Tabuleiro {
	private Vector<PosicaoTabuleiro> tabuleiro;
	private int numPosicoes;

	public Tabuleiro() {
		this.tabuleiro = new Vector<>();
		this.numPosicoes = 0;
	}

	/**
	 * Construtor com parâmetros.
	 * 
	 * @param numPosicoes
	 *            Número de posições do tabuleiro
	 */
	public Tabuleiro(int numPosicoes) {
		this(); // Chama construtor sem argumentos
		this.tabuleiro.setSize(numPosicoes);
		this.numPosicoes = numPosicoes;
	}

	public Vector<PosicaoTabuleiro> getTabuleiro() {
		return tabuleiro;
	}

	public void setTabuleiro(Vector<PosicaoTabuleiro> tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public int getNumPosicoes() {
		return numPosicoes;
	}

	public void setNumPosicoes(int numPosicoes) {
		this.numPosicoes = numPosicoes;
	}

}
