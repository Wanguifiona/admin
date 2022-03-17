package dao;

import models.Invoice;

import java.util.List;

public interface InvoiceDao {

    // CREATE
    void save(Invoice invoice);

    // READ
    List<Invoice> getAll();
    Invoice findById(int id);
    List<Invoice> findByUserId(int userId);

    List<Invoice> findAll();

    // UPDATE

    // DELETE
    void deleteById(int id);
    void clearAll();

}

