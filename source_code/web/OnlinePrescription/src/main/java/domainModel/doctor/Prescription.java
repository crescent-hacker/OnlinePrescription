package domainModel.doctor;

import java.util.Map;

/**
 * Author: Difan Chen
 * Date: 31/10/2016
 * Student No: 4901496
 */
public class Prescription {
    int patientId;
    int[] drugsId;
    String[] drugsDesc;
    int[] drugsDose;
    double totalPrice;
    String description;
    int repeat;
    int duration;

    public Prescription(Map<String,Object> params) {
        this.patientId = Integer.valueOf((String)params.get("patient_id"));
        //drug ids
        String[] drugsIdStrs = ((String)params.get("drugs_id")).split("~");
        drugsId = new int[drugsIdStrs.length];
        for (int i = 0; i < drugsId.length; i++) {
            drugsId[i] = Integer.valueOf(drugsIdStrs[i]);
        }
        //drug descs
        this.drugsDesc = new String[drugsId.length];
        String[] tmpDescs = ((String)params.get("drugs_desc")).split("~");
        for (int i = 0; i < drugsDesc.length; i++) {
            if(i<tmpDescs.length){
                drugsDesc[i] = tmpDescs[i];
            }else{
                drugsDesc[i] = "";
            }
        }
        //drug doses
        String[] drugsDoseStrs = ((String)params.get("drugs_dose")).split("~");
        drugsDose = new int[drugsDoseStrs.length];
        for (int i = 0; i < drugsDose.length; i++) {
            drugsDose[i] = Integer.valueOf(drugsDoseStrs[i]);
        }
        //total price
        totalPrice = Double.valueOf((String)params.get("total_price"));
        //description
        description = (String)params.get("description");
        //repeat
        repeat = Integer.valueOf((String)params.get("repeat"));
        //duration
        duration = Integer.valueOf((String)params.get("duration"));
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int[] getDrugsId() {
        return drugsId;
    }

    public void setDrugsId(int[] drugsId) {
        this.drugsId = drugsId;
    }

    public String[] getDrugsDesc() {
        return drugsDesc;
    }

    public void setDrugsDesc(String[] drugsDesc) {
        this.drugsDesc = drugsDesc;
    }

    public int[] getDrugsDose() {
        return drugsDose;
    }

    public void setDrugsDose(int[] drugsDose) {
        this.drugsDose = drugsDose;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
