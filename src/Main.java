import com.sun.source.doctree.RawTextTree;

import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void mostrarInicio() {
        System.err.println("*******************");
        System.err.println(" JOGO RPG ");
        System.err.println("*******************");
    }

    public static int escolhaPersonagem() {

        Scanner sc = new Scanner(System.in);
        int opcaoClasse = 0;

        do {

            System.out.println("Escolha sua classe :");
            System.out.println("1- Guerreiro");
            System.out.println("2- Arqueiro");
            System.out.println("3- Gremista");
            System.out.println("4- Mago");
            opcaoClasse = sc.nextInt();

        } while (opcaoClasse <= 0 || opcaoClasse >= 5);

        return opcaoClasse;
    }

    public static void mostrarMenu() {

        System.out.println("Inicio da jornada");
        System.out.println("1-Explorar");
        System.out.println("2-Ver Status");
        System.out.println("3-Recuperar");
        System.out.println("4-Mostrar monstros derrotados");
        System.out.println("0-Sair");
    }

    public static void mostrarStatus(Personagem personagem) {
        System.out.println(" Olá " + personagem.nome);
        System.out.println(" Ataque : " + personagem.ataque);
        System.out.println(" Defesa : " + personagem.defesa);
        System.out.println(" Vida : " + personagem.vida);
        System.out.println(" Energia : " + personagem.energia);
        System.out.println(" Armadura : " + (personagem.temArmadura ? "Sim" : "Não"));
        System.out.println(" Arma : " + (personagem.temArma ? "Sim" : "Não"));

    }

    public static Personagem definirStatusPersonagem(int opcao, Personagem personagem) {

        if (opcao == 1) {
            personagem.vida = 120;
            personagem.ataque = 80;
            personagem.defesa = 50;
        } else if (opcao == 2) {
            personagem.vida = 100;
            personagem.ataque = 70;
            personagem.defesa = 40;
        } else if (opcao == 3) {
            personagem.vida = 80;
            personagem.ataque = 50;
            personagem.defesa = 60;
            personagem.almaCopeira = true;
        } else {
            personagem.vida = 90;
            personagem.ataque = 40;
            personagem.defesa = 20;
        }

        personagem.vidaMaxima = personagem.vida;
        return personagem;

    }

    public static void recuperar(Personagem personagem) {

        try {
            personagem.vida += 10;


            if (personagem.vida >= personagem.vidaMaxima) {
                personagem.vida = personagem.vidaMaxima;
                System.out.println("Vida maxima atingida !");
            } else {
                System.out.println("Vida restaurada !");
            }

            System.out.println("Vamos recuperar sua energia..");

            Thread.sleep(2000);

            System.out.println("Ainda estamos recuperando");

            Thread.sleep(3000);

            System.out.println("Energia Recuperada !!");

            personagem.energia += 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void itemRaro(Personagem personagem) {
        System.out.println("Sortudinho, ganhou um item raro");

        Random random = new Random();
        int itemSorteado = random.nextInt(2) + 1;

        if (itemSorteado == 1) {

            if (personagem.temArmadura) {
                System.out.println("Você já tem, rala");
            } else {
                personagem.temArmadura = true;
            }

        } else {

            if (personagem.temArma) {
                System.out.println("Você ja ta armado !");
            } else {
                personagem.temArma = true;
            }
        }
    }

    public static int D20() {
        Random random = new Random();
        return random.nextInt(20) + 1;
    }

    public static int explorar() {

        Random random = new Random();
        int sorteio = random.nextInt(100) + 1;

        if (sorteio <= 40) {
            return 1; //explorar
        } else if (sorteio <= 60) {
            return 2; //Energia
        } else if (sorteio <= 95) {
            return 3; //vidinha
        } else {
            return 4; //item raro
        }

           /*
                 1 - Encontrou um inimigo 40
                 2 - Encontrou uma energia 20
                 3 - Segue a sua vidinha 35
                 4 - Encontrou item raro 5
                 */

    }

    public static int combater(Personagem personagem, Scanner sc) {


        personagem.almaCopeiraDisponivel = personagem.almaCopeira;
        Random random = new Random();
        // primeira nova função: item aumenta ataque/defesa em 1,5x por turno enquanto em combate.

        double ataqueAtual = personagem.ataque;
        double defesaAtual = personagem.defesa;

        int monstroEspecial = random.nextInt(20) + 1;
        int vidaMonstro = random.nextInt(80) + 40 + monstroEspecial;
        int ataqueMonstro = random.nextInt(30) + 5;

        if (vidaMonstro >= 100) {
            System.out.println("Você encontrou o Marcelo Goblin!");
        }

        System.out.println("Seu inimigo tem :");
        System.out.println("Vida: " + vidaMonstro);
        System.out.println("Ataque: " + ataqueMonstro);

        while (vidaMonstro > 0 && personagem.vida > 0) {


            if (personagem.temArmadura) {
                defesaAtual *= 1.5;
                System.out.println("Sua armadura fortalece seu ataque !");
            }

            if (personagem.temArma) {
                ataqueAtual *= 1.5;
                System.out.println("Sua arma fortalece seu ataque !");
            }

            int sorteioDado = D20();

            if (sorteioDado == 19 && personagem.almaCopeiraDisponivel) {
                if (ativarAlmaCopeira(sc)) {
                    sorteioDado = 20;
                    personagem.almaCopeiraDisponivel = false;
                    System.out.println("A ALMA COPEIRA TRANSFORMOU 19 EM 20!");
                }
            }
            if (sorteioDado <= 7) {
                System.out.println("Você errou !");
            } else if (sorteioDado <= 14) {
                vidaMonstro -= (int) ataqueAtual / 2;
                System.out.println("Metade do dano");
            } else if (sorteioDado <= 19) {
                vidaMonstro -= (int) ataqueAtual;
                System.out.println("Ataque total !");
            } else {
                vidaMonstro -= (int) ataqueAtual * 2;
                System.out.println("ATAQUE CRITICO");
            }


            if (vidaMonstro > 0) {

                sorteioDado = D20();

                if (sorteioDado <= 7) {
                    System.out.println("O Goblin errou !");
                } else if (sorteioDado <= 14) {
                    personagem.vida -= ataqueMonstro / 2;
                    System.out.println("Você recebeu a metade do dano");
                } else if (sorteioDado <= 19) {
                    personagem.vida -= ataqueMonstro;
                    System.out.println("Você recebeu o ataque total !");
                } else if (sorteioDado == 20) {
                    //segunda adição, destruir armadura ou arma
                    if (personagem.temArma) {
                        personagem.temArma = false;
                        System.out.println("O GOBLIN DESTRUIU SUA ARMA!");
                    } else if (personagem.temArmadura) {
                        personagem.temArmadura = false;
                        System.out.println("O GOBLIN DESTRUIU SUA ARMADURA!");
                    } else {
                        personagem.vida -= ataqueMonstro * 2;
                        System.out.println("O GOBLIN DEU UM ATAQUE CRÍTICO!");
                    }
                }
            }
            if (personagem.vida <= 0 && personagem.almaCopeiraDisponivel) {

                System.out.println("VOCÊ ESTÁ PRESTES A MORRER!");

                if (ativarAlmaCopeira( sc)) {
                    personagem.vida = 20;
                    personagem.almaCopeiraDisponivel = false;
                    System.out.println("A ALMA COPEIRA IMPEDIU SUA MORTE! +20 DE VIDA!");
                }
            }
            System.err.println("Vida Personagem: " + personagem.vida);
            System.err.println("Vida Monstro: " + vidaMonstro);
            }



        if (vidaMonstro <= 0) {
            System.out.println("Você matou o Goblin! ");
            return 1;
        } else {
            System.out.println("Você se foi ");
            return 0;
        }
    }

    public static void EncontrarEnergia(Personagem personagem) {
        System.out.println("Você encontrou um bau de energia");
        if (personagem.energia + 1 < 10) {
            personagem.energia += 1;
        } else {
            System.out.println("Energia cheia !");
        }
    }

    public static int Acharmarcelo(Personagem personagem, int opcaoJogo, Scanner sc) {
        System.out.println("Você encontrou um Goblin");

        System.out.println("Você quer fazer oque ?");
        System.out.println("1 - Ataque");
        System.out.println("2 - Defender");
        System.out.println("3 - Fugir");
        int opcaoLuta = sc.nextInt();

        if (opcaoLuta == 1) {

            //Lutar
            int resultado = combater(personagem, sc);
            return resultado;

        } else if (opcaoLuta == 2) {
            EncontrarEnergia(personagem);

        } else if (opcaoLuta == 3) {
            System.out.println("Correu de um mísero goblin, seu covarde!");

        } else {
            System.out.println("Opção Invalida");
        }
        return 0;
    }

    //terceira adição: almaCopeira
    public static boolean ativarAlmaCopeira(Scanner sc) {

        System.out.println("A ALMA COPEIRA ESTÁ DISPONÍVEL!");
        System.out.println("Deseja ativá-la?");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");

        int opcao = sc.nextInt();

        return opcao == 1;
    }



        public static void main (String[]args) throws InterruptedException {

            int monstrosDerrotados = 0;
            mostrarInicio();

            Scanner sc = new Scanner(System.in);


            Personagem personagem = new Personagem();
            System.out.println("Informe o nome do seu personagem");
            personagem.nome = sc.next();

            System.out.println("Seja bem-vindo " + personagem.nome + " ! ");

            int opcao = escolhaPersonagem();

            personagem = definirStatusPersonagem(opcao, personagem);

            int opcaoJogo = -1;


            while (opcaoJogo != 0) {

                mostrarMenu();
                opcaoJogo = sc.nextInt();

                if (opcaoJogo == 1) {
                    int opcaoExplorar = explorar();

                    if (opcaoExplorar == 1) {

                        monstrosDerrotados += Acharmarcelo(personagem, opcaoJogo, sc);

                        if (personagem.vida <= 0) {
                            System.out.println("VOCE MORREU !!!");
                            break;
                        }

                    } else if (opcaoExplorar == 2) {

                    } else if (opcaoExplorar == 3) {
                        System.out.println("Você deve seguir sua vidinha");
                    } else if (opcaoExplorar == 4) {
                        itemRaro(personagem);
                    }
                } else if (opcaoJogo == 2) {

                    mostrarStatus(personagem);

                } else if (opcaoJogo == 3) {

                    recuperar(personagem);
                } else if (opcaoJogo == 4) {
                    System.out.println("Monstros derrotados: " + monstrosDerrotados);
                }


            }

        }
    }