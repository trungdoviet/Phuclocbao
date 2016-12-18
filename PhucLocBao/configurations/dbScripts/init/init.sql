
    drop table if exists tblCompany

    drop table if exists tblCompanyType

    drop table if exists tblUser

    create table tblCompany (
        id integer not null auto_increment,
        address varchar(255),
        description varchar(255),
        name varchar(255) not null,
        companytype integer,
        primary key (id)
    )

    create table tblCompanyType (
        id integer not null auto_increment,
        name varchar(100),
        primary key (id)
    )

    create table tblUser (
        id integer not null auto_increment,
        email varchar(255),
        fullname varchar(255),
        password varchar(100),
        username varchar(30),
        companyEntity_id integer,
        primary key (id)
    )

    alter table tblCompany 
        add index FK_g7s20w1yfmoxa4v9nelh16msr (companytype), 
        add constraint FK_g7s20w1yfmoxa4v9nelh16msr 
        foreign key (companytype) 
        references tblCompanyType (id)

    alter table tblUser 
        add index FK_tdbth28vtu0pyx5bibwj90j2k (companyEntity_id), 
        add constraint FK_tdbth28vtu0pyx5bibwj90j2k 
        foreign key (companyEntity_id) 
        references tblCompany (id)

INSERT INTO `phuclocbao`.`tblcompanytype` (`name`) VALUES ('Trụ sở chính');
INSERT INTO `phuclocbao`.`tblcompanytype` (`name`) VALUES ('Chi nhánh');
INSERT INTO `phuclocbao`.`tblcompany` (`address`,`description`,`name`, `companytype`) VALUES ('39b Truong son','Axon active','Axon active vietnam','1');

INSERT INTO `phuclocbao`.`tbluser` (`email`, `fullname`, `password`, `username`, `companyEntity_id`) VALUES ('test@mail.com', 'phuc loc bao', '123456', 'phuclocbao', '1');

