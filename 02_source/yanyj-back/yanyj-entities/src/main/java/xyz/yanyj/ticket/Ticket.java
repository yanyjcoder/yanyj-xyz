package xyz.yanyj.ticket;

import xyz.yanyj.base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 彩票类
 * @author yanyj
 * @date 2017-04-12
 **/

@Entity
@Table(name = "t_ticket_lottery")
public class Ticket extends BaseEntity{

    /***
     * 流水单号
     */
    @Column(length = 11)
    private String serialNumber;

    /***
     * 彩票类型
     */
    @Column(length = 50)
    private String lotteryClass;

    /***
     * 彩票种类
     */
    @Column(length = 50)
    private String lotteryType;

    /***
     * 下注日期
     */
    @Column
    private Date betDate;

    /***
     * 主队
     */
    @Column(length = 50)
    private String homeTeam;

    /***
     * 客队
     */
    @Column(length = 50)
    private String guesTeam;

    /***
     * 即时比分
     */
    @Column()
    private BigDecimal liveScore;

    /***
     * 最终比分
     */
    @Column()
    private BigDecimal finalScore;

    /***
     * 下注类型 大小， 胜负
     */
    @Column()
    private String betType;

    /***
     * 下注金额
     */
    @Column()
    private BigDecimal stake;

    /***
     * 赔率 1：n
     */
    @Column(scale = 2)
    private BigDecimal odds;

    /***
     * 下注的比率
     */
    @Column(scale = 2)
    private BigDecimal gmbl;

    /***
     * 盈利
     */
    @Column(scale = 2)
    private BigDecimal profit;

    /***
     * 注单状态 0 未结算 1 已结算 （-1 作废）
     */
    @Column(scale = 2)
    private int status;

    /***
     * 注单来源
     */
    @Column()
    private String source;

    /*************** getter and setter ****************/

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getLotteryClass() {
        return lotteryClass;
    }

    public void setLotteryClass(String lotteryClass) {
        this.lotteryClass = lotteryClass;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public Date getBetDate() {
        return betDate;
    }

    public void setBetDate(Date betDate) {
        this.betDate = betDate;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getGuesTeam() {
        return guesTeam;
    }

    public void setGuesTeam(String guesTeam) {
        this.guesTeam = guesTeam;
    }

    public BigDecimal getLiveScore() {
        return liveScore;
    }

    public void setLiveScore(BigDecimal liveScore) {
        this.liveScore = liveScore;
    }

    public BigDecimal getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(BigDecimal finalScore) {
        this.finalScore = finalScore;
    }

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public BigDecimal getStake() {
        return stake;
    }

    public void setStake(BigDecimal stake) {
        this.stake = stake;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public BigDecimal getGmbl() {
        return gmbl;
    }

    public void setGmbl(BigDecimal gmbl) {
        this.gmbl = gmbl;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
