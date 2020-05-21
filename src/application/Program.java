package application;

import entities.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Program {
    public static void main(String args[]) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter full file path: "); // /Users/lhserafim/IdeaProjects/StreamExResolvido-java/in.txt
        String path = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            List<Product> list = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Product(fields[0], Double.parseDouble(fields[1])));
                line = br.readLine();
            }

            // transformo minha lista em stream - uso de pipeline
            // aplico map (aplica uma função em todos os elementos da minha lista)
            // somo (reduce) e divido pela lista.size
            double avg = list.stream()
                    .map(x -> x.getPrice())
                    .reduce(0.0,(x,y) -> x + y) / list.size(); // precisa colocar 0.0 pois é double

            System.out.println("Average price: " + String.format("%.2f",avg));

            // Criei um comparator para comparar os Strings
            // Coloca os Strings em ordem
            Comparator<String> comp = (s1,s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
            List<String> names = list.stream() //transforma em stream
                    .filter(x -> x.getPrice() < avg) //pega os preços menores que a média
                    .map(x -> x.getName()) // pega os nomes
                    .sorted(comp.reversed()) //ordena a comparação
                    .collect(Collectors.toList());  // transforma em lista

            names.forEach(System.out::println);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sc.close();
    }
}
