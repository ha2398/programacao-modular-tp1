#Programação Modular - Trabalho Prático 1 - 1º Semestre de 2017 - UFMG/DCC

**Autores**: [Hugo Sousa](https://github.com/ha2398) e [Jéssica Carneiro](https://github.com/jessicacarneiro)

##Introdução

O objetivo desse trabalho é implementar um subconjunto do jogo Banco Imobiliário, utilizando a linguagem Java.

##Instruções

O tabuleiro é composto de uma sequência de posições, sendo que em cada posição do tabuleiro será associado um determinado imóvel. 

Sendo que os imóveis podem ser dos seguintes tipos:

* Residência
* Comércio
* Indústria
* Hotel
* Hospital

Por meio da leitura de um arquivo (tabuleiro.txt), disponibilizado na mesma pasta de execução do programa,
é construído um tabuleiro que tem para cada posição um imóvel pré-determinado. Esse arquivo ainda
descreve para cada imóvel um valor de compra e uma taxa de aluguel. O sistema também lê um segundo arquivo (jogadas.txt), também disponibilizado na mesma pasta de execução do
programa. Esse arquivo descreve as instruções que correspondem às jogadas dos participantes no Banco Imobiliário. O arquivo jogadas.txt indica os jogadores e seus respectivos números tirados em um dado de seis faces. Cada linha deste arquivo será uma jogada.

###Regras do Jogo

O jogo tem algumas simplificações comparado à versão de tabuleiro original. A seguir estão descritas algumas regras específicas:

1. Cada imóvel inicialmente pertence ao Banco;
1. O Banco tem valor infinito em caixa;
1. Caso a posição do tabuleiro que o jogador caiu, mediante o número lido do arquivo de jogadas, for um imóvel do Banco a ação será: jogador deverá comprar o imóvel. Desta forma, o jogador deverá pagar para o banco a quantia referente ao imóvel (desde que possua saldo suficiente).
1. Caso o imóvel seja de outro jogador a ação será: jogador deverá pagar o valor do aluguel. Desta forma, o jogador deverá pagar para outro jogador, dono do imóvel, a quantia referente ao aluguel do imóvel. Se o jogador cair em um imóvel que seja dele próprio não deverá pagar e nem receber nada.
1. Caso o jogador tenha caído em uma posição do tabuleiro que tem um passe a vez, o jogador não paga nada e uma nova instrução deverá ser lida;
1. Se o jogador tiver saldo menor que zero, as jogadas do arquivo de Jogadas correspondente a ele deverão ser ignoradas (jogador não está mais participando da partida);
1. Cada vez que um jogador passar pela a posição inicial deverá receber um valor de 500 reais do Banco;
1. O sistema deverá terminar o jogo quando restar apenas um jogador com saldo positivo, ou quando for lido no arquivo de entrada a instrução DUMP;

## Arquivos de Entrada e Saída

###Entrada

**tabuleiro.txt**: Este arquivo indica como será o tabuleiro do jogo.

A primeira linha do arquivo corresponde ao número de posições que o tabuleiro deverá ter. As demais linhas
descrevem qual o tipo da posição: 1) Iniciar, 2) Passe a vez e 3) Imóvel.
Os campos de cada linha são separados por ponto e vírgula (;). Com exceção da primeira linha, as demais linhas são organizadas da seguinte forma:

1. **Id**: é um identificador de cada linha no arquivo que figura de 1 até n.
1. **Posição**: corresponde a um identificador da posição que a especificada deve ocupar no tabuleiro, indo de 1 até a posição n. As posições não são precisam necessariamente ser descritas em ordem crescente.
1. **Tipo da posição**: Início (1), Passe a vez (2) e Imóvel (3).
1. **Tipo do imóvel**: Corresponde ao tipo do imóvel que pertence a posição, conforme a Tabela 1.
1. **Valor do imóvel**: Corresponde ao valor do imóvel para compra;
1. **Taxa do aluguel**: Corresponde à taxa de aluguel do imóvel. Esse valor é descrito em porcentagem.
Ou seja, será a porcentagem do valor do imóvel.

**Tabela 1:**

Imóvel | Identificador
--------- |------------
Residência | 1
Comércio | 2
Indústria | 3
Hotel | 4
Hospital | 5

**jogadas.txt**: Este arquivo informa as jogadas de cada participante do jogo. A primeira linha do arquivo corresponde ao número de instruções de jogadas que o arquivo contém, o
número de jogadores participantes e o valor inicial que cada jogador começa o jogo. Essas informações são
separadas pelo caractere (%).
As demais linhas correspondem às jogadas de cada jogador. Sendo que, estas linhas têm três campos
separados por ponto e vírgula (;). Os campos são organizados da seguinte forma:

1. **Id**: é um identificador de cada linha no arquivo que vai de 1 até n.
1. **Id do jogador**: corresponde ao identificador do jogador.
1. **Valor do dado**: indica o valor do dado de seis faces, que o jogador tirou na jogada.

###Saída

O arquivo de saída será relativo apenas às estatísticas do jogo. Ao logo do jogo deverão ser computadas informações para responder as seguintes questões:

1. Quantas rodadas o jogo teve?
1. Quantas voltas foram dadas no tabuleiro por cada jogador?
1. Quanto de dinheiro cada jogador acumulou?
1. Qual foi a quantidade de aluguel recebida por cada jogador?
1. Qual foi o valor pago de aluguel por cada jogador?
1. Qual foi o valor gasto na compra de imóveis por cada jogador?
1. Quantos "passa a vez" cada jogador teve?

No arquivo de saída serão informadas as linhas referentes às questões acima citadas. Em cada linha o
número da questão será separado da resposta por meio do caractere dois pontos (:).