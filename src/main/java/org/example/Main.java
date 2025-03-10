package org.example;
import org.example.controller.RequestController;
import java.io.IOException;
import java.util.Scanner;

/**
 * Dentro da Main terão os métodos responsáveis por
 * manter o menu de interação com o usuário e identificação
 * do tipo de pagamento que será realizado.
 */
public class Main {
    // Menu principal
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        int opcao=5;
        do {
            System.out.println("1 - Chamar pagamento\n0 - Sair\n\nDigite a opção desejada: ");
            opcao = scan.nextInt();
            switch (opcao) {
                case 1:
                    option(scan);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
        scan.close();
    }

    //Metodo do segundo menu que vai solicitar ao usuário o tipo de pagamento.
    public static void option(Scanner scan) throws Exception {
        RequestController request = new RequestController();
        int opcao = 5;
        do {
            System.out.println("1 - DEBITO\n2 - CREDITO\n0 - SAIR");
            System.out.print("Escolha a opcao:");
            opcao = scan.nextInt();
            switch (opcao) {
                case 1:
                    debito(request, scan);
                    return;
                case 2:
                    credito(request, scan);
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao!= 0);
    }

    // Metodo responsável por chamar o pagamento de débito.
    public static void debito(RequestController request, Scanner scan) throws IOException, InterruptedException {
        String paymentMethod = "CARD";
        String paymentType = "DEBIT";
        String paymentMethodSubType = "FULL_PAYMENT";
        System.out.println("Digite o valor: "); double value = scan.nextDouble();

        request.chamaPagamento(value, paymentMethod, paymentType, paymentMethodSubType);
    }

    // Metodo responsável por chamar o pagamento de crédito.
    public static void credito(RequestController request, Scanner scan) throws IOException, InterruptedException {
        String paymentMethod = "CARD";
        String paymentType = "CREDIT";
        String paymentMethodSubType = "";
        double value = 0f;
        System.out.print("1 - A VISTA\n2 - PARCELADO\nEscolha a opcao: "); int option = scan.nextInt();

        if (option != 1 && option != 2) {
            System.out.println("Opção inválida!");
        } else if (option == 1) {
            paymentMethodSubType = "FULL_PAYMENT";
            System.out.println("Digite o valor: "); value = scan.nextDouble();
            request.chamaPagamento(value, paymentMethod, paymentType, paymentMethodSubType);
        } else {
            paymentMethodSubType = "FINANCED_NO_FEES";
            System.out.println("Digite o valor: "); value = scan.nextDouble();
            request.chamaPagamento(value, paymentMethod, paymentType, paymentMethodSubType);
        }
    }
}