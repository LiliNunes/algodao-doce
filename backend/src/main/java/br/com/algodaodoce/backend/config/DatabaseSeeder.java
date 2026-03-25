package br.com.algodaodoce.backend.config;

import br.com.algodaodoce.backend.model.Product;
import br.com.algodaodoce.backend.model.ProductOption;
import br.com.algodaodoce.backend.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                // Produto 1
                Product p1 = new Product();
                p1.setCategory("Para comer de colher");
                p1.setName("Brigadeiro Crocante");
                p1.setDescription("Casca de chocolate meio amargo com recheio de brigadeiro cremoso de chocolate meio amargo e crocante de Ovomaltine");
                p1.setImage("assets/brigadeiro-crocante.jpeg");
                
                ProductOption o1 = new ProductOption(); o1.setWeight("150g"); o1.setPrice(45.0);
                ProductOption o2 = new ProductOption(); o2.setWeight("300g"); o2.setPrice(85.0);
                p1.setOptions(Arrays.asList(o1, o2));
                repository.save(p1);

                // Produto 2
                Product p2 = new Product();
                p2.setCategory("Para comer de colher");
                p2.setName("Pistache");
                p2.setDescription("Casca de chocolate ao leite e chocolate branco saborizado de pistache com recheio de brigadeiro de pistache");
                p2.setImage("assets/pistache-novo.jpeg");
                
                ProductOption o3 = new ProductOption(); o3.setWeight("150g"); o3.setPrice(49.0);
                ProductOption o4 = new ProductOption(); o4.setWeight("300g"); o4.setPrice(95.0);
                p2.setOptions(Arrays.asList(o3, o4));
                repository.save(p2);

                System.out.println("====== DB POPULADO COM PRODUTOS ======");
            }
        };
    }
}
