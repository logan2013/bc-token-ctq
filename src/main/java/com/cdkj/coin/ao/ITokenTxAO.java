package com.cdkj.coin.ao;

import java.util.List;

import com.cdkj.coin.bo.base.Paginable;
import com.cdkj.coin.domain.EthTransaction;
import com.cdkj.coin.dto.req.EthTxPageReq;

/**
 * Created by tianlei on 2017/十一月/02.
 */
public interface ITokenTxAO {

    // 定时器调用：每隔**同步账户流水
    public void doTokenTransactionSync();

    // 确认推送成功
    public Object confirmPush(List<String> hashList);

    // 分页查询交易
    public Paginable<EthTransaction> queryTxPage(EthTxPageReq req);
}