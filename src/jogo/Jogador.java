package jogo;

public class Jogador {
	private String name;
	private double balance;

	public Jogador(String name, double balance) {
		this.name = name;
		this.balance = balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return this.balance;
	}

	public void decreaseBalance(double amount) {
		this.balance -= amount;
	}

	public void increaseBalance(double amount) {
		this.balance += amount;
	}
}
