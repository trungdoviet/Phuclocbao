use phuclocbao;
drop table if exists tblCompany;

    drop table if exists tblCompanyType;

    drop table if exists tblContract;

    drop table if exists tblCustomer;

    drop table if exists tblPaymentSchedule;

    drop table if exists tblTransportOwner;

    drop table if exists tblUser;

    create table tblCompany (
        id integer not null auto_increment,
        address varchar(255),
        city varchar(100),
        costBeforeStartDate double precision,
        description varchar(255),
        fax varchar(255),
        investBeforeStartDate varchar(255),
        motobikeRentingFund varchar(255),
        name varchar(255) not null,
        originalFund double precision,
        phoneNumber varchar(100),
        revenueBeforeStartDate double precision,
        startDate datetime,
        totalFund varchar(255),
        companytype integer,
        primary key (id)
    );

    create table tblCompanyType (
        id integer not null auto_increment,
        name varchar(100),
        primary key (id)
    );

    create table tblContract (
        id integer not null auto_increment,
        contractType varchar(255) not null,
        expireDate datetime not null,
        feeADay double precision not null,
        note varchar(1024),
        notifyBeforePeriod varchar(1),
        periodOfPayment integer not null,
        startDate datetime not null,
        totalAmount double precision not null,
        company_id integer,
        primary key (id)
    );

    create table tblCustomer (
        id integer not null auto_increment,
        address varchar(255),
        idNo varchar(255) not null,
        name varchar(255) not null,
        phone varchar(255) not null,
        province varchar(255),
        contract_id integer not null,
        primary key (id)
    );

    create table tblPaymentSchedule (
        id integer not null auto_increment,
        finish varchar(1),
        notifiedDate datetime,
        payDate datetime,
        expectedPayDate datetime,
        contract_id integer,
        primary key (id)
    );

    create table tblTransportOwner (
        id integer not null auto_increment,
        chassisFrameNumber varchar(255),
        chassisNumber varchar(255),
        detail varchar(255),
        name varchar(255),
        numberPlate varchar(255),
        transportType varchar(255),
        contract_id integer,
        primary key (id)
    );

    create table tblUser (
        id integer not null auto_increment,
        email varchar(255),
        fullname varchar(255),
        password varchar(100),
        username varchar(30),
        companyEntity_id integer,
        primary key (id)
    );

    alter table tblCustomer 
        add constraint UK_3o2aqpijjd7i6f5galmbyc8d8 unique (contract_id);

    alter table tblCompany 
        add index FK_g7s20w1yfmoxa4v9nelh16msr (companytype), 
        add constraint FK_g7s20w1yfmoxa4v9nelh16msr 
        foreign key (companytype) 
        references tblCompanyType (id);

    alter table tblContract 
        add index FK_87w69tnlejq496ny03wh7u98w (company_id), 
        add constraint FK_87w69tnlejq496ny03wh7u98w 
        foreign key (company_id) 
        references tblCompany (id);

    alter table tblCustomer 
        add index FK_3o2aqpijjd7i6f5galmbyc8d8 (contract_id), 
        add constraint FK_3o2aqpijjd7i6f5galmbyc8d8 
        foreign key (contract_id) 
        references tblContract (id);

    alter table tblPaymentSchedule 
        add index FK_c90gk0vht5laui68ncan14oip (contract_id), 
        add constraint FK_c90gk0vht5laui68ncan14oip 
        foreign key (contract_id) 
        references tblContract (id);

    alter table tblTransportOwner 
        add index FK_agqgecfs7h42d8au6if5vq7uy (contract_id), 
        add constraint FK_agqgecfs7h42d8au6if5vq7uy 
        foreign key (contract_id) 
        references tblContract (id);

    alter table tblUser 
        add index FK_tdbth28vtu0pyx5bibwj90j2k (companyEntity_id), 
        add constraint FK_tdbth28vtu0pyx5bibwj90j2k 
        foreign key (companyEntity_id) 
        references tblCompany (id);
        
INSERT INTO `phuclocbao`.`tblcompanytype` (`name`) VALUES ('Trụ sở chính');
INSERT INTO `phuclocbao`.`tblcompanytype` (`name`) VALUES ('Chi nhánh');
INSERT INTO `phuclocbao`.`tblcompany` (`address`,`description`,`name`, `companytype`) VALUES ('39b Truong son','Axon active','Axon active vietnam','1');

INSERT INTO `phuclocbao`.`tbluser` (`email`, `fullname`, `password`, `username`, `companyEntity_id`) VALUES ('test@mail.com', 'phuc loc bao', '123456', 'phuclocbao', '1');

