package jogo;

public class Jogador {
	private int id;
	private double saldo;
	
	public Jogador(int id, double saldo) {
		this.id = id;
		this.saldo = saldo;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public void aumentaSaldo(double valor) {
		this.saldo += valor;
	}
	
	public void reduzSaldo(double valor) {
		this.saldo -= valor;
	}
}
