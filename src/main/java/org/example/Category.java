package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Represents a category with a name and provides utility methods related to categories.
 */
public class Category {
    private final String name;
    private static final Map<String, Category> CATEGORY_HASH = new HashMap<>();

    private Category(String name) {
        this.name = name;
    }

    /**
     * Creates a Category instance with the given name or retrieves an existing one if it already exists.
     *
     * @param name The name of the category
     * @return The Category instance associated with the provided name
     * @throws IllegalArgumentException if the name is null
     */
    public static Category of(String name) {
        if (name == null) throw new IllegalArgumentException("Category name can't be null");

        // Compute a new Category instance if not already present in the hash map
        return CATEGORY_HASH.computeIfAbsent(name, Category::new);
    }

    /**
     * Gets the name of the category with the first letter capitalized.
     *
     * @return The formatted category name
     */
    public String getName() {
        // Capitalize the first letter of the category name
        return Pattern.compile("^.").matcher(name).replaceFirst(m -> m.group().toUpperCase());
    }
}