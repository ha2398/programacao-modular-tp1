package jogo;

public class JogadorBanco extends Jogador {
	
	public static final double valorRodada = 500.0;

	public JogadorBanco() {
		/* Banco tem ID -1  e saldo infinito */
		super(-1, Double.POSITIVE_INFINITY); 
	}
	
	public static void pagaRodada(JogadorHumano j1) {
		j1.aumentaSaldo(valorRodada);
	}
}
