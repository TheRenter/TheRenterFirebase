package in.therenter.therenterfirebase;

public class Product {
    private String imageString;
    private String name;
    private String brand;
    private String model;
    private String type;
    private String deliveryType;
    private String shippingType;
    private String tags;
    private String categories;
    private String rentalPeriodType;
    private String color;
    private String shortDesc;
    private String longDesc;
    private String deposit;
    private String shippingCharge;
    private String stockValue;
    private int rent1;
    private int rent2;
    private int rent3;
    private int rent4;
    private int rent5;

    public Product() {
    }

    public Product(String imageString, String name, String brand, String model, String type, String deliveryType, String shippingType, String tags, String categories, String rentalPeriodType, String color, String shortDesc, String longDesc, String deposit, String shippingCharge, String stockValue, int rent1, int rent2, int rent3, int rent4, int rent5) {
        this.imageString = imageString;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.deliveryType = deliveryType;
        this.shippingType = shippingType;
        this.tags = tags;
        this.categories = categories;
        this.rentalPeriodType = rentalPeriodType;
        this.color = color;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.deposit = deposit;
        this.shippingCharge = shippingCharge;
        this.stockValue = stockValue;
        this.rent1 = rent1;
        this.rent2 = rent2;
        this.rent3 = rent3;
        this.rent4 = rent4;
        this.rent5 = rent5;
    }

    public String getImageString() {
        return imageString;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public String getShippingType() {
        return shippingType;
    }

    public String getTags() {
        return tags;
    }

    public String getCategories() {
        return categories;
    }

    public String getRentalPeriodType() {
        return rentalPeriodType;
    }

    public String getColor() {
        return color;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public String getDeposit() {
        return deposit;
    }

    public String getShippingCharge() {
        return shippingCharge;
    }

    public String getStockValue() {
        return stockValue;
    }

    public int getRent1() {
        return rent1;
    }

    public int getRent2() {
        return rent2;
    }

    public int getRent3() {
        return rent3;
    }

    public int getRent4() {
        return rent4;
    }

    public int getRent5() {
        return rent5;
    }
}
