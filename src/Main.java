import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccountServiceImpl service = new AccountServiceImpl();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите своё имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите свою фамилию: ");
        String lastName = scanner.nextLine();
        service.singUp(name, lastName);
        System.out.print("Введите номер карты: ");
        String cardNumber = scanner.nextLine();
        System.out.print("Введите пин код карты: ");
        String pinCode = scanner.nextLine();
        while (true) {
            service.singIn(cardNumber, pinCode);
        }
    }
}