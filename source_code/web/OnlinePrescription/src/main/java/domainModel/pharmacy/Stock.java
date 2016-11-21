package domainModel.pharmacy;

import java.io.Serializable;

/**
 * Created by Fang on 9/10/2016.
 */
public class Stock  implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;
    private String name;
    private String producer;
    private String stock;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
