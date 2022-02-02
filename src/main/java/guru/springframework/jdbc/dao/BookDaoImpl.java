package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

@Component
public class BookDaoImpl implements BookDao{

    private final EntityManagerFactory emf;

    public BookDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Book getById(Long id) {
        EntityManager em = getEntityManager();
        Book book;
        try {
            book =  getEntityManager().find(Book.class, id);
        } finally {
            em.close();
        }
        return book;
    }

    @Override
    public Book findBookByTitle(String title) {
        EntityManager em = getEntityManager();
        Book book;
        try {
            TypedQuery<Book> query = getEntityManager().createQuery("select b from Book b where b.title = :title", Book.class);
            query.setParameter("title", title);
            book = query.getSingleResult();
        } finally {
            em.close();
        }
        return book;
    }

    @Override
    public Book saveNewBook(Book book) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(book);
            em.flush();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        EntityManager em = getEntityManager();
        Book savedBook;
        try {
            em.getTransaction().begin();
            em.merge(book);
            em.flush();
            em.getTransaction().commit();
            savedBook = em.find(Book.class, book.getId());
        } finally {
            em.close();
        }
        return savedBook;

    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Book book = em.find(Book.class, id);
            em.remove(book);
            em.flush();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
