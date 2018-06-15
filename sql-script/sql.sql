CREATE TABLE ac.tbl_client (
	id INT NOT NULL AUTO_INCREMENT,
	username varchar(256) DEFAULT "somebody" NULL, -- 记录所登录的账号
	`date` DATETIME NULL, -- 记录登录的时间点
	ip_source varchar(2048) NULL, -- 记录客户端IP
	tag TINYINT NULL, -- 所用的账号是否成功登录
	CONSTRAINT tbl_watcher_PK PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci ;
