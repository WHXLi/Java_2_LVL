package lesson3;

import java.util.LinkedList;

public class MyTelephoneBook {
    private LinkedList<Human> telephoneBook = new LinkedList<>();

    public void arrayCreate() {
        telephoneBook.add(new Human("Сазонова", "8 (916) xxx - 01 - xx"));
        telephoneBook.add(new Human("Сазонова", "8 (916) xxx - 02 - xx"));
        telephoneBook.add(new Human("Сазонова", "8 (916) xxx - 03 - xx"));
        telephoneBook.add(new Human("Сазонова", "8 (916) xxx - 04 - xx"));
        telephoneBook.add(new Human("Сазонова", "8 (916) xxx - 05 - xx"));
        telephoneBook.add(new Human("Сазонова", "8 (916) xxx - 06 - xx"));
        telephoneBook.add(new Human("Сазонова", "8 (916) xxx - 06 - xx"));
        telephoneBook.add(new Human("Сазонова", "8 (916) xxx - 06 - xx"));

        System.out.println(telephoneBook);
        System.out.println();
    }

    public void arraySort() {

    }

    public void arraySearch(String name){

    }
}