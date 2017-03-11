package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;


import java.util.Date;

public class Medicine {

    private Integer id;

    private String medicine;

    private String description;

    private Integer catId;

    private Integer reminderId;

    private Boolean remind;

    private Integer quantity;

    private Integer dosage;

    private Integer consumeQuantity;

    private Integer threshold;

    private Date dateIssue;

    private Integer expireFactor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getReminderId() {
        return reminderId;
    }

    public void setReminderId(Integer reminderId) {
        this.reminderId = reminderId;
    }

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDosage() {
        return dosage;
    }

    public void setDosage(Integer dosage) {
        this.dosage = dosage;
    }

    public Integer getConsumeQuantity() {
        return consumeQuantity;
    }

    public void setConsumeQuantity(Integer consumeQuantity) {
        this.consumeQuantity = consumeQuantity;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Date getDateIssue() {
        return dateIssue;
    }

    public void setDateIssue(Date dateIssue) {
        this.dateIssue = dateIssue;
    }

    public Integer getExpireFactor() {
        return expireFactor;
    }

    public void setExpireFactor(Integer expireFactor) {
        this.expireFactor = expireFactor;
    }
}
