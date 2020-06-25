alter table existing_payment
    add payed_client_id int null after payment_type_id;

alter table existing_payment
    add constraint existing_payment_client_id_fk
        foreign key (payed_client_id) references client (id);