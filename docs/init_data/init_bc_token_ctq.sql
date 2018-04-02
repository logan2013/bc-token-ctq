
INSERT INTO `tsys_config`(type, ckey, cvalue, updater, update_datetime, remark) VALUES ('0','curEthBlockNumber','0','code',now(),'ETH下次从哪个区块开始扫描');
INSERT INTO `tsys_config`(type, ckey, cvalue, updater, update_datetime, remark) VALUES ('0','curBtcBlockNumber','1','admin',now(),'BTC下次从哪个区块开始扫描');
INSERT INTO `tsys_config`(type, ckey, cvalue, updater, update_datetime, remark) VALUES ('0','curTokenBlockNumber','0','admin',now(),'Token下次从哪个区块开始扫描');

INSERT INTO `tsys_config`(type, ckey, cvalue, updater, update_datetime, remark) VALUES ('0','blockConfirmEth','0','admin',now(),'ETH需要多少个区块确认');
INSERT INTO `tsys_config`(type, ckey, cvalue, updater, update_datetime, remark) VALUES ('0','blockConfirmBtc','0','admin',now(),'BTC需要多少个区块确认');
INSERT INTO `tsys_config`(type, ckey, cvalue, updater, update_datetime, remark) VALUES ('0','blockConfirmToken','0','admin',now(),'Token需要多少个区块确认');

INSERT INTO `tctq_token_contract` (`symbol`,`contract_address`,`create_datetime`) VALUES ('OGC','0x3F53B5cCC5b8EeddF837EdbA3948a1Ad51885471',now());