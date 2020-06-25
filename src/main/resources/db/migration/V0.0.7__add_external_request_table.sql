create table external_request
(
    id int auto_increment,
    action_id int not null,
    request_json text not null,
    response_json text null,
    request_time timestamp null,
    response_time timestamp null,
    constraint table_name_pk
        primary key (id),
    constraint table_name_client_action_id_fk
        foreign key (action_id) references client_action (id)
)