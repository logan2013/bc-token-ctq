/**
 * @Title TokenClient.java 
 * @Package com.cdkj.coin.wallet.contract 
 * @Description 
 * @author xieyj  
 * @date 2018年3月7日 下午8:54:24 
 * @version V1.0   
 */
package com.cdkj.coin.token;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import com.cdkj.coin.common.PropertiesUtil;
import com.cdkj.coin.ethereum.Web3JClient;
import com.cdkj.coin.exception.BizException;
import com.cdkj.coin.exception.EBizErrorCode;
import com.cdkj.coin.token.OrangeCoinToken.TransferEventResponse;

/** 
 * @author: xieyj 
 * @since: 2018年3月7日 下午8:54:24 
 * @history:
 */
public class TokenClient {

    static final Logger logger = LoggerFactory.getLogger(TokenClient.class);

    private static String TOKEN_URL = PropertiesUtil.Config.TOKEN_URL;

    private TokenClient() {
    }

    private volatile static Web3j web3j;

    public static Web3j getClient() {
        if (web3j == null) {
            synchronized (TokenClient.class) {
                if (web3j == null) {
                    web3j = Web3j.build(new HttpService(TOKEN_URL));
                }
            }
        }
        return web3j;
    }

    public static EthBlock.Block getBlock(Long blockNumber) {
        EthBlock.Block currentBlock = null;
        try {

            // 获取当前区块
            EthBlock ethBlockResp = getClient().ethGetBlockByNumber(
                new DefaultBlockParameterNumber(blockNumber), true).send();
            if (ethBlockResp.getError() != null) {
                logger.error("获取区块发送异常，原因：" + ethBlockResp.getError());
            }
            currentBlock = ethBlockResp.getResult();

        } catch (Exception e) {
            throw new BizException("xn000000",
                "获取区块" + blockNumber + "发生异常，原因：" + e.getMessage());
        }
        return currentBlock;
    }

    public static BigInteger getCurBlockNumber() {
        BigInteger currentBlockNumber = null;
        try {

            currentBlockNumber = getClient().ethBlockNumber().send()
                .getBlockNumber();

        } catch (Exception e) {
            throw new BizException("xn000000",
                "查询当前最大区块发生异常，原因：" + e.getMessage());
        }
        return currentBlockNumber;
    }

    private volatile static OrangeCoinToken orangeCoinToken;

    // 加载合约
    public static OrangeCoinToken loadHolderContract(String contractAddress) {
        try {
            Credentials credentials = WalletUtils.loadCredentials(
                PropertiesUtil.Config.CONTRACT_HOLDER_PWD,
                PropertiesUtil.Config.KEY_STORE_PATH + "/"
                        + PropertiesUtil.Config.CONTRACT_HOLDER_KEYSTORE);
            BigInteger gasLimit = BigInteger.valueOf(210000);
            BigInteger gasPrice = Web3JClient.getClient().ethGasPrice().send()
                .getGasPrice();
            BigInteger txFee = gasLimit.multiply(gasPrice);
            logger.info("以太坊平均价格=" + gasPrice + "，预计矿工费=" + txFee);
            synchronized (TokenClient.class) {
                if (orangeCoinToken == null) {
                    orangeCoinToken = OrangeCoinToken.load(contractAddress,
                        getClient(), credentials, gasPrice, gasLimit);
                }
            }
        } catch (Exception e) {
            throw new BizException(EBizErrorCode.DEFAULT.getErrorCode(),
                "智能合约初始化失败,原因是" + e.getMessage());
        }
        return orangeCoinToken;
    }

    public static void main(String[] args) {
    }

    public static List<TransferEventResponse> loadTransferEvents(
            TransactionReceipt transactionReceipt) {

        List<OrangeCoinToken.TransferEventResponse> transferEventList = null;

        OrangeCoinToken contract = loadHolderContract(
            transactionReceipt.getTo());
        try {
            transferEventList = contract.getTransferEvents(transactionReceipt);
        } catch (Exception e) {
            throw new BizException(EBizErrorCode.DEFAULT.getErrorCode(),
                "智能合约解析交易事件失败，原因：" + e.getMessage());
        }
        return transferEventList;
    }

}