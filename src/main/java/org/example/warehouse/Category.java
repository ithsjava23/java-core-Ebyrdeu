package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Category {
    private final String name;
    private static final Map<String, Category> CATEGORY_HASH = new HashMap<>();
    private Category(String name) {
        this.name = name;
    }

    public static Category of(String name) {
        if (name == null) throw new IllegalArgumentException("Category name can't be null");

        return CATEGORY_HASH.computeIfAbsent(name, Category::new);
    }


    public String getName() {
        return Pattern.compile("^.").matcher(name).replaceFirst(m -> m.group().toUpperCase());
    }

}
