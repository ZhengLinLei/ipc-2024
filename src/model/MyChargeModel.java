package model;

import javafx.beans.property.SimpleStringProperty;

public class MyChargeModel {
    private final SimpleStringProperty categoryName;
    private final SimpleStringProperty chargeName;
    private final SimpleStringProperty chargePrice;
    private final SimpleStringProperty chargeUds;
    private final SimpleStringProperty chargeDate;
    private Charge charge;

    public MyChargeModel(String categoryName, String chargeName, String chargePrice, String chargeUds, String chargeDate, Charge charge) {
        this.categoryName = new SimpleStringProperty(categoryName);
        this.chargeName = new SimpleStringProperty(chargeName);
        this.chargePrice = new SimpleStringProperty(chargePrice);
        this.chargeUds = new SimpleStringProperty(chargeUds);
        this.chargeDate = new SimpleStringProperty(chargeDate);
        this.charge = charge;
    }

    // Functions for tableview
    public String getCategoryName() {
        return categoryName.get();
    }

    public void setCategoryName(String categoryName) {
        this.categoryName.set(categoryName);
    }

    public String getChargeName() {
        return chargeName.get();
    }

    public void setChargeName(String chargeName) {
        this.chargeName.set(chargeName);
    }

    public String getChargePrice() {
        return chargePrice.get();
    }

    public void setChargePrice(String chargePrice) {
        this.chargePrice.set(chargePrice);
    }

    public String getChargeUds() {
        return chargeUds.get();
    }

    public void setChargeUds(String chargeUds) {
        this.chargeUds.set(chargeUds);
    }

    public String getChargeDate() {
        return chargeDate.get();
    }

    public void setChargeDate(String chargeDate) {
        this.chargeDate.set(chargeDate);
    }
    
    public Charge getChargeModel() {return this.charge;}
}
