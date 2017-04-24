package xyz.yanyj.entity.ticket;

import xyz.yanyj.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yanyj on 2017/4/13.
 */

@Entity
@Table(name = "t_ticket_profit")
public class Profit extends BaseEntity{


    /***
     * 日期
     */
    @Column()
    private Date date;

    /***
     * 盈利
     */
    @Column(scale = 2)
    private BigDecimal profit;

    /***
     * 投注额
     */
    @Column(scale = 2)
    private BigDecimal invest;

    /***
     * 红单数
     */
    @Column()
    private int redNum;

    /***
     * 黑单数
     */
    @Column()
    private int blackNum;

    /***
     * 走水单数
     */
    @Column()
    private int drawNum;

    /**************** getter and setter ****************/

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getInvest() {
        return invest;
    }

    public void setInvest(BigDecimal invest) {
        this.invest = invest;
    }

    public int getRedNum() {
        return redNum;
    }

    public void setRedNum(int redNum) {
        this.redNum = redNum;
    }

    public int getBlackNum() {
        return blackNum;
    }

    public void setBlackNum(int blackNum) {
        this.blackNum = blackNum;
    }

    public int getDrawNum() {
        return drawNum;
    }

    public void setDrawNum(int drawNum) {
        this.drawNum = drawNum;
    }

}
