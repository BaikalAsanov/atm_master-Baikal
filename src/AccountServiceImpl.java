import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AccountServiceImpl implements AccountService{
    private final AccountDao accountDao = new AccountDao();
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);
    private final Scanner scannerInt = new Scanner(System.in);

    @Override
    public void singUp(String name, String lastName) {
        ArrayList<UserAccount> users = new ArrayList<>();
        int num;
        int code;
        if (name.length() > 1 && lastName.length() > 1) {
            num = random.nextInt(10000000, 99999999);
            code = random.nextInt(1000, 9999);
            String cardNumber = Integer.toString(num);
            String cardPinCode = Integer.toString(code);
            System.out.println("Ваш номер карты: " + cardNumber + "\nВаш пин код карты: " + cardPinCode);
            users.add(new UserAccount(name, lastName, cardNumber, cardPinCode, 0));
            users.add(new UserAccount("Husein", "Obama", "00000000", "0000", 0));
            accountDao.setUserAccounts(users);
        } else {
            System.out.println("Имя и фамилия должны содержать больше одной буквы!!!");
        }
    }

    @Override
    public void singIn(String cardNumber, String pinCode) {
        for (UserAccount a : accountDao.getUserAccounts()) {
            if (cardNumber.equals(a.getCardNumber()) && pinCode.equals(a.getPinCode())) {
                System.out.println("Что бы выбирать действия вводите в консоль их номера.");
                System.out.println("1 Баланс." +
                        "\n2 Депозит." +
                        "\n3 Отправить деньги другу." +
                        "\n4 Снять деньги.");
                System.out.print("Выберите дальнейшие действия: ");
                String move = scanner.nextLine();
                if (move.equals("1")) {
                    System.out.print("Ваш баланс: ");
                    balance(cardNumber, pinCode);
                }
                if (move.equals("2")) {
                    deposit(cardNumber, pinCode);
                }
                if (move.equals("3")) {
                    System.out.print("Введите номер карты друга: ");
                    String friendCode = scanner.nextLine();
                    sendToFriend(friendCode);
                }
                if (move.equals("4")) {
                    System.out.print("Напишите сумму которую хотите вывести кратную 100: ");
                    int amount = scannerInt.nextInt();
                    withdrawMoney(amount);
                }
            }
        }
    }

    @Override
    public void balance(String cardNumber, String pinCode) {
        for (UserAccount u : accountDao.getUserAccounts()) {
            if (u.getCardNumber().equals(cardNumber) && u.getPinCode().equals(pinCode)) {
                System.out.println(u.getBalance());
            }
        }
    }

    @Override
    public void deposit(String cardNumber, String pinCode) {
        System.out.print("Сумма депозита который хотите внести : ");
        int sum = scannerInt.nextInt();
        System.out.print("Счёт на который хотите внести депозит: ");
        String num = scanner.nextLine();
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            if (userAccount.getCardNumber().equals(num)) {
                userAccount.setBalance(+sum);
                System.out.println("Депозит успешно внесён на этот счёт.");
                System.out.println("Баланс на этом счету: " + userAccount.getBalance());
            }
        }
    }

    @Override
    public void sendToFriend(String friendCardNumber) {
        System.out.print("Введите номер своей карты: ");
        String num = scanner.nextLine();
        System.out.print("Ваш баланс: ");
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            if (userAccount.getCardNumber().equals(num)) {
                System.out.println(userAccount.getBalance());
            }
        }
        System.out.print("Введите сумму которую хотите отправить другу: ");
        int sum = scannerInt.nextInt();
        for (UserAccount us : accountDao.getUserAccounts()) {
            if (us.getCardNumber().equals(num)) {
                if (us.getBalance() >= sum) {
                    int balance = us.getBalance() - sum;
                    us.setBalance(balance);
                    System.out.println("Ваш баланс: " + us.getBalance());
                }
            }
        }
        for (UserAccount u : accountDao.getUserAccounts()) {
            if (friendCardNumber.equals(u.getCardNumber())) {
                int balance = u.getBalance() + sum;
                u.setBalance(balance);
                System.out.println("Баланс вашего друга: " + u.getBalance());
            }
        }


    }

    @Override
    public void withdrawMoney(int amount) {
        System.out.print("Введите свой номер карты: ");
        String num = scanner.nextLine();
        System.out.print("Введите свой пин код карты: ");
        String pin = scanner.nextLine();
        if (amount % 1000 == 0) {
            int shtuk = amount / 1000;
            System.out.println("Получить " + amount + " рублей по (" + shtuk + ") 1000 рублёвыми купюрами (нажмите на 1)");
        }
        if (amount % 500 == 0) {
            int shtuk = amount / 500;
            System.out.println("Получить " + amount + " рублей по (" + shtuk + ") 500 рублёвыми купюрами (нажмите на 2)");
        }
        if (amount % 200 == 0) {
            int shtuk = amount / 200;
            System.out.println("Получить " + amount + " рублей по (" + shtuk + ") 200 рублёвыми купюрами (нажмите на 3)");
        }
        if (amount % 100 == 0) {
            int shtuk = amount / 100;
            System.out.println("Получить " + amount + " рублей по (" + shtuk + ") 100 рублёвыми купюрами (нажмите на 4)");
        }
        if (amount % 50 == 0) {
            int shtuk = amount / 50;
            System.out.println("Получить " + amount + " рублей по (" + shtuk + ") 50 рублёвыми купюрами (нажмите на 5)");
        }
        if (amount % 10 == 0) {
            int shtuk = amount / 10;
            System.out.println("Получить " + amount + " рублей по (" + shtuk + ") 10 рублёвыми монетами (нажмите на 6)");
        }
        System.out.print("Введите номер действия: ");
        int move = scannerInt.nextInt();
        for (UserAccount account : accountDao.getUserAccounts()) {
            int balance = account.getBalance() - amount;
            if (move == 1 && account.getCardNumber().equals(num) && account.getBalance() >= amount) {
                account.setBalance(balance);
                System.out.println("Ваш баланс: " + account.getBalance());
                System.out.println("Выведено: " + amount + " рублей по 1000 рублей (" + amount / 1000 + " штук).");
            } else if (move == 2 && account.getCardNumber().equals(num) && account.getBalance() >= amount) {
                account.setBalance(balance);
                System.out.println("Ваш баланс: " + account.getBalance());
                System.out.println("Выведено: " + amount + " рублей по 500 рублей (" + amount / 500 + " штук).");
            } else if (move == 3 && account.getCardNumber().equals(num) && account.getBalance() >= amount) {
                account.setBalance(balance);
                System.out.println("Ваш баланс: " + account.getBalance());
                System.out.println("Выведено: " + amount + " рублей по 200 рублей (" + amount / 200 + " штук).");
            } else if (move == 4 && account.getCardNumber().equals(num) && account.getBalance() >= amount) {
                account.setBalance(balance);
                System.out.println("Ваш баланс: " + account.getBalance());
                System.out.println("Выведено: " + amount + " рублей по 100 рублей (" + amount / 100 + " штук).");
            } else if (move == 5 && account.getCardNumber().equals(num) && account.getBalance() >= amount) {
                account.setBalance(balance);
                System.out.println("Ваш баланс: " + account.getBalance());
                System.out.println("Выведено: " + amount + " рублей по 50 рублей (" + amount / 50 + " штук).");
            } else if (move == 6 && account.getCardNumber().equals(num) && account.getBalance() >= amount) {
                account.setBalance(balance);
                System.out.println("Ваш баланс: " + account.getBalance());
                System.out.println("Выведено: " + amount + " рублей по 10 рублей (" + amount / 10 + " штук).");
            }
        }
    }
}
