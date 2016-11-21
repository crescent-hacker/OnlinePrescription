package domainModel.pharmacy;

import java.io.Serializable;

/**
 * Created by Fang on 7/10/2016.
 */
public class Detail implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;


    private String drug_id;
    private String drug_name;
    private String drug_serial;
    private String drug_producer;


    private String drug_package;
    private String drug_price;
    private String value;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getDrug_id() {
        return drug_id;
    }

    public void setDrug_id(String drug_id) {
        this.drug_id = drug_id;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }
    public String getDrug_serial() {
        return drug_serial;
    }

    public void setDrug_serial(String drug_serial) {
        this.drug_serial = drug_serial;
    }

    public String getDrug_producer() {
        return drug_producer;
    }

    public void setDrug_producer(String drug_producer) {
        this.drug_producer = drug_producer;
    }

    public String getDrug_package() {
        return drug_package;
    }

    public void setDrug_package(String drug_package) {
        this.drug_package = drug_package;
    }

    public String getDrug_price() {
        return drug_price;
    }

    public void setDrug_price(String drug_price) {
        this.drug_price = drug_price;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
