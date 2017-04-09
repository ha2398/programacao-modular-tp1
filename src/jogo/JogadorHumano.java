package jogo;

public class JogadorHumano extends Jogador {
	private int posicaoNoTabuleiro;
	
	/**
	 * Dados estatísticos sobre os jogadores
	 */
	private int voltasTabuleiro;
	private int numPasseVez;
	private double aluguelRecebido;
	private double aluguelPago;
	private double compraImoveis;
	
	public JogadorHumano(int id, double saldo, int pInicial) {
		super(id, saldo);
		this.posicaoNoTabuleiro = pInicial;
		this.voltasTabuleiro = 0;
		this.voltasTabuleiro = 0;
		this.aluguelRecebido = 0.0;
		this.aluguelPago = 0.0;
		this.compraImoveis = 0.0;
	}
	
	public int getVoltasTabuleiro() {
		return voltasTabuleiro;
	}
	
	public void incVoltasTabuleiro() {
		this.voltasTabuleiro++;
	}

	public int getNumPasseVez() {
		return numPasseVez;
	}
	
	public void incNumPasseVez() {
		this.numPasseVez++;
	}

	public double getAluguelRecebido() {
		return aluguelRecebido;
	}
	
	public void aumentaAluguelRecebido(double valor) {
		this.aluguelRecebido += valor;
	}

	public double getAluguelPago() {
		return aluguelPago;
	}
	
	public void aumentaAluguelPago(double valor) {
		this.aluguelPago += valor;
	}

	public double getCompraImoveis() {
		return compraImoveis;
	}
	
	public void aumentaCompraImoveis(double valor) {
		this.compraImoveis += valor;
	}
	
	public boolean jogadorPerdeu() {
		return this.getSaldo() < 0;
	}
	
	public int getPosicao() {
		return this.posicaoNoTabuleiro;
	}
	
	/**
	 * Anda no tabuleiro e retorna um valor boolean que indica se o jogador
	 * passou pela posição inicial.
	 * @param pInicial -> Indica a posição inicial do tabuleiro.
	 * @param tamanho -> Tamanho do tabuleiro.
	 * @param dado -> Valor do dado de seis faces obtido na rodada.
	 * @return true sse o jogador passou pela posição inicial.
	 */
	public boolean andaNoTabuleiro(int pInicial, int tamanho, int dado) {
		//this.posicaoNoTabuleiro = (this.posicaoNoTabuleiro + dado) % tamanho;
		
		/**
		 * Indica se o jogador passou pela posição inicial nessa rodada
		 */
		boolean passouInicio = false;
		
		for (int i = 0; i < dado; i++) {
			this.posicaoNoTabuleiro = ((this.posicaoNoTabuleiro++) % tamanho) + 1;
			
			if (this.posicaoNoTabuleiro == pInicial)
				passouInicio = true;
		}
		
		return passouInicio;
	}
	
	public void compraImovel(Imovel imovelComprado) {
		double valor = imovelComprado.getValorCompra();
		
		if (this.getSaldo() < valor)
			return;
		
		this.reduzSaldo(valor);
		imovelComprado.setDono(this.getId());
		this.aumentaCompraImoveis(valor);
	}
	
	public void pagaAluguel(Imovel imovelAlugado, Jogador dono) {
		double taxa = imovelAlugado.getTaxaAluguel();
		
		this.reduzSaldo(taxa);
		dono.aumentaSaldo(taxa);
		this.aumentaAluguelPago(taxa);
	}
}
