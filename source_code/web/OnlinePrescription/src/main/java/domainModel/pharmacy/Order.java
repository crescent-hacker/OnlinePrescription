package domainModel.pharmacy;

import java.io.Serializable;

/**
 * Created by Fang on 7/10/2016.
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String order_id;
    private String doc_id;
    private String doc_title;

    public String getDoc_title() {
        return doc_title;
    }

    public void setDoc_title(String doc_title) {
        this.doc_title = doc_title;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDoc_phone() {
        return doc_phone;
    }

    public void setDoc_phone(String doc_phone) {
        this.doc_phone = doc_phone;
    }

    public String getDoc_hospital() {
        return doc_hospital;
    }

    public void setDoc_hospital(String doc_hospital) {
        this.doc_hospital = doc_hospital;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    private String doc_name;
    private String doc_phone;
    private String doc_hospital;
    private String department;
    private String pat_id;
    private String phar_id;
    private String order_time;
    private String pick_time;
    private String status;
    private String pat_name;
    private String pat_phone;


    private String pat_medicnum;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getPat_id() {
        return pat_id;
    }

    public void setPat_id(String pat_id) {
        this.pat_id = pat_id;
    }

    public String getPhar_id() {
        return phar_id;
    }

    public void setPhar_id(String phar_id) {
        this.phar_id = phar_id;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getPick_time() {
        return pick_time;
    }

    public void setPick_time(String pick_time) {
        this.pick_time = pick_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPat_name() {
        return pat_name;
    }

    public void setPat_name(String pat_name) {
        this.pat_name = pat_name;
    }

    public String getPat_phone() {
        return pat_phone;
    }

    public void setPat_phone(String pat_phone) {
        this.pat_phone = pat_phone;
    }

    public String getPat_medicnum() {
        return pat_medicnum;
    }

    public void setPat_medicnum(String pat_medicnum) {
        this.pat_medicnum = pat_medicnum;
    }

}
