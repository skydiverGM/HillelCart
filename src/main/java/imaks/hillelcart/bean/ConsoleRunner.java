package imaks.hillelcart.bean;


import imaks.hillelcart.entity.Product;
import imaks.hillelcart.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        Cart cart = context.getBean(Cart.class);

        Scanner scanner = new Scanner(System.in);
        String input;

        printMenu();

        while (true) {
            System.out.print("\nВведіть команду: ");
            input = scanner.nextLine();
            String[] parts = input.split("\\s+");

            if (parts.length == 0) continue;

            String command = parts[0].toLowerCase();

            switch (command) {
                case "add":
                    if (parts.length < 2) {
                        System.out.println("Вкажіть id товару для додавання.");
                        break;
                    }
                    try {
                        Long id = Long.parseLong(parts[1]);
                        Product product = productRepository.findById(id);
                        if (product != null) {
                            cart.addProduct(product);
                            System.out.println("Товар додано до кошика: " + product);
                        } else {
                            System.out.println("Товар з id " + id + " не знайдено.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Невірний формат id.");
                    }
                    break;

                case "remove":
                    if (parts.length < 2) {
                        System.out.println("Вкажіть id товару для видалення.");
                        break;
                    }
                    try {
                        Long id = Long.parseLong(parts[1]);
                        cart.removeProductById(id);
                        System.out.println("Спроба видалення товару з id " + id + " завершена.");
                    } catch (NumberFormatException e) {
                        System.out.println("Невірний формат id.");
                    }
                    break;

                case "list":
                    System.out.println("Доступні товари:");
                    productRepository.findAll().forEach(System.out::println);
                    break;

                case "cart":
                    cart.displayCart();
                    break;

                case "help":
                    printMenu();
                    break;

                case "exit":
                    System.out.println("Вихід з програми.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Невідома команда. Введіть 'help' для перегляду списку команд.");
            }
        }
    }

    private void printMenu() {
        System.out.println("Список доступних команд:");
        System.out.println("  add <id>    - додати товар до кошика");
        System.out.println("  remove <id> - видалити товар з кошика");
        System.out.println("  list        - переглянути список усіх товарів");
        System.out.println("  cart        - переглянути вміст кошика");
        System.out.println("  help        - показати це меню");
        System.out.println("  exit        - завершити роботу програми");
    }
}
