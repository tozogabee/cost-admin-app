package cost.admin.app.model.category;

public enum ProductCategory {
    HOUSING("housing"),
    TRAVEL("travel"),
    FOOD("food"),
    UTILITIES("utilities"),
    INSURANCE("insurance"),
    HEALTHCARE("healthcare"),
    FINANCIAL("financial"),
    LIFESTYLE("lifestyle"),
    ENTERTAINMENT("entertainment"),
    MISCELLANEOUS("miscellaneous");

    String name;

    ProductCategory(String name) {
        this.name = name;
    }
}
