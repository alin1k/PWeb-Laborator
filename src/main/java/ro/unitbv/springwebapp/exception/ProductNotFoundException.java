package ro.unitbv.springwebapp.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Integer id) {
        super("Produsul cu id-ul " + id + " nu a fost gasit.");
    }
}