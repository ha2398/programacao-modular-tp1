package jogo;

public class PosicaoTabuleiro {
	/* Tipo das posições */
	public static final int INICIO = 1;
	public static final int PASSA = 2;
	public static final int IMOVEL = 3;

	private int tipo;
	private Imovel imovel;

	public PosicaoTabuleiro(int tipo, Imovel imovel) {
		this.tipo = tipo;
		this.imovel = imovel;
	}

	public int getTipo() {
		return this.tipo;
	}

	public Imovel getImovel() {
		return this.imovel;
	};
}
