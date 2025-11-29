import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // 1. 1'den 10000'e kadar olan sayıları ArrayList'e ekle
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            numbers.add(i);
        }

        // 2. Sayıları 4 eşit parçaya ayır
        List<Integer> part1 = numbers.subList(0, 2500);
        List<Integer> part2 = numbers.subList(2500, 5000);
        List<Integer> part3 = numbers.subList(5000, 7500);
        List<Integer> part4 = numbers.subList(7500, 10000);

        // 3. Ortak tek ve çift sayılar ArrayList'leri
        List<Integer> evenNumbers = Collections.synchronizedList(new ArrayList<>());
        List<Integer> oddNumbers = Collections.synchronizedList(new ArrayList<>());

        // 4. Thread sınıfını tanımla
        class NumberProcessor extends Thread {
            private List<Integer> list;

            public NumberProcessor(List<Integer> list) {
                this.list = list;
            }

            @Override
            public void run() {
                for (int num : list) {
                    if (num % 2 == 0) {
                        evenNumbers.add(num);
                    } else {
                        oddNumbers.add(num);
                    }
                }
            }
        }

        // 5. 4 Thread oluştur ve başlat
        Thread t1 = new NumberProcessor(part1);
        Thread t2 = new NumberProcessor(part2);
        Thread t3 = new NumberProcessor(part3);
        Thread t4 = new NumberProcessor(part4);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // 6. Threadlerin bitmesini bekle
        t1.join();
        t2.join();
        t3.join();
        t4.join();

        // 7. Sonuçları yazdır
        System.out.println("Çift sayı sayısı: " + evenNumbers.size());
        System.out.println("Tek sayı sayısı: " + oddNumbers.size());

        // İstersen listelerin ilk 10 elemanını da görebilirsin
        System.out.println("Çift sayılardan ilk 10: " + evenNumbers.subList(0, 10));
        System.out.println("Tek sayılardan ilk 10: " + oddNumbers.subList(0, 10));
    }
}
