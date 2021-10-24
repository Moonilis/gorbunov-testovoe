package org.acme;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "insproduct", schema = "public")
@NamedQuery(name = "InsProduct.findAll", query = "SELECT i FROM InsProduct i")
public class InsProduct {

    @Id
    @SequenceGenerator(name="insProductGen",
            sequenceName="ins_product_seq", allocationSize=1, initialValue = 100)
    @GeneratedValue(generator="insProductGen")
    @Column(name = "id_document")
    private Integer id_document;

    @Column(name = "docnumber")
    private String docnumber;

    @Column(name = "datestart")
 //   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yy")
    private Date datestart;

    @Column(name = "datefinish")
 //   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yy")
    private Date datefinish;

    @Column(name = "inssum")
    private Integer inssum;

    @Column(name = "prodver")
    private Integer prodver;

    public InsProduct() {}
    public InsProduct(String docnumber, Date datestart, Date datefinish, Integer inssum, Integer prodver) {
        this.docnumber = docnumber;
        this.datestart = datestart;
        this.datefinish = datefinish;
        this.inssum = inssum;
        this.prodver = prodver;
    }
    public InsProduct(String docnumber) {
        new InsProduct(docnumber, null, null, null, null);
    }

    public Integer getId() { return id_document; }

    public void setDocnumber(String docnumber) { this.docnumber = docnumber; }

    public String getDocnumber() { return docnumber; }

    public void setDatestart(Date datestart) { this.datestart = datestart; }

    public Date getDatestart() { return datestart; }

    public void setDatefinish(Date datefinish) { this.datefinish = datefinish; }

    public Date getDatefinish() { return datefinish; }

    public void setInssum(Integer inssum) { this.inssum = inssum; }

    public Integer getInssum() { return inssum; }

    public void setProdver(Integer prodver) { this.prodver = prodver; }

    public Integer getProdver() { return prodver; }
}