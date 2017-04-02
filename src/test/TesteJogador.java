package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jogo.JogadorHumano;
import jogo.JogadorBanco;

public class TesteJogador {
	double saldoInicial = 5505.0;
	JogadorBanco j1 = new JogadorBanco();
	JogadorHumano j2 = new JogadorHumano(1, saldoInicial, 1);
	
	@Test
	public void testaAumentaSaldo() {
		double valor = 350.5;
		j2.aumentaSaldo(valor);
		assertEquals(saldoInicial + valor, j2.getSaldo(), 0.001);
	}
	
	@Test
	public void testaReduzSaldo() {
		double valor = 182.92;
		j2.reduzSaldo(valor);
		assertEquals(saldoInicial - valor, j2.getSaldo(), 0.001);
	}
	
	@Test
	public void testaIncVoltasTab() {
		for(int i = 0; i < 67; i++)
			j2.incVoltasTabuleiro();
		
		assertEquals(67, j2.getVoltasTabuleiro());
	}
	
	@Test
	public void testaIncNumPasseVez() {
		for(int i = 0; i < 210; i++)
			j2.incNumPasseVez();
		
		assertEquals(210, j2.getNumPasseVez());
	}
	
	@Test
	public void testaAumAluguelRecebido() {
		j2.aumentaAluguelRecebido(546.78);
		j2.aumentaAluguelRecebido(120);
		assertEquals(546.78+120, j2.getAluguelRecebido(), 0.001);
	}
	
	@Test
	public void testaAumAluguelPago() {
		j2.aumentaAluguelPago(78.29);
		j2.aumentaAluguelPago(120);
		assertEquals(78.29+120, j2.getAluguelPago(), 0.001);
	}
	
	@Test
	public void testaAumCompraImoveis() {
		j2.aumentaCompraImoveis(200);
		j2.aumentaCompraImoveis(12.12);
		assertEquals(200+12.12, j2.getCompraImoveis(), 0.001);
	}
	
	@Test
	public void testaJogadorPerdeu() {
		j2.setSaldo(-10.0);
		assertEquals(true, j2.jogadorPerdeu());
	}
	
	@Test
	public void testaPagaRodada() {
		double saldoInicial = j2.getSaldo();
		j1.pagaRodada(j2);
		assertEquals(saldoInicial + JogadorBanco.valorRodada, j2.getSaldo(), 0.001);
	}
}
