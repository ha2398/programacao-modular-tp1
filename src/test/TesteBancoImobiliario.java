package test;

import static org.junit.Assert.*;

import org.junit.Test;

import jogo.*;
import java.util.Scanner;

public class TesteBancoImobiliario {
	private String tabuleiro = "5\n"
			+ "1;4;2\n"
			+ "2;2;3;5;1000;150\n"
			+ "3;1;1\n"
			+ "4;3;3;2;2000;300\n"
			+ "5;5;3;1;5000;1000";
	
	@Test
	public void testaConstroiTabuleiro() {
		Scanner leitor = new Scanner(tabuleiro);
		BancoImobiliario.constroiTabuleiro(leitor);
		
		PosicaoTabuleiro[] posicoesTabuleiro = BancoImobiliario.getTabuleiro();
		
		assertNotNull(posicoesTabuleiro);
		assertEquals(BancoImobiliario.getNumPosicoes(), 5);
		
		// Tipos
		assertEquals(posicoesTabuleiro[0].getTipo(), 1);
		assertEquals(posicoesTabuleiro[1].getTipo(), 3);
		assertEquals(posicoesTabuleiro[2].getTipo(), 3);
		assertEquals(posicoesTabuleiro[3].getTipo(), 2);
		assertEquals(posicoesTabuleiro[4].getTipo(), 3);
		
		assertEquals(posicoesTabuleiro[2].getImovel().getTaxaAluguel(), 300, 0.001);
	}
}
