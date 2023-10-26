package org.example.warehouse;

import org.example.Category;

import java.math.BigDecimal;
import java.util.*;

/**
 * Represents a warehouse that manages product records.
 */
public class Warehouse {
    /**
     * Map to store instances of Warehouse by their names.
     */
    private static final Map<String, Warehouse> WAREHOUSE_HASH = new HashMap<>();

    /**
     * List to store the product records in the warehouse.
     */
    private static List<ProductRecord> products = new ArrayList<>();

    /**
     * List to store product records that have been changed.
     */
    private static List<ProductRecord> changed = new ArrayList<>();

    /**
     * Name of the warehouse.
     */
    private String name;

    /**
     * Private constructor for Warehouse.
     */
    private Warehouse() {
    }

    /**
     * Private constructor for Warehouse with a specified name.
     *
     * @param name The name of the warehouse.
     */
    private Warehouse(String name) {
        this.name = name;
    }

    /**
     * Gets an instance of Warehouse. Clears the products list.
     *
     * @return An instance of Warehouse.
     */
    public static Warehouse getInstance() {
        products = new ArrayList<>();
        return new Warehouse();
    }

    /**
     * Gets an instance of Warehouse with a specified name. Clears the products list.
     *
     * @param name The name of the warehouse.
     * @return An instance of Warehouse.
     */
    public static Warehouse getInstance(String name) {
        products = new ArrayList<>();
        return WAREHOUSE_HASH.computeIfAbsent(name, Warehouse::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(name, warehouse.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Checks if the warehouse is empty.
     *
     * @return True if the warehouse is empty, false otherwise.
     */
    public boolean isEmpty() {
        return products.isEmpty();
    }

    /**
     * Gets a list of product records in the warehouse.
     *
     * @return An unmodifiable list of product records.
     */
    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(products);
    }

    /**
     * Adds a new product record to the warehouse.
     *
     * @param uuid       The UUID of the product.
     * @param name       The name of the product.
     * @param category   The category of the product.
     * @param bigDecimal The price of the product.
     * @return The created ProductRecord.
     * @throws IllegalArgumentException If a product with the given UUID already exists.
     */
    public ProductRecord addProduct(UUID uuid, String name, Category category, BigDecimal bigDecimal) {
        if (!products.stream().filter(pR -> pR.uuid().equals(uuid)).toList().isEmpty())
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");

        ProductRecord product = new ProductRecord(uuid, name, category, bigDecimal);
        products.add(product);
        return product;
    }

    /**
     * Gets a product record by its UUID.
     *
     * @param uuid The UUID of the product.
     * @return An Optional containing the product record, or empty if not found.
     */
    public Optional<ProductRecord> getProductById(UUID uuid) {
        return products.stream()
                .filter(pR -> pR.uuid().equals(uuid))
                .findFirst();
    }

    /**
     * Updates the price of a product.
     *
     * @param uuid  The UUID of the product.
     * @param price The new price for the product.
     * @throws IllegalArgumentException If a product with the given UUID doesn't exist.
     */
    public void updateProductPrice(UUID uuid, BigDecimal price) {
        // Check if the product with the given UUID exists
        Optional<ProductRecord> existingProduct = products.stream()
                .filter(product -> product.uuid().equals(uuid))
                .findFirst();

        if (existingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        }

        List<ProductRecord> updatedProducts = products.stream()
                .map(product -> product.uuid().equals(uuid)
                        ? new ProductRecord(product.uuid(), product.name(), product.category(), price)
                        : product)
                .toList();

        changed = products.stream()
                .filter(product -> product.uuid().equals(uuid))
                .toList();

        products = updatedProducts;

    }

    /**
     * Gets a list of product records that have been changed.
     *
     * @return An unmodifiable list of changed product records.
     */
    public List<ProductRecord> getChangedProducts() {

        return Collections.unmodifiableList(changed);
    }

    /**
     * Groups products by categories.
     *
     * @return A map where keys are categories and values are lists of product records for that category.
     */
    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        Map<Category, List<ProductRecord>> sorted = new HashMap<>();

        products.forEach(p -> sorted.computeIfAbsent(p.category(), k -> new ArrayList<>()).add(p));

        return sorted;
    }

    /**
     * Gets a list of product records for a specific category.
     *
     * @param category The category of products to retrieve.
     * @return A list of product records for the specified category.
     */
    public List<ProductRecord> getProductsBy(Category category) {
        return products.stream().filter(p -> p.category() == category).toList();
    }
}
