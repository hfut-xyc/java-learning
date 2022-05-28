import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class FutureDemo3 {

    private static final Random random = new Random();

    private static final List<String> shops = Arrays.asList("JD", "TMall", "TaoBao", "PDD");

    private static double getPrice(String product) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return random.nextDouble() * 10 + 10;
    }

    public static List<String> getPriceList(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price: %.2f", shop, getPrice(product)))
                .collect(Collectors.toList());
    }

    public static List<String> getPriceListParallel(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price: %.2f", shop, getPrice(product)))
                .collect(Collectors.toList());
    }

    public static List<String> getPriceListAsync(String product) {
        List<CompletableFuture<String>> futures =
                shops.stream().map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price: %.2f", shop, getPrice(product))
                )).collect(Collectors.toList());

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        long start = System.nanoTime();
        List<String> iphone = getPriceList("iphone");
        long end = System.nanoTime();
        System.out.println(iphone);
        System.out.println((end - start) / 1_000_000 + "ms");
    }
}
