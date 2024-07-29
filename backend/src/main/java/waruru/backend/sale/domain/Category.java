package waruru.backend.sale.domain;

public enum Category {

    SALE("매매"), JEONSE("전세"), MONTHLY("월세");

    private final String description;

    Category(String description) {

        this.description = description;
    }

    public String getDescription() {

        return description;
    }
}
