package cost.admin.app.model.currency;

public enum Currency {

    EUR("EUR"),
    USD("USD"),
    CHF("CHF"),
    HUF("HUF"),
    GBP("GBP");

    String name;

    Currency(String name) {
        this.name = name;
    }
}
