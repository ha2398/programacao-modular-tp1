package jogo;

public class Imovel {
	
	/* Tipos de imóveis */
	public static final int RESIDENCIA = 1;
	public static final int COMERCIO = 2;
	public static final int INDUSTRIA = 3;
	public static final int HOTEL = 4;
	public static final int HOSPITAL = 5;
	
	private int tipo;
	private int dono;
	private double valorCompra;
	private double taxaAluguel;
	
	public Imovel(int tipo, double compra, double aluguel) {
		this.tipo = tipo;
		this.dono = -1; // Banco é o dono inicial; seu ID é -1
		this.valorCompra = compra;
		this.taxaAluguel = aluguel;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getDono() {
		return dono;
	}

	public void setDono(int dono) {
		this.dono = dono;
	}

	public double getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(double valorCompra) {
		this.valorCompra = valorCompra;
	}

	public double getTaxaAluguel() {
		return taxaAluguel;
	}

	public void setTaxaAluguel(double taxaAluguel) {
		this.taxaAluguel = taxaAluguel;
	}
}
