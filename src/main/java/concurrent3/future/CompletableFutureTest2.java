package concurrent3.future;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
public class CompletableFutureTest2 {

    private static final Random random = new Random();

    private static final List<String> shops = Arrays.asList("JD", "TMall", "TaoBao", "PDD");

    private static double getPrice() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return random.nextDouble() * 10 + 10;
    }

    public static List<String> getPriceList() {
        return shops.stream()
                .map(shop -> String.format("%s price: %.2f", shop, getPrice()))
                .collect(Collectors.toList());
    }

    public static List<String> getPriceListParallel() {
        return shops.parallelStream()
                .map(shop -> String.format("%s price: %.2f", shop, getPrice()))
                .collect(Collectors.toList());
    }

    public static List<String> getPriceListAsync() {
        List<CompletableFuture<String>> futures =
                shops.stream().map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price: %.2f", shop, getPrice())
                )).collect(Collectors.toList());

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        long start = System.nanoTime();
        List<String> priceList = getPriceListAsync();
        long end = System.nanoTime();
        log.info("{}", priceList);
        log.info((end - start) / 1_000_000 + "ms");
    }
}
