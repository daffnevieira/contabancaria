package conta;

import java.io.IOException;
import java.util.Scanner;

import conta.controller.ContaController;
import conta.model.ContaCorrente;
import conta.model.ContaPoupanca;
import conta.util.Cores;

public class Menu {
    public static void main(String[] args) {
        ContaController contas = new ContaController();
        Scanner sc = new Scanner(System.in);

        int opcao, numero, agencia, tipo, aniversario, numeroDestino;
        String titular;
        float saldo, limite, valor;

        System.out.println("\nCriar Contas\n");

        ContaCorrente cc1 = new ContaCorrente(contas.gerarNumero(), 123, 1, "João da Silva", 1000f, 100.0f);
        contas.cadastrar(cc1);

        ContaCorrente cc2 = new ContaCorrente(contas.gerarNumero(), 124, 1, "Maria da Silva", 2000f, 100.0f);
        contas.cadastrar(cc2);

        ContaPoupanca cp1 = new ContaPoupanca(contas.gerarNumero(), 125, 2, "Mariana dos Santos", 4000f, 12);
        contas.cadastrar(cp1);

        ContaPoupanca cp2 = new ContaPoupanca(contas.gerarNumero(), 125, 2, "Juliana Ramos", 8000f, 15);
        contas.cadastrar(cp2);

        contas.listarTodas();

        while (true) {
            System.out.println(Cores.TEXT_PURPLE_BOLD_BRIGHT + Cores.ANSI_WHITE_BACKGROUND);
            System.out.println("*****************************************************");
            System.out.println("                                                     ");
            System.out.println("                BANCO DO BRAZIL COM Z                ");
            System.out.println("                                                     ");
            System.out.println("*****************************************************");
            System.out.println("                                                     ");
            System.out.println("            1 - Criar Conta                          ");
            System.out.println("            2 - Listar todas as Contas               ");
            System.out.println("            3 - Buscar Conta por Numero              ");
            System.out.println("            4 - Atualizar Dados da Conta             ");
            System.out.println("            5 - Apagar Conta                         ");
            System.out.println("            6 - Sacar                                ");
            System.out.println("            7 - Depositar                            ");
            System.out.println("            8 - Transferir valores entre Contas      ");
            System.out.println("            9 - Sair                                 ");
            System.out.println("                                                     ");
            System.out.println("*****************************************************");
            System.out.println("Entre com a opção desejada:                          ");
            System.out.println("                                                     ");

            opcao = sc.nextInt();

            if (opcao == 9) {
                System.out.println("\nBanco do Brazil com Z - O seu Futuro começa aqui!");
                sobre();
                sc.close();
                System.exit(0);
            }

            switch (opcao) {
                case 1:
                    System.out.println(Cores.TEXT_PURPLE_BOLD_BRIGHT + "Criar Conta\n\n");
                    System.out.println("Digite o Numero da Agência: ");
                    agencia = sc.nextInt();
                    System.out.println("Digite o Nome do Titular: ");
                    sc.skip("\\R?");
                    titular = sc.nextLine();

                    do {
                        System.out.println("Digite o Tipo da Conta (1-CC ou 2-CP): ");
                        tipo = sc.nextInt();
                    } while (tipo < 1 && tipo > 2);

                    System.out.println("Digite o Saldo da Conta (R$): ");
                    saldo = sc.nextFloat();

                    switch (tipo) {
                        case 1 -> {
                            System.out.println("Digite o Limite de Crédito (R$): ");
                            limite = sc.nextFloat();
                            contas.cadastrar(new ContaCorrente(contas.gerarNumero(), agencia, tipo, titular, saldo, limite));
                        }
                        case 2 -> {
                            System.out.println("Digite o dia do Aniversario da Conta: ");
                            aniversario = sc.nextInt();
                            contas.cadastrar(new ContaPoupanca(contas.gerarNumero(), agencia, tipo, titular, saldo, aniversario));
                        }
                    }

                    keyPress();
                    break;

                case 2:
                    System.out.println(Cores.TEXT_PURPLE_BOLD_BRIGHT + "Listar todas as Contas\n\n");
                    contas.listarTodas();
                    keyPress();
                    break;

                case 3:
                    System.out.println(Cores.TEXT_PURPLE_BOLD_BRIGHT + "Consultar dados da Conta - por número\n\n");
                    System.out.println("\nDigite o número da conta");
                    numero = sc.nextInt();
                    contas.procurarPorNumero(numero);
                    keyPress();
                    break;

                case 4:
                    System.out.println(Cores.TEXT_PURPLE_BOLD_BRIGHT + "Atualizar dados da Conta\n\n");
                    System.out.println("Digite o número da conta: ");
                    numero = sc.nextInt();

                    var buscaConta = contas.buscarNaCollection(numero);

                    if (buscaConta != null) {
                        tipo = buscaConta.getTipo();
                        System.out.println("Digite o Numero da Agência: ");
                        agencia = sc.nextInt();
                        System.out.println("Digite o Nome do Titular: ");
                        sc.skip("\\R?");
                        titular = sc.nextLine();
                        System.out.println("Digite o Saldo da Conta (R$): ");
                        saldo = sc.nextFloat();

                        switch (tipo) {
                            case 1 -> {
                                System.out.println("Digite o Limite de Crédito (R$): ");
                                limite = sc.nextFloat();
                                contas.atualizar(new ContaCorrente(numero, agencia, tipo, titular, saldo, limite));
                            }
                            case 2 -> {
                                System.out.println("Digite o dia do Aniversario da Conta: ");
                                aniversario = sc.nextInt();
                                contas.atualizar(new ContaPoupanca(numero, agencia, tipo, titular, saldo, aniversario));
                            }
                            default -> System.out.println("Tipo de conta inválido!");
                        }
                    } else {
                        System.out.println("A Conta não foi encontrada!");
                    }
                    keyPress();
                    break;

                case 5:
                    System.out.println(Cores.TEXT_PURPLE_BOLD_BRIGHT + "Apagar a Conta\n\n");
                    System.out.println("Digite o número da conta: ");
                    numero = sc.nextInt();
                    contas.deletar(numero);
                    keyPress();
                    break;

                case 6:
                    System.out.println(Cores.TEXT_PURPLE_BOLD_BRIGHT + "Saque\n\n");
                    System.out.println("Digite o número da conta: ");
                    numero = sc.nextInt();
                    do {
                        System.out.println("Digite o valor do saque: ");
                        valor = sc.nextFloat();
                    } while (valor <= 0);

                    contas.sacar(numero, valor);
                    keyPress();
                    break;

                case 7:
                    System.out.println(Cores.TEXT_PURPLE_BOLD_BRIGHT + "Depósito\n\n");
                    System.out.println("Digite o Numero da conta: ");
                    numero = sc.nextInt();

                    do {
                        System.out.println("Digite o Valor do Depósito (R$): ");
                        valor = sc.nextFloat();
                    } while (valor <= 0);

                    contas.depositar(numero, valor);
                    keyPress();
                    break;

                case 8:
                    System.out.println(Cores.TEXT_PURPLE_BOLD_BRIGHT + "Transferência entre Contas\n\n");
                    System.out.println("Digite o Numero da Conta de Origem: ");
                    numero = sc.nextInt();
                    System.out.println("Digite o Numero da Conta de Destino: ");
                    numeroDestino = sc.nextInt();

                    do {
                        System.out.println("Digite o Valor da Transferência (R$): ");
                        valor = sc.nextFloat();
                    } while (valor <= 0);

                    contas.transferir(numero, numeroDestino, valor);
                    keyPress();
                    break;

                default:
                    System.out.println(Cores.TEXT_RED_BOLD + "\nOpção Inválida!\n");
                    break;
            }
        }
    }

    public static void sobre() {
        System.out.println("\n*********************************************************");
        System.out.println("Projeto Desenvolvido por: Daffne Vieira Rodrigues");
        System.out.println("Generation Brasil - daffner@generation.org");
        System.out.println("github.com/conteudoGeneration");
        System.out.println("*********************************************************");
    }

    public static void keyPress() {
        try {
            System.out.println(Cores.TEXT_RESET + "\n\nPressione Enter para Continuar...");
            System.in.read();
        } catch (IOException e) {
            System.out.println("Você pressionou uma tecla diferente de enter!");
        }
    }
}
