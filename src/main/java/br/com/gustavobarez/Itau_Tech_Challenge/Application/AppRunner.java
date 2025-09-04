package br.com.gustavobarez.Itau_Tech_Challenge.Application;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.gustavobarez.Itau_Tech_Challenge.Asset.Asset;
import br.com.gustavobarez.Itau_Tech_Challenge.Position.GlobalPositionDTO;
import br.com.gustavobarez.Itau_Tech_Challenge.Position.Position;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);
    private final ApplicationService applicationService;

    public AppRunner(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMainMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleInvestmentTotal(scanner);
                    break;
                case "2":
                    handleBrokerageFee(scanner);
                    break;
                case "3":
                    handlePositionPerAsset(scanner);
                    break;
                case "4":
                    handleGlobalPosition(scanner);
                    break;
                case "0":
                    logger.info("Encerrando a aplicação...");
                    scanner.close();
                    return;
                default:
                    logger.warn("Opção inválida. Por favor, tente novamente.");
                    break;
            }
            promptEnterKey(scanner);
        }
    }

    private void showMainMenu() {
        System.out.println("\n=========================================================");
        System.out.println("  Selecione a operação que deseja realizar:");
        System.out.println("=========================================================");
        System.out.println("  1 - Calcular Investimento Total por Ativo");
        System.out.println("  2 - Calcular Taxa Total de Corretagem");
        System.out.println("  3 - Consultar Posição por Ativo");
        System.out.println("  4 - Consultar Posição Global Consolidada");
        System.out.println("  0 - Sair");
        System.out.print("\n  Digite sua escolha: ");
    }

    private Long askForUserId(Scanner scanner) {
        System.out.print("  Digite o ID do usuário: ");
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            logger.error("ERRO: O ID do usuário deve ser um número válido.");
            return null;
        }
    }

    private void handleInvestmentTotal(Scanner scanner) {
        logger.info("--> [Teste 1] Calculando Investimento Total por Ativo...");
        Long userId = askForUserId(scanner);
        if (userId == null)
            return;

        try {
            CompletableFuture<Map<Asset, BigDecimal>> future = applicationService.investimentTotalPerAsset(userId);
            Map<Asset, BigDecimal> result = future.get();

            if (result.isEmpty()) {
                logger.info("Resultado: Nenhuma operação de 'COMPRA' encontrada para este usuário.");
            } else {
                result.forEach((asset, total) -> logger.info("Resultado: Ativo: {} ({}) - Total Investido: R$ {}",
                        asset.getCode(), asset.getName(), total));
            }
        } catch (ExecutionException e) {
            logger.error("ERRO ao calcular o investimento: {}", e.getCause().getMessage());
        } catch (InterruptedException e) {
            logger.error("A operação foi interrompida.", e);
            Thread.currentThread().interrupt();
        }
    }

    private void handleBrokerageFee(Scanner scanner) {
        logger.info("--> [Teste 2] Calculando Taxa Total de Corretagem...");
        Long userId = askForUserId(scanner);
        if (userId == null)
            return;

        try {
            CompletableFuture<BigDecimal> future = applicationService.totalBrokerageFee(userId);
            BigDecimal result = future.get();
            logger.info("Resultado: Taxa total de corretagem paga pelo usuário: R$ {}", result);
        } catch (ExecutionException e) {
            logger.error("ERRO ao calcular a corretagem: {}", e.getCause().getMessage());
        } catch (InterruptedException e) {
            logger.error("A operação foi interrompida.", e);
            Thread.currentThread().interrupt();
        }
    }

    private void promptEnterKey(Scanner scanner) {
        System.out.println("\nPressione \"ENTER\" para continuar...");
        scanner.nextLine();
    }
}
