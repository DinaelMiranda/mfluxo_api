CREATE TABLE IF  NOT EXISTS pessoa  (
id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR (50) NOT NULL,
status BOOLEAN NOT NULL,
logradouro VARCHAR (30) NOT NULL,
numero VARCHAR (10) NOT NULL,
complemento VARCHAR (50) NOT NULL,
bairro VARCHAR (30) NOT NULL,
cep VARCHAR (20) NOT NULL,
cidade VARCHAR (20) NOT NULL,
estado VARCHAR (20) NOT NULL

) ENGINE=innoDB DEFAULT CHARSET=utf8;

