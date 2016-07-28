package com.chaoxu;

import com.chaoxu.pojo.Card;
import com.chaoxu.pojo.Person;
import com.chaoxu.util.FactoryUtil;
import org.hibernate.Session;
import org.junit.Test;

/**
 * Created by dell on 2016/7/26.
 */
public class OneToOnetestCase {

    @Test
    public void testSave() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Person person = new Person();
        person.setName("Harry");

        Card card = new Card();
        card.setCardname("VIP 999");
        card.setPerson(person);

        session.save(person);
        session.save(card);

        session.getTransaction().commit();
    }

    @Test
    public void testFindPerson() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Person person = (Person) session.get(Person.class, 15);

        System.out.println(person.getCard().getCardname());


        session.getTransaction().commit();
    }

    @Test
    public void testFindCard() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Card card = (Card) session.get(Card.class, 13);
        System.out.println(card.getPerson().getName());

        session.getTransaction().commit();
    }

    @Test
    public void testDel() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Person person = (Person) session.get(Person.class, 14);
        session.delete(person);

        session.getTransaction().commit();
    }

    @Test
    public void testDelCard() {
        Session session = FactoryUtil.getSession();
        session.beginTransaction();

        Card card = (Card) session.get(Card.class, 15);
        session.delete(card);
        session.getTransaction().commit();
    }
}

