alter table client_ip
    drop column ip_address_int,
    drop column domain,
    MODIFY column ip_address varchar(32) NOT NULL;
create unique index client_ip_country_code_uindex
    on client_ip (ip_address);
