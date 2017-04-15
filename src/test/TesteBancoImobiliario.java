package test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.BeforeClass;
import org.junit.Test;

import jogo.BancoImobiliario;
import jogo.Imovel;
import jogo.JogadorBanco;
import jogo.JogadorHumano;
import jogo.PosicaoTabuleiro;
import jogo.Tabuleiro;

public class TesteBancoImobiliario {
	public static Tabuleiro tabuleiro = new Tabuleiro();
	public static BancoImobiliario jogo = new BancoImobiliario();
	
	@BeforeClass
	public static void init() {
		/* Criando as posições do tabuleiro */
		Imovel m1 = new Imovel(1, 500.0, 20.0);
		Imovel m2 = new Imovel(2, 450.0, 10.0);
		PosicaoTabuleiro p1 = new PosicaoTabuleiro(1, null);
		PosicaoTabuleiro p2 = new PosicaoTabuleiro(3, m1);
		PosicaoTabuleiro p3 = new PosicaoTabuleiro(3, m2);
		PosicaoTabuleiro p4 = new PosicaoTabuleiro(2, null);
		
		/* Atribuindo as posições ao tabuleiro */
		Vector<PosicaoTabuleiro> posicoes = new Vector<>();
		posicoes.add(p1);
		posicoes.add(p2);
		posicoes.add(p3);
		posicoes.add(p4);
		tabuleiro.setTabuleiro(posicoes);
		
		/* Salvando o tabuleiro no jogo */
		jogo.setTabuleiro(tabuleiro);
		
		/* Criando jogadores para o jogo */
		JogadorHumano j1 = new JogadorHumano(1, 6000.0, 1);
		JogadorHumano j2 = new JogadorHumano(2, 6000.0, 1);
		JogadorBanco banco = new JogadorBanco();
		
		/* Salvando os jogadores no jogo */
		Vector<JogadorHumano> jogadores = new Vector<>();
		jogadores.add(j1);
		jogadores.addElement(j2);
		jogo.setJogadores(jogadores);
		jogo.setBanco(banco);
	}
	
	@Test
	public void testaCriaTipoPosicaoStart() {
		String[] linha = {"1", "1", "1"};
		
		PosicaoTabuleiro p = jogo.criaTipoPosicao(linha, 1, 1);
		assertEquals(p.getTipo(), PosicaoTabuleiro.INICIO);
	}
	
	@Test
	public void testaCriaTipoPosicaoPassaAVez() {
		String[] linha = {"2", "2", "2"};
		
		PosicaoTabuleiro p = jogo.criaTipoPosicao(linha, 2, 2);
		assertEquals(p.getTipo(), PosicaoTabuleiro.PASSA);
	}
	
	@Test
	public void testaCriaTipoPosicaoImovel() {
		String[] linha = {"3", "3", "3", "1", "500", "10"};
		
		PosicaoTabuleiro p = jogo.criaTipoPosicao(linha, 3, 3);
		assertEquals(p.getTipo(), PosicaoTabuleiro.IMOVEL);
		assertEquals(p.getImovel().getTipo(), Imovel.RESIDENCIA);
	}
	
	@Test
	public void testaJogoTerminou() {
		assertEquals(jogo.jogoTerminou("DUMP"), true);
	}	
}
