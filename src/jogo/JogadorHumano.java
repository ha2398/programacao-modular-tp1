package jogo;

public class JogadorHumano extends Jogador {
	private int posicaoNoTabuleiro;
	private boolean ativo;

	/* Dados estatísticos sobre os jogadores */
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
		this.ativo = true;
	}

	/* Getters e setters */

	public boolean isAtivo() {
		return this.ativo && this.getSaldo() >= 0;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public int getPosicaoNoTabuleiro() {
		return this.posicaoNoTabuleiro;
	}

	private void setPosicaoTabuleiro(int posicaoAtual) {
		this.posicaoNoTabuleiro = posicaoAtual;
	}

	public int getVoltasTabuleiro() {
		return voltasTabuleiro;
	}

	public int getNumPasseVez() {
		return numPasseVez;
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

	/* Métodos do jogador */

	private void incVoltasTabuleiro() {
		this.voltasTabuleiro++;
	}

	public void incNumPasseVez() {
		this.numPasseVez++;
	}

	/**
	 * Anda no tabuleiro e retorna um valor boolean que indica se o jogador
	 * passou pela posição inicial.
	 * 
	 * @param tamanho
	 *            Tamanho do tabuleiro.
	 * @param dado
	 *            Valor do dado de seis faces obtido na rodada.
	 * @return Retorna true se o jogador passou pela posição inicial, false caso
	 *         contrário
	 */
	public boolean andaNoTabuleiro(int tamanho, int dado) {
		int posicaoAnterior = this.getPosicaoNoTabuleiro();
		int posicaoAtual = (this.getPosicaoNoTabuleiro() + dado) % tamanho;

		this.setPosicaoTabuleiro((posicaoAtual != 0) ? posicaoAtual : tamanho);

		if ((posicaoAnterior + dado) > tamanho) {
			this.incVoltasTabuleiro();
			return true;
		}
		return false;
	}

	/**
	 * Realiza a operação de comprar um imóvel.
	 * 
	 * @param imovelComprado
	 *            Imóvel que o jogador irá adquirir
	 * @return Retorna true se a compra for bem-sucedida (jogador possuir saldo
	 *         suficiente), falso caso contrário
	 */
	public boolean compraImovel(Imovel imovelComprado) {
		double valor = imovelComprado.getValorCompra();

		if (this.getSaldo() < valor)
			return false;

		this.reduzSaldo(valor);
		imovelComprado.setDono(this.getId());
		this.aumentaCompraImoveis(valor);
		return true;
	}

	/**
	 * Realiza o pagamento da taxa de aluguel a outro jogador.
	 * 
	 * @param imovelAlugado
	 *            imóvel cuja taxa de aluguel deve ser paga pelo jogador
	 * @param dono
	 *            jogador dono do imóvel
	 * @return true se o pagament for bem-sucedido (jogador possuir saldo
	 *         suficiente), falso caso contrário
	 */
	public boolean pagaAluguel(Imovel imovelAlugado, JogadorHumano dono) {
		double taxa = imovelAlugado.getTaxaAluguel();
		double preco = imovelAlugado.getValorCompra();
		double aluguel = (taxa / 100) * preco;

		if (this.getSaldo() < aluguel)
			return false;

		this.reduzSaldo(aluguel);
		this.aumentaAluguelPago(aluguel);

		dono.aumentaSaldo(aluguel);
		dono.aumentaAluguelRecebido(aluguel);
		return true;
	}
}
