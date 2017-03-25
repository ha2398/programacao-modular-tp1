package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jogo.Jogador;

public class TesteJogador {
	double saldoInicial = 5505.0;
	Jogador j1 = new Jogador(1, saldoInicial);
	
	@Test
	public void testaAumentaSaldo() {
		double valor = 350.5;
		j1.aumentaSaldo(valor);
		assertEquals(saldoInicial + valor, j1.getSaldo(), 0.001);
	}
	
	@Test
	public void testaReduzSaldo() {
		double valor = 182.92;
		j1.reduzSaldo(valor);
		assertEquals(saldoInicial - valor, j1.getSaldo(), 0.001);
	}
	
	@Test
	public void testaIncVoltasTab() {
		for(int i = 0; i < 67; i++)
			j1.incVoltasTabuleiro();
		
		assertEquals(67, j1.getVoltasTabuleiro(), 0.001);
	}
	
	@Test
	public void testaIncNumPasseVez() {
		for(int i = 0; i < 210; i++)
			j1.incNumPasseVez();
		
		assertEquals(210, j1.getNumPasseVez(), 0.001);
	}
	
	@Test
	public void testaAumAluguelRecebido() {
		j1.aumentaAluguelRecebido(546.78);
		j1.aumentaAluguelRecebido(120);
		assertEquals(546.78+120, j1.getAluguelRecebido(), 0.001);
	}
	
	@Test
	public void testaAumAluguelPago() {
		j1.aumentaAluguelPago(78.29);
		j1.aumentaAluguelPago(120);
		assertEquals(78.29+120, j1.getAluguelPago(), 0.001);
	}
	
	@Test
	public void testaAumCompraImoveis() {
		j1.aumentaCompraImoveis(200);
		j1.aumentaCompraImoveis(12.12);
		assertEquals(200+12.12, j1.getCompraImoveis(), 0.001);
	}
}
