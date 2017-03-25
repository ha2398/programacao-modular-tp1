package jogo;

public class Jogador {
	private int id;
	private double saldo;
	
	/**
	 * Dados estatísticos
	 */
	private int voltasTabuleiro;
	private int numPasseVez;
	private double aluguelRecebido;
	private double aluguelPago;
	private double compraImoveis;
	
	public Jogador(int id, double saldo) {
		this.id = id;
		this.saldo = saldo;
	}

	public int getId() {
		return id;
	}

	public double getSaldo() {
		return saldo;
	}
	
	public void aumentaSaldo(double valor) {
		this.saldo += valor;
	}
	
	public void reduzSaldo(double valor) {
		this.saldo -= valor;
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
}
