package com.GoldenMine.보지마세여;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class BankAccount {
    private String account;
    private BigInteger money; // 2^64 이상

    public static void main(String[] args) {

        BankAccount 홍길동 = new BankAccount("1111-1111-1111-1111");
        BankAccount 김철수 = new BankAccount("1111-1111-1111-1112");
        홍길동.addMoney(5000);
        김철수.addMoney(5000);

        홍길동.subMoney(99999); // 처리 안됨

        홍길동.withdraw(김철수, 2500);

        System.out.println("홍길동: " + 홍길동.getMoneyInBig() + "원");
        System.out.println("김철수: " + 김철수.getMoneyInBig() + "원");

        System.out.println("==========  테스트 끝  ==========\n\n");

        LinkedHashMap<String, BankAccount> data = new LinkedHashMap<>();

        Scanner scan = new Scanner(System.in);

        BankAccount currentAccount = null;

        while(true) {
            System.out.print("만들기 1, 계좌설정 2, 입금 3, 출금 4, 송금 5, 계좌리스트 6, 종료 7 : ");
            int scanned = scan.nextInt();


            switch(scanned) {
                case 1:
                    System.out.print("계좌번호: ");
                    String a = scan.next();
                    if(data.containsKey(a)) {
                        System.out.println("이미 계좌 존재함");
                    } else {
                        data.put(a, new BankAccount(a));
                        System.out.println("완료");
                    }
                    break;

                case 2:
                    System.out.print("계좌번호: ");
                    String b = scan.next();
                    if(data.containsKey(b)) {
                        currentAccount = data.get(b);
                        System.out.println("완료");
                    } else {
                        System.out.println("해당하는 계좌 없음;;");
                    }
                    break;
                case 3:
                    System.out.print("돈: ");
                    currentAccount.addMoney(new BigInteger(scan.next()));
                    break;
                case 4:
                    System.out.print("돈: ");
                    currentAccount.subMoney(new BigInteger(scan.next()));
                    break;
                case 5:
                    System.out.print("송금할 계좌: ");
                    String account = scan.next();
                    if(data.containsKey(account)) {
                        System.out.print("돈: ");
                        currentAccount.withdraw(data.get(account), new BigInteger(scan.next()));
                        System.out.println("완료");
                    } else {
                        System.out.println("해당하는 계좌 없음");
                    }
                    break;

                case 6:
                    for(String s : data.keySet()) {
                        System.out.println(s + ": " + data.get(s).getMoneyInBig() + "원");
                    }
                    break;

                case 7:
                    System.exit(0);
                    break;
            }
        }
    }

    public BankAccount(String account) {
        this.account = account;

        money = new BigInteger("0");
    }

    public BankAccount(String account, int money) {
        this.account = account;

        this.money = new BigInteger(String.valueOf(money));
    }

    public BankAccount(String account, BigInteger money) {
        this.account = account;

        this.money = money;
    }

    public void addMoney(int money) {
        addMoney(new BigInteger(String.valueOf(money)));
    }

    public void addMoney(BigInteger money) {
        synchronized(this.money) {
            this.money = this.money.add(money);
        }
        System.out.println(account + ", 입금 완료! : " + this.money.toString());
    }

    public void subMoney(int money) {
        subMoney(new BigInteger(String.valueOf(money)));
    }

    public void subMoney(BigInteger money) {
        BigInteger bMoney = new BigInteger(String.valueOf(money));
        if(checkMoney(bMoney)) {
            synchronized(this.money) {
                this.money = this.money.subtract(money);
            }
            System.out.println(account + ", 출금 완료! : " + this.money.toString());
        } else {
            System.out.println(account + ", 돈이 없어");
            //throw new RuntimeException("거지");
        }
    }

    public boolean checkMoney(BigInteger bMoney) {
        return this.money.compareTo(bMoney)>=0;
    }

    public void withdraw(BankAccount account, int money) {
        withdraw(account, new BigInteger(String.valueOf(money)));
    }

    public void withdraw(BankAccount account, BigInteger money) {
        if(checkMoney(money)) {
            synchronized (this.money) {
                synchronized (account.money) {
                    subMoney(money);
                    account.addMoney(money);
                }
            }
            System.out.println(this.account + ", 송금 완료! : " + this.money.toString() + ", 상대: " + account.money.toString());
        } else {
            System.out.println(this.account + ", 돈이 없어");
            //throw new RuntimeException("거지");
        }
    }

    public BigInteger getMoneyInBig() {
        return money;
    }

    public int getMoney() {
        return money.intValue();
    }
}
