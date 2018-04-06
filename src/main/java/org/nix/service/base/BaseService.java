package org.nix.service.base;

import java.io.Serializable;
import java.util.List;

/**
 * @author 11723
 */
public interface BaseService<M extends Object,ID extends Serializable>{

    /**
     * 保存对象M
     * @param m
     * @return 成功返回m
     * 失败返回null
     * */
    M save(M m);
    /**
     * 删除对象M
     * @param m
     * @return 成功返回m
     * 失败返回null
     * */
    M delete(M m);
    /**
     * 更新对象M
     * @param m
     * @return 成功返回修改后的m
     * 失败返回null
     * */
    M update(M m);
    /**
     * 查找唯一对象M
     * @param id id值
     * @return 唯一对象
     * */
    M findById(ID id);

    /**
     * 查找部对象
     * @return 全部对象
     * */
    List<M> findAll();
}
