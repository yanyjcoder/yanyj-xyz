package xyz.yanyj.ticket;

import xyz.yanyj.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by yanyj on 2017/4/13.
 */
@Entity
@Table(name = "t_ticket_account")
public class Account extends BaseEntity{

    /***
     * 金额
     */
    @Column(scale = 2)
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
