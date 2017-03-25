package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jogo.Jogador;

public class TestJogador {

	double initialAmount = 5505.0;
	Jogador p1 = new Jogador("Abel", initialAmount);

	@Test
	public void testIncreaseBalance() {
		double amount = 350.5;
		p1.increaseBalance(amount);
		assertEquals(initialAmount + amount, p1.getBalance(), 0.001);
	}

	@Test
	public void testDecreaseBalance() {
		double amount = 58.97;
		p1.decreaseBalance(amount);
		assertEquals(initialAmount - amount, p1.getBalance(), 0.001);
	}

}
