package xyz.yanyj.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base实体类
 * @author yanyj
 * @date 2017-04-12
 **/
@MappedSuperclass
public class BaseEntity {

    /**
     * 主键
     */
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", length = 32, unique = true, nullable = false)
    protected String id;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

}
