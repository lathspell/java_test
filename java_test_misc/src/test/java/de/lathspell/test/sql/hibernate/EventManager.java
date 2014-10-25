package de.lathspell.test.sql.hibernate;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;

public class EventManager {
    private SessionFactory sessionFactory;
    private SessionFactory annoSessionFactory;
    
    public static void main(String[] args) {
        EventManager mgr = new EventManager("de/lathspell/test/sql/hibernate/hibernate.cfg.xml");
        
        // speichern
        mgr.createAndStoreEvent("My Event", new Date());

        // laden
        for (Event e : mgr.listEvents()) {
            System.out.println(e);
        }

        // person -> event
        Long eventId = mgr.createAndStoreEvent("My Event", new Date());
        Long personId = mgr.createAndStorePerson("Foo", "Bar");
        mgr.addPersonToEvent(personId, eventId);
        System.out.println("Added person " + personId + " to event " + eventId);

        // food (try Annotations)
        mgr.createAndStoreFood("Eggs");
        Long foodId = mgr.createAndStoreFood("Noodles");
        System.out.println("Added Food (Noodles) as #"+foodId);
        
        // food (Named Query)
        System.out.println("Food count: " + mgr.listFoodCount());
        
        mgr.close();
    }

    public EventManager(String cfg) {
        
        Configuration xmlconfig = new Configuration();
        xmlconfig.configure(cfg);
        xmlconfig.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
        sessionFactory = xmlconfig.buildSessionFactory();
    

        AnnotationConfiguration annoconfig = new AnnotationConfiguration();
        annoconfig.configure(cfg);
        annoconfig.addAnnotatedClass(Food.class);
        annoSessionFactory = annoconfig.buildSessionFactory();
    }
    
    public void close() {
        System.out.println(sessionFactory.getStatistics());
        sessionFactory.close();
    }
    
    private long createAndStoreEvent(String title, Date theDate) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Event theEvent = new Event();
        theEvent.setTitle(title);
        theEvent.setDate(theDate);

        Long id = (Long)session.save(theEvent);
        session.getTransaction().commit();
    
        return id;
    }
    
    private void addEmailToPerson(Long personId, String emailAddress) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Person aPerson = (Person) session.load(Person.class, personId);

        // The getEmailAddresses() might trigger a lazy load of the collection
        aPerson.getEmailAddresses().add(emailAddress);

        session.getTransaction().commit();
    }

    private long createAndStoreFood(String fav) {
        Session session = annoSessionFactory.getCurrentSession();
        session.beginTransaction();

        Food food = new Food();
        food.setFav(fav);
        Long id = (Long)session.save(food);
        session.getTransaction().commit();
    
        return id;
    }
    
    private long createAndStorePerson(String vorname, String nachname) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Person thePerson = new Person();
        thePerson.setFirstname(vorname);
        thePerson.setLastname(nachname);
        Long id = (Long)session.save(thePerson);
        session.getTransaction().commit();
    
        return id;
    }
    
    private List<Event> listEvents() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Event> result = (List<Event>)session.createQuery("from Event").list();

        session.getTransaction().commit();
        return result;
    }

    private Long listFoodCount() {
        Session session = annoSessionFactory.getCurrentSession();
        session.beginTransaction();

        Query named1 = session.getNamedQuery("Food.named1");
        BigInteger result = (BigInteger)named1.list().get(0);
        
        session.getTransaction().commit();
        return result.longValue();
    }
    
    private void addPersonToEvent(Long personId, Long eventId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Person aPerson = (Person) session.load(Person.class, personId);
        Event anEvent = (Event) session.load(Event.class, eventId);

        aPerson.getEvents().add(anEvent);

        session.getTransaction().commit();
    }
}