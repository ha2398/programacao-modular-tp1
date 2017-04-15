package jogo;

public class JogadorBanco extends Jogador {

	public JogadorBanco() {
		/* Banco tem ID -1  e saldo infinito */
		super(-1, Double.POSITIVE_INFINITY); 
	}
	
	public void pagaRodada(JogadorHumano j1, double valorRodada) {
		j1.aumentaSaldo(valorRodada);
	}
}
