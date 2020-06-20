package edu.psu.ist;
/*
Project: Lab 9
Purpose Details: Pizza ordering application
Course: IST 242
Author: Wenhua Lian
Date Developed: 6/11/2020
Last Date Changed: 6/14/2020
Rev: 7
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private int cCount;
    private static Scanner scnr = new Scanner(System.in);
    public static void main(String[] args) {

        Main main = new Main();

        final char EXIT_CODE = 'E';
        final char CUST_CODE = 'C';
        final char MENU_CODE = 'M';
        final char ORDE_CODE = 'O';
        final char TRAN_CODE = 'T';
        final char CUST_PRNT = 'P';
        final char HELP_CODE = '?';
        char userAction;
        final String PROMPT_ACTION = "Add 'C'ustomer, 'P'rint Customer, List 'M'enu, Add 'O'rder, List 'T'ransaction or 'E'xit: ";
        ArrayList<Customer> cList = new ArrayList<>();
        ArrayList<Menu> mList = new ArrayList<>();
        ArrayList<Order> oList = new ArrayList<>();
        ArrayList<Transaction> tList = new ArrayList<>();

        Order order1 = new Order(1);
        Transaction trans1 = new Transaction(1, order1,PaymentType.cash);

        Menu menu1 = new Menu(1, "Plain",10);
        Menu menu2 = new Menu(2, "Meat",15);
        Menu menu3 = new Menu(3, "Extra",13);
        Menu menu4 = new Menu(4, "Veg", 11);

        mList.add(menu1);
        mList.add(menu2);
        mList.add(menu3);
        mList.add(menu4);

        oList.add(order1);
        tList.add(trans1);

        userAction = getAction(PROMPT_ACTION);

        while (userAction != EXIT_CODE) {
            switch(userAction) {
                case CUST_CODE : cList.add(main.addCustomer());
                    break;
                case CUST_PRNT : Customer.printCustomer(cList);
                    break;
                case MENU_CODE : Menu.listMenu(mList);
                    break;
                case ORDE_CODE :
                    System.out.print("Enter Customer ID : ");
                    int cid = scnr.nextInt();
                    if (cid<cList.size()){
                        ArrayList<Menu>cMenu=selectMenu(mList);
                        Order.addOrders(order1,cList.get(cid),cMenu);
                        oList.add(order1);
                        trans1=Payment(order1);
                        tList.add(trans1);
                    }
                    break;
                case TRAN_CODE : Transaction.listTransactions(tList);
                    break;
            }

            userAction = getAction(PROMPT_ACTION);
        }
    }



    private static Transaction Payment(Order order1) {
        double total = 0;
        double amount;
        System.out.println("Bill is");
        for (Menu menu : order1.getMenuItem()){
            System.out.print(menu.getmenuItem());
            System.out.print("Quantity: ");
            System.out.println(menu.getQuantity());
            System.out.print("amount: ");
            amount = menu.getQuantity() * menu.getItemPrice();
            total = total +amount;
            System.out.println(amount);
        }
        System.out.print("Total is: ");
        System.out.println(total);
        int choose;
        Transaction t;
        while (true){
            System.out.print("Choose the way to pay: ");
            System.out.println("Choose 1 would be Cash");
            System.out.println("Choose 2 would be Card");
            choose = scnr.nextInt();
            if (choose==1){
                t=new Transaction(order1.getorderId(),order1,PaymentType.cash);
                return t;
            }
            else if (choose==2){
                t=new Transaction(order1.getorderId(),order1,PaymentType.credit);
                return t;
            }
        }
    }

    public static ArrayList<Menu> selectMenu(ArrayList<Menu> menus){
        System.out.println("Select menu (by ID): (Press 0 to finalize)");
        for (Menu menu : menus)
            System.out.println("'" + menu.getmenuId() + "' for " + menu.getmenuItem());
        int flag;
        ArrayList<Menu> menus1 = new ArrayList<>();
        while(true) {
            flag = scnr.nextInt();
            if(flag == 0)
                break;
            System.out.print("Add quantity :");
            int quantity = scnr.nextInt();
            Menu item = menus.get(flag-1);
            item.setQuantity(quantity);
            menus1.add(item);
        }
        return menus1;
    }

    public static char getAction(String prompt) {
        Scanner scnr = new Scanner(System.in);
        String answer = "";
        System.out.println(prompt);
        answer = scnr.nextLine().toUpperCase() + " ";
        char firstChar = answer.charAt(0);
        return firstChar;
    }

    public Customer addCustomer(){
        Customer cust = new Customer(cCount++);
        System.out.println("Please Enter your Name: ");
        cust.setCustomerName(scnr.nextLine());
        System.out.println("Please Enter your Phone: ");
        cust.setCustomerPhoneNumber(scnr.nextLine());
        return cust;
    }
}
