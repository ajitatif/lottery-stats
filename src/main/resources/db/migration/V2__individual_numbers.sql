create table milli_piyango_number (
    lottery_type varchar(32) not null,
    lottery_date date not null,
    number int not null,
    lottery_oid varchar(16) not null references milli_piyango_result(oid)
);