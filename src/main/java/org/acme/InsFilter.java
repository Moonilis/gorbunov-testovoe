package org.acme;

import java.util.Date;

public class InsFilter {

    private String docnumber;

    private Date datestart;

    private Date datefinish;

    private Integer minsum;

    private Integer maxsum;

    private Integer prodver;

    public String getDocnumber() {
        return docnumber;
    }

    public void setDocnumber(String docnumber) {
        this.docnumber = docnumber;
    }

    public Date getDatestart() {
        return datestart;
    }

    public void setDatestart(Date datestart) {
        this.datestart = datestart;
    }

    public Date getDatefinish() {
        return datefinish;
    }

    public void setDatefinish(Date datefinish) {
        this.datefinish = datefinish;
    }

    public Integer getMinsum() {
        return minsum;
    }

    public void setMinsum(Integer minsum) {
        this.minsum = minsum;
    }

    public Integer getMaxsum() { return maxsum; }

    public void setMaxsum(Integer maxsum) { this.maxsum = maxsum; }

    public Integer getProdver() {
        return prodver;
    }

    public void setProdver(Integer prodver) {
        this.prodver = prodver;
    }
}
