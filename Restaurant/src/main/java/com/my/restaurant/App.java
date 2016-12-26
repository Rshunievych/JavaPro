package com.my.restaurant;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            emf = Persistence.createEntityManagerFactory("Restaurant");
            em = emf.createEntityManager();

            while (true) {
                System.out.println("------------------------------");
                System.out.println("1: Add dish");
                System.out.println("2: Select by price");
                System.out.println("3: See all discounts");
                System.out.println("4: Choose 1 kg meal");
                System.out.println("5: Show the menu");
                System.out.println("6: Add 100 random dishes");

                String s = sc.nextLine();

                switch (s) {
                    case "1":
                        System.out.println("Enter dish name:");
                        String name = sc.nextLine();

                        System.out.println("Enter price:");
                        double price =  Double.parseDouble(sc.nextLine());

                        System.out.println("Enter weight:");
                        double weight = Double.parseDouble(sc.nextLine());

                        System.out.print("Does it have a discount?");
                        String discount = sc.nextLine();
                        if("yes".equals(discount.toLowerCase())) {
                            addDish(name, price, weight, "yes");
                        }else{
                            addDish(name, price, weight, "no");
                        }
                        break;
                    case "2":
                        System.out.println("Enter minimum price:");
                        double from =  Double.parseDouble(sc.nextLine());

                        System.out.println("Enter maximum price:");
                        double to = Double.parseDouble(sc.nextLine());
                        getByPrice(from, to);
                        break;
                    case "3":
                        getMenu(true);
                        break;
                    case "4":
                        onekgMeal(sc);
                        break;
                    case "5":
                        getMenu(false);
                        break;
                    case "6":
                        addRandomDishes();
                        break;
                    default:
                        return;
                }
            }

        }finally {
            em.close();
            emf.close();
            sc.close();
        }
    }

    private static void addDish(String name, double price, double weight, String discount){
        try{
            em.getTransaction().begin();

            Menu menu = new Menu(name, price, weight, discount);
            em.persist(menu);
            em.getTransaction().commit();
        } catch (Exception ex){
            em.getTransaction().rollback();
        }
    }

    private static void getByPrice(double from, double to){
        Query query = em.createQuery("SELECT m FROM Menu m WHERE m.price>:from AND m.price<=:to", Menu.class);
        query.setParameter("from", from);
        query.setParameter("to", to);

        List<Menu> menu = query.getResultList();

        if(!menu.isEmpty()){
            for (Menu m : menu)
                System.out.println(m);
        }else
            System.out.println("Sorry, no such dishes");
    }

    private static void getMenu(boolean discount){
        Query query;

        if(discount){
            query = em.createNamedQuery("Menu.discount", Menu.class);
            query.setParameter("discount", "yes");
        }else{
            query = em.createNamedQuery("Menu.all", Menu.class);
        }

        List<Menu> menu = query.getResultList();

        if(!menu.isEmpty()){
            for (Menu m : menu)
                System.out.println(m);
        }else
            System.out.println("Sorry, no discounts");
    }

    public static void onekgMeal(Scanner sc){
        List<Menu> order = new ArrayList<>();
        int totalWeight = 0;

        while(true){
            System.out.println("Enter dish id");
            long id = Long.parseLong(sc.nextLine());

            Query query = em.createQuery("SELECT m FROM Menu m WHERE m.id = :id", Menu.class);
            query.setParameter("id", id);
            Menu menu = (Menu) query.getSingleResult();

            if(totalWeight + menu.getWeight() <= 1000){
                order.add(menu);
                totalWeight += menu.getWeight();
            }else{
                System.out.println("---------------------");
                System.out.println("Your order:");
                for(Menu m : order)
                    System.out.println(m);
                System.out.println("Total weight: " + totalWeight);
                break;
            }
        }
    }

    public static void addRandomDishes(){
        try{
            em.getTransaction().begin();

            for(int i = 0; i < 100; i++){
                if(i % 10 == 0) {
                    Menu menu = new Menu("Dish-" + i, 100 + 50 * (i % 10), 200 + 25 * (i % 3), "yes");
                    em.persist(menu);
                }else {
                    Menu menu = new Menu("Dish-" + i, 100 + 50 * (i % 10), 200 + 25 * (i % 3), "no");
                    em.persist(menu);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex){
            em.getTransaction().rollback();
        }
    }
}
