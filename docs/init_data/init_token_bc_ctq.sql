
INSERT INTO `tsys_config`(type, ckey, cvalue, updater, update_datetime, remark) VALUES ('0','curEthBlockNumber','0','admin',now(),'ETH下次从哪个区块开始扫描');
INSERT INTO `tsys_config`(type, ckey, cvalue, updater, update_datetime, remark) VALUES ('0','curBtcBlockNumber','1','admin',now(),'BTC下次从哪个区块开始扫描');

INSERT INTO `tsys_config`(type, ckey, cvalue, updater, update_datetime, remark) VALUES ('0','blockConfirmEth','0','admin',now(),'ETH需要多少个区块确认');
INSERT INTO `tsys_config`(type, ckey, cvalue, updater, update_datetime, remark) VALUES ('0','blockConfirmBtc','0','admin',now(),'BTC需要多少个区块确认');

INSERT INTO `tctq_token_contract` (`symbol`,`contract_address`,`create_datetime`) VALUES ('FMVP','0x68aaefbdd5cb18ff577966cb2679df42e2eba548',now());